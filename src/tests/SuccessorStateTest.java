package tests;

import ai.AmazonHeuristic;
import ai.AmazonSuccessorFunction;
import game.*;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Created by nolan on 12/03/15.
 */
public class SuccessorStateTest {

    public static int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }


    public static void main(String[] args) throws InterruptedException {
        GamePlayer gamePlayer = new GamePlayer("nullstesrs3ss8sd", "password");

        GameBoard board = gamePlayer.getGameBoard();
        BoardState bs = board.state;



        for(int i = 0; i < 99 ; i ++){
            BoardState newstate = gamePlayer.getGameBoard().state;
            AmazonSuccessorFunction sf = new AmazonSuccessorFunction(newstate);
            SuccessorState[] st = sf.getSuccessors();
            Vector bestMove = null;
            int maxScore = 0;
            Queen toMove = null;
            for(SuccessorState s: st){
                System.out.println(s + "" + st);
                if(s.getBestMoveValue() > maxScore) {
                    bestMove = s.getBestMove();
                    maxScore = s.getBestMoveValue();
                    toMove = (Queen) s.getPositionObject();
                }
            }
            System.out.println("BEST MOVE: " + bestMove);



            newstate.moveQueen(toMove.position, bestMove);
             boolean thrower = false;
            while (!thrower) {
                int x = randomWithRange(0, 10);
                int y = randomWithRange(0, 10);
                thrower = newstate.isValidPosition(new Vector(x, y));
                if(thrower){
                    newstate.throwStone(new Vector(x, y));
                }

            }


            gamePlayer.getGameBoard().state = null;
            gamePlayer.getGameBoard().state = newstate;
            gamePlayer.getGameBoard().reDraw();
            sleep(1000);

        }
        System.out.println("DONEE!!");





//        }
    }
}
