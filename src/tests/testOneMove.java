package tests;

import ai.AmazonSuccessorFunction;
import game.*;
import game.Vector;

import java.util.*;

import static java.lang.System.gc;

/**
 * Created by nolan on 28/02/15.
 */
public class testOneMove {

public static void main(String[] args) {
    GamePlayer gamePlayer = new GamePlayer("nsulldsshsnssssssstesrwss8ssd", "password");

    AmazonSuccessorFunction succer = new AmazonSuccessorFunction(gamePlayer.getGameBoard().state);

    SuccessorState[] suc = succer.getSuccessors();

        game.Vector bestMove = null;
        Position oldQueen = null;
        int bestMoveValue = 0;
    gamePlayer.getGameBoard().reDraw();


        for (SuccessorState s : suc) {
            if (s.getBestMoveValue() > bestMoveValue) {
                bestMove = s.getBestMove();
                oldQueen = s.getPositionObject();
            }

        }


        gamePlayer.getGameBoard().state.moveQueen(oldQueen.position, bestMove);



        gc();
        succer = new AmazonSuccessorFunction(gamePlayer.getGameBoard().state);

        suc = succer.getSuccessors();


        bestMove = null;
        bestMoveValue = 0;

        for (SuccessorState s : suc) {
            if (s.getBestMoveValue() > bestMoveValue) {
                bestMove = s.getBestMove();
            }

        }


        Scanner sc = new Scanner(System.in);
        gamePlayer.getGameBoard().state.throwStone(new Vector(5,5));
         gamePlayer.getGameBoard().reDraw();
    gamePlayer.getGameBoard().reDraw();


}

}
