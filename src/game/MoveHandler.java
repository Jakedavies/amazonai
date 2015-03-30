package game;

import net.n3.nanoxml.IXMLElement;
import ubco.ai.connection.ServerMessage;
import ubco.ai.games.GameMessage;

public class MoveHandler {

	/**
	* Create the server message for a move in the correct format
	*
	* @param roomID - Room to send message to.
	* @param action - The action of the queen moving
	* @return String of the message for the server.
	*/
	public static String createMove(int roomID, Action action){
		byte[]  arrow = action.getStoneThrow();
		System.out.println("TRANSLATED FROM: " +action.getXStart() + "," + action.getYStart());

		System.out.println("TRANSLATED TO: " +action.getXFinal() + "," + action.getyFinal());
		System.out.println("STONE: "+arrow[0] + "," + arrow[1]);
		String actionMsg = "<action type='" + GameMessage.ACTION_MOVE + "'><queen move='"
				+ getLetter(action.getYStart()) + action.getXStart() + "-" +
				getLetter(action.getyFinal()) + action.getXFinal() +
				"'></queen><arrow move='"+ getLetter(arrow[1]) + arrow[0] + "'></arrow></action>";
		String msg = ServerMessage.compileGameMessage(GameMessage.MSG_GAME, roomID, actionMsg);
		System.out.println(msg);
		return msg;
	}
	
	public static Action createMove(IXMLElement e, BoardStateByte board){
		 String queen = ((IXMLElement) e.getChildrenNamed("queen")
				 .firstElement())
				 .getAttribute("move", null);
		 String arrow = ((IXMLElement) e.getChildrenNamed("arrow")
				 .firstElement())
				 .getAttribute("move", null);
		 		 
		 byte[] original = new byte[2];
				 original[0] = Byte.valueOf(queen.substring(1, 2));
				 original[1] = getByte(queen.charAt(0));
		 System.out.println("Original by Enemy = X:" + original[0] + ",Y:" + original[1]);
		 
		 byte[] finish = new byte[2];
				 finish[0] = Byte.valueOf(queen.substring(4, 5));
				 finish[1] = getByte(queen.charAt(3));
		 System.out.println("Final by Enemy = X:" + finish[0] + ",Y:" + finish[1]);
		 
		 byte[] stone = new byte[2];
		 stone[0] = Byte.valueOf(arrow.substring(1, 2));
		 stone[1] = getByte(arrow.charAt(0));
		 System.out.println("Stone by Enemy = X:" + stone[0] + ", Y:" + stone[1]);
		 	 
		 return new Action(original, finish, stone, board);
	}
	
	private static char getLetter(byte c){
		switch(c){
		case(0):
			return 'a';
		case(1):
			return 'b';
		case(2):
			return 'c';
		case(3):
			return 'd';
		case(4):
			return 'e';
		case(5):
			return 'f';
		case(6):
			return 'g';
		case(7):
			return 'h';
		case(8):
			return 'i';
		case 9:
			return 'j';
		}
		return ' ';
	}
	
	private static byte getByte(char c){
		switch(c){
		case 'a':
			return 0;
		case 'b':
			return 1;
		case 'c':
			return 2;
		case 'd':
			return 3;
		case 'e':
			return 4;
		case 'f':
			return 5;
		case 'g':
			return 6;
		case 'h':
			return 7;
		case 'i':
			return 8;
		case 'j':
			return 9;
		}
		return -1;
	}
	
	private static byte getByteChar(char c){
		switch(c){
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case 'h':
			return 7;
		case 'i':
			return 8;
		case 'j':
			return 9;
		}
		return -1;
	}
}
