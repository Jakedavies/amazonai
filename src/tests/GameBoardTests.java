package tests;

import ai.AmazonSuccessorFunction;
import game.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by jakedavies on 15-02-28.
 */
public class GameBoardTests {
    public static void main(String[] args)
    {
        GameBoard board = new GameBoard();
        AmazonSuccessorFunction succer = new AmazonSuccessorFunction(new BoardState());

        ConcurrentLinkedQueue<game.Vector> v = succer.getSuccessors();

        for(game.Vector q : v){
            System.out.println(q.toString());
        }
    }
}
