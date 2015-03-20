//package game;
//
//
//
//import java.util.ArrayList;
//import java.util.Scanner;
//
//
//
//public class GamePlayer implements ubco.ai.games.GamePlayer{
//
//
//    GameClient gameClient = null;
//    ArrayList<GameRoom> roomlist;
//    GameBoard frame = null;
//
//    public GameBoard getGameBoard(){ return this.frame;}
//
//
//
//
//
//    public GamePlayer(String name, String passwd){
//
//    	frame = new GameBoard();
//    	frame.pack();
//    	frame.setResizable(true);
//    	frame.setLocationRelativeTo( null );
//    	frame.setVisible(true);
//
//    	gameClient = new GameClient(name,passwd,this);
//    	roomlist = gameClient.getRoomLists();
//
//    	//Print out the room list to user
//    	for(GameRoom gr : roomlist){
//    		System.out.println(gr.toString());
//    		System.out.println("With: " + gr.userCount + " users");
//    	}
//
//
//    	//Scan room number and connect to room.
//        Scanner sc = new Scanner(System.in);
//    	System.out.println("Which game room would you like to join?");
//    	int chosenRoom = Integer.parseInt(sc.next());
//    	System.out.println(chosenRoom);
//    	gameClient.joinGameRoom(gameClient.getRoomLists().get(chosenRoom-2).roomName);
//
//
//    	/*
//        Most of this is useless and just for testing...
//    	 */
//
//    	boolean escape = false;
//    	while(!escape){
//
//    		//debug
//    		System.out.println("You are connected to room:  " + gameClient.getRoomLists().get(chosenRoom-2).roomName );
//    		System.out.println("There are: " + gameClient.getRoomLists().get(chosenRoom-2).userCount + " users in the room currently.");
//
//
//
//    		System.out.println("Send a message or type 'n' to quit...");
//
//    		String message = sc.next();
//
//
//    		if(!"n".equalsIgnoreCase(message)){
//
//    			/*
//                Looks like we need to compile the message (see yong's code snippet below this...)
//                Broken at the moment.
//    			 */
//                String messageToSend = ServerMessage.compileGameMessage(GameMessage.MSG_CHAT, gameClient.getRoomLists().get(chosenRoom-2).roomID, wrapChatMessage(message));
//                gameClient.sendToServer(messageToSend);
//    		}
//    		else{
//    			escape = true;
//    		}
//    	}
//    }
//
//
//    /*
//        Wraps chat message in xml
//
//     */
//    public String wrapChatMessage(String msg){
//        msg = "<action type='"+GameMessage.MSG_CHAT+"'>"+msg+"</action>";
//        return msg;
//    }
//
//
//
//    public boolean handleMessage(String msg){
//    	System.out.println(msg);
//    	return true;
//    }
//
//    public boolean handleMessage(GameMessage msg){
//    	IXMLElement xml = ServerMessage.parseMessage(msg.msg);
//    	String type = xml.getAttribute("type", null);
//    	System.out.println(msg);
//
//    	switch(type){
//    		case GameMessage.MSG_CHAT:
//    			break;
//    		case GameMessage.MSG_JOIN_ROOM:
//    			break;
//    		case GameMessage.ACTION_ROOM_JOINED:
//    			System.out.println("Joined Room");
//    	}
//
//    	return true;
//    }
//    public void sendToServer(String msgType, int roomID)
//    {
//    	// before sending the message to the server, you need to (1) build the text of the message
//    	// as a string,  (2) compile the message by calling
//    	// the static method ServerMessage.compileGameMessage(msgType, roomID, actionMsg),
//    	// and (3) call the method gameClient.sendToServer() to send the compiled message.
//
//    	// For message types and message format, see the GameMessage API and the project notes
//    }
//
//}
