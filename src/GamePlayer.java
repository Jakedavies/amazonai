import net.n3.nanoxml.IXMLElement;
import ubco.ai.GameRoom;
import ubco.ai.connection.ServerMessage;
import ubco.ai.games.GameClient;
import ubco.ai.games.GameMessage;

import java.util.ArrayList;
import java.util.Scanner;



public class GamePlayer implements ubco.ai.games.GamePlayer{


    GameClient gameClient = null;
    ArrayList<GameRoom> roomlist;
    GameBoard frame = null;

    public GamePlayer(String name, String passwd){


       frame = new GameBoard();
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);

        gameClient = new GameClient(name,passwd,this);
        roomlist = gameClient.getRoomLists();



        //Print out the room list to user
        for(GameRoom gr : roomlist){
            frame.write(gr.toString());
            frame.write(" With: " + gr.userCount + " users" + "\n");
        }


        //Scan room number and connect to room.
        Scanner sc = new Scanner(System.in);
        frame.write("Which game room would you like to join?");
        Integer chosenRoom = sc.nextInt();
        System.out.println(chosenRoom);
        gameClient.joinGameRoom(chosenRoom.toString());


        /*
        Most of this is useless and just for testing...
         */

        boolean escape = false;
        while(!escape){

            //debug
            System.out.println("You are connected to room:  " + gameClient.getRoomLists().get(chosenRoom-2).roomName );
            System.out.println("There are: " + gameClient.getRoomLists().get(chosenRoom-2).userCount + " users in the room currently.");



            System.out.println("Send a message or type 'n' to quit...");

            String message = sc.next();


            if(!"n".equalsIgnoreCase(message)){

                /*
                Looks like we need to compile the message (see yong's code snippet below this...)
                Broken at the moment.
                 */
                String messageToSend = ServerMessage.compileGameMessage("MSG_CHAT", chosenRoom, message);
                gameClient.sendToServer(messageToSend);
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
