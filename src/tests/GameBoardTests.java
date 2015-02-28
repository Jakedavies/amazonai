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
        long startTime = System.nanoTime();
        System.out.println(Arrays.toString(succer.getSuccessors()));
        long estm = System.nanoTime();
        System.out.println(TimeUnit.NANOSECONDS.toMillis(estm-startTime));


    }
}
