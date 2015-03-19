package tests;

//import ai.AmazonSuccessorFunction;
//import game.*;
//import game.Vector;
//
//import java.util.*;
//
//import static java.lang.System.gc;
//import static java.lang.Thread.sleep;
//
///**
// * Created by nolan on 28/02/15.
// */
//public class testOneMove {
//
//    public static void main(String[] args) throws InterruptedException {
//
//        GamePlayer gamePlayer = new GamePlayer("nullspoissshhsnssters8sd","password");
//
//        GameBoard board = gamePlayer.getGameBoard();
//        BoardState bs = board.state;
//        ArrayList<Queen> fq = bs.getFriendlyQueens();
//
//        Position last = null;
//        for(Queen q: fq){
//            Position npos = new Position(q.getX(), q.getY()-1);
//            last = npos;
//            //q.move(npos.position, bs);
//            bs.throwStone(new Vector(npos.getX(), npos.getY()+1));
//            board.reDraw();
//            sleep(5000);
//        }
//        sleep(5000);
//
//
//    }
//
//}
