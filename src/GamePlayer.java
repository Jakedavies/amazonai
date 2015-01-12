
import ubco.ai.games.GameClient;
import ubco.ai.games.GameMessage;
import ubco.ai.connection.ServerMessage;
import net.n3.nanoxml.*;


public class GamePlayer implements ubco.ai.games.GamePlayer{

    GameClient gameClient = null;

    public GamePlayer(String name, String passwd){
        gameClient = new GameClient(name,passwd,this);
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
