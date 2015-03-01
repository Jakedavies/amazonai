package tests;

import ai.AmazonSuccessorFunction;
import game.GamePlayer;
import game.Position;
import game.SuccessorState;

import static java.lang.System.gc;

/**
 * Created by nolan on 28/02/15.
 */
public class testOneMove {

public static void main(String[] args) {
    GamePlayer gamePlayer = new GamePlayer("nsulldsshsnsssterss8sd", "password");

    AmazonSuccessorFunction succer = new AmazonSuccessorFunction(gamePlayer.getGameBoard().state);

    SuccessorState[] suc = succer.getSuccessors();


        game.Vector bestMove = null;
        Position oldQueen = null;
        int bestMoveValue = 0;
        gamePlayer.getGameBoard().state.throwStone(new game.Vector(4,5));


        for (SuccessorState s : suc) {
            if (s.getBestMoveValue() > bestMoveValue) {
                bestMove = s.getBestMove();
                oldQueen = s.getPositionObject();
            }

        }


        gamePlayer.getGameBoard().state.moveQueen(oldQueen.position, bestMove);
        gamePlayer.getGameBoard().reDraw();


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

        gamePlayer.getGameBoard().state.throwStone(bestMove);
        gamePlayer.getGameBoard().reDraw();


}

}
