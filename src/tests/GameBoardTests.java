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



        SuccessorState[] suc = succer.getSuccessors();

        for(SuccessorState s : suc){
            System.out.println(s.getBestMoveValue());
        }



    }
}
