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
	
		gameClient = new GameClient(name,passwd,this);
		roomlist = gameClient.getRoomLists();
	
		//Print out the room list to user
		for(GameRoom gr : roomlist){
			System.out.println(gr.toString());
			System.out.println("With: " + gr.userCount + " users");
		}
	
	
		//Scan room number and connect to room.
	    Scanner sc = new Scanner(System.in);
		System.out.println("Which game room would you like to join?");
		int chosenRoom = Integer.parseInt(sc.next());
		System.out.println(chosenRoom);
		gameClient.joinGameRoom(gameClient.getRoomLists().get(chosenRoom-2).roomName);
		roomId = chosenRoom;
	
		/*
	    Most of this is useless and just for testing...
		 */
		
		System.out.println("You are connected to room:  " + gameClient.getRoomLists()
				.get(chosenRoom-2).roomName );
		System.out.println("There are: " + gameClient.getRoomLists()
				.get(chosenRoom-2).userCount + " users in the room currently.");
	}

	public GameBoard getGameBoard(){ 
    	return this.frame;
    }

    /*
        Wraps chat message in xml

     */
    public String wrapChatMessage(String msg){
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
    	System.out.println(msg);

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
        t.generateDepthsOurMoveIsWhite(true);
        Node n =t.getBestMove();
        System.out.println(n);
        
        Action a = n.getAction();

        frame.setNewBoard(a.makeThisMove());
        frame.reDraw();
    	String move = MoveHandler.createMove(roomId, a);
    	gameClient.sendToServer(move, true);
    }
}
