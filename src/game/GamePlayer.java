package game;

import java.util.ArrayList;
import java.util.Scanner;

import Tree.Node;
import Tree.Tree;
import net.n3.nanoxml.IXMLElement;
import ubco.ai.GameRoom;
import ubco.ai.connection.ServerMessage;
import ubco.ai.games.GameClient;
import ubco.ai.games.GameMessage;


public class GamePlayer implements ubco.ai.games.GamePlayer{

    GameClient gameClient = null;
    ArrayList<GameRoom> roomlist;
    GameBoard frame = null;
    String userName;
    boolean turn;
    int roomId;
    //If True, role is white
    boolean roleBoolean;

    public GamePlayer(String name, String passwd){
	
		frame = new GameBoard(true);
		frame.pack();
		frame.setResizable(true);
		frame.setLocationRelativeTo( null );
		frame.setVisible(true);
		userName = name;
	
		gameClient = new GameClient(name, passwd, this);
		roomlist = gameClient.getRoomLists();
	
		//Print out the room list to user
		for(GameRoom gr : roomlist){
			System.out.println(gr.toString());
			System.out.println("With: " + gr.userCount + " users");
		}
	
	
		this.joinRoom();
		
		/*
	    Most of this is useless and just for testing...
		 */
		
	
	}

	public GameBoard getGameBoard(){ 
    	return this.frame;
    }

    /*
        Wraps chat message in xml
     */
    public String wrapChatMessage(String msg)throws Exception{
        msg = "<action type='"+GameMessage.MSG_CHAT+"'>"+msg+"</action>";
        return msg;
    }



    public boolean handleMessage(String msg){
    	System.out.println(msg);
    	return true;
    }

    public boolean handleMessage(GameMessage msg)throws Exception{
    	IXMLElement xml = ServerMessage.parseMessage(msg.msg);
    	String type = xml.getAttribute("type", null);
    	System.out.println(msg.msg);

    	switch(type){
    		case GameMessage.MSG_CHAT:
    			break;
    		case GameMessage.ACTION_GAME_START:
    			// search the element for our role
    			for (Object o : xml.getChildAtIndex(0).getChildren()){
    				IXMLElement user = (IXMLElement) o;
    				String name = user.getAttribute("name", null);
    				if (name.equals(userName)){
    					String role = user.getAttribute("role", null);
    					turn = role.equals("W");
    					roleBoolean = turn;
    					break;
    				}
    			}
    			if(turn){
    				makeMove();
    			}
    			// suggest garbage collection during "down time"
    			System.runFinalization();
    			System.gc();
    			break;
    		case GameMessage.ACTION_ROOM_JOINED:
    			System.out.println("Joined Room");
    			break;
    		case GameMessage.ACTION_MOVE:
    			Action move = MoveHandler.createMove(xml, frame.state);
    			// first validate the move!
    			if (!turn){
    				turn = true;
    				// apply the opponent's action
    				frame.setNewBoard (move.makeThisMove());
    				frame.reDraw();
    		
    				makeMove();
    			}else{
    				System.out.println("Other player tried to move during our turn");
    			}
    			
    			// suggest garbage collection during "down time"
    			System.runFinalization();
    			System.gc();
    			break;
    		default:
    	}

    	return true;
    }
    
    public void sendToServer(String msgType, int roomID){
    	// before sending the message to the server, you need to (1) build the text of the message
    	// as a string,  (2) compile the message by calling
    	// the static method ServerMessage.compileGameMessage(msgType, roomID, actionMsg),
    	// and (3) call the method gameClient.sendToServer() to send the compiled message.

    	//For message types and message format, see the GameMessage API and the project notes
    }
    
    private void makeMove(){
    	turn = false;
    	Tree t = new Tree(frame.getBoard());
    	 Node n;
    	if(roleBoolean){
    		t.generateDepthsOurMoveIsWhite(true);
    		n = t.getBestMoveAsWhite();
    	}else{
    		t.generateDepthsOurMoveIsBlack(false);
    		n = t.getBestMoveAsBlack();
    	}
        System.out.println(n);
        
        Action a = n.getAction();

        frame.setNewBoard(a.makeThisMove());
    	String move = MoveHandler.createMove(roomId, a);
    	gameClient.sendToServer(move, true);
        frame.reDraw();
    }
    
    // Join a room
    public void joinRoom() {
        Scanner in = new Scanner(System.in);
        System.out.println("\nWhich room would you like to join?");

        String room = in.nextLine();            // stores the name of the room we will be joining
        gameClient.joinGameRoom(room);

        // now we will check through the list of game rooms and see which one has this name and determine the room number
        for (GameRoom r : gameClient.getRoomLists()) {
            if (r.roomName.equals(room)) {
                roomId = r.roomID;
                System.out.println("The room ID of the room we are in (" + room + ") is " + roomId);
                break;
            }
        }
    }
}
