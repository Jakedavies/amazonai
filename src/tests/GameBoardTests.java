package tests;

import ai.AmazonSuccessorFunction;
import game.*;
import game.Vector;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by jakedavies on 15-02-28.
 */
public class GameBoardTests {
    public static void main(String[] args)
    {
        GameBoard board = new GameBoard();



        AmazonSuccessorFunction succer = new AmazonSuccessorFunction(board.state);
        board.state.throwStone(new Vector(7,7));
        board.state.throwStone(new Vector(5,5));
        board.state.throwStone(new Vector(2,1));











        SuccessorState[] suc = succer.getSuccessors();


        long startTime = System.nanoTime();

        for(SuccessorState s : suc){
            System.out.println("Queen's Best Move: \n Value: " + s.getBestMoveValue() + " Coordinates:" + s.getBestMove().toString());
            s.worstMove();
            System.out.println("Queen's Worst Move: \n Value: " + s.getWorstMoveValue() + " Coordinates:" + s.getWorstMove().toString());

        }
        long EndtTime = System.nanoTime();


        System.out.println(EndtTime - startTime);



    }
}
