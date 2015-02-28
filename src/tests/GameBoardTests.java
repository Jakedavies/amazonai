package tests;

import ai.AmazonSuccessorFunction;
import game.GameBoard;
import java.util.*;

/**
 * Created by jakedavies on 15-02-28.
 */
public class GameBoardTests {
    public static void Main(String[] args)
    {
        GameBoard board = new GameBoard();
        AmazonSuccessorFunction succer = new AmazonSuccessorFunction(board.state);
        System.out.print(succer.getSuccessors().size());
    }
}
