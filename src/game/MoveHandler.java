//package game;
//import ubco.ai.connection.ServerMessage;
//import ubco.ai.games.GameMessage;
//
//public class MoveHandler {
//
//	/**
//	* Create the server message for a move in the correct format
//	*
//	* @param roomID - Room to send message to.
//	* @param startPos - Vector of Starting Position of Queen
//	* @param endPos - Vector of Ending position of Queen
//	* @param arrow - Vector of where arrow goes.
//	* @return String of the message for the server.
//	*/
//	public static String move(int roomID, Vector startPos, Vector endPos, Vector arrow){
//		String actionMsg = "<action type='" + GameMessage.ACTION_MOVE + "'><queen move='"
//				+ getLetter(startPos.y) + startPos.x + "-" + getLetter(endPos.y) + endPos.x +
//				"'></queen><arrow move='"+ getLetter(arrow.y) + arrow.x + "'></arrow></action>";
//		String msg = ServerMessage.compileGameMessage(GameMessage.MSG_GAME, roomID, actionMsg);
//		System.out.println(msg);
//		return msg;
//	}
//
//	private static char getLetter(int c){
//		switch(c){
//		case(0):
//			return 'a';
//		case(1):
//			return 'b';
//		case(2):
//			return 'c';
//		case(3):
//			return 'd';
//		case(4):
//			return 'e';
//		case(5):
//			return 'f';
//		case(6):
//			return 'g';
//		case(7):
//			return 'h';
//		case(8):
//			return 'i';
//		case(9):
//			return 'j';
//		}
//		return ' ';
//	}
//}
