
import ubco.ai.GameRoom;
import ubco.ai.games.GameClient;
import ubco.ai.games.GameMessage;
import ubco.ai.connection.ServerMessage;
import net.n3.nanoxml.*;

import java.util.ArrayList;
import java.util.*;


public class GamePlayer implements ubco.ai.games.GamePlayer{

    GameClient gameClient = null;
    ArrayList<GameRoom> roomlist;
    public GamePlayer(String name, String passwd){
        gameClient = new GameClient(name,passwd,this);
        roomlist = gameClient.getRoomLists();
        for(GameRoom gr : roomlist){
            System.out.println(gr.toString());
            System.out.println("With: " + gr.userCount + " users");
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Which game room would you like to join?");

        Integer chosenRoom = sc.nextInt();
        System.out.println(chosenRoom);
        gameClient.joinGameRoom(chosenRoom.toString());





        System.out.println("You are connected to room:  " + roomlist.get(chosenRoom-2).roomName );
        System.out.println("There are: " + roomlist.get(chosenRoom-2).userCount + "users in the room currently.");

        boolean escape = false;
        while(!escape){
            System.out.println("Would send a message or type 'n' to quit...");
            String message = sc.nextLine();

            if("n".equalsIgnoreCase(message)){
                gameClient.sendToServer(message);
            }
            else{
                escape = true;
            }

        }


    }
    public boolean handleMessage(String msg){
        System.out.println(msg);
        return true;
    }
    public boolean handleMessage(GameMessage msg){
        IXMLElement xml = ServerMessage.parseMessage(msg.msg);
        String type = xml.getAttribute("type", "WRONG!");
        System.out.println(msg);

        if(GameMessage.ACTION_ROOM_JOINED.equals(msg)){

        }
        else if(msg.equals(GameMessage.ACTION_GAME_START)){

        }
        else if(msg.equals(GameMessage.ACTION_MOVE))
        {

        }
        return true;
    }
    public void sendToServer(String msgType, int roomID)
    {
        // before sending the message to the server, you need to (1) build the text of the message
        // as a string,  (2) compile the message by calling
        // the static method ServerMessage.compileGameMessage(msgType, roomID, actionMsg),
        // and (3) call the method gameClient.sendToServer() to send the compiled message.

        // For message types and message format, see the GameMessage API and the project notes
    }

}
