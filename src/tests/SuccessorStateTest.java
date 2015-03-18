package tests;

import ai.AmazonHeuristic;
import ai.AmazonSuccessorFunction;
import game.*;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

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


        String randomJoin = "" + randomWithRange(2,90);
        GamePlayer gamePlayer = new GamePlayer("nsusssSd" + randomJoin, "password");

        GameBoard board = gamePlayer.getGameBoard();

            BoardState newstate = board.state.clone();

            AmazonSuccessorFunction sf = new AmazonSuccessorFunction(newstate);


        for(int j = 0; j < 10; j ++) {


            int count = 0;
            long timeold = System.currentTimeMillis();
            ArrayList<BoardState> states = sf.generateTreeLevel();


            ArrayList<BoardState> states2 = new ArrayList<>();


            for (BoardState a : states) {
                count++;

                AmazonSuccessorFunction sf2 = new AmazonSuccessorFunction(a);
                ArrayList<BoardState> bs = sf2.generateTreeLevel();
                count += bs.size();

            }

            long timenew = System.currentTimeMillis();


            System.out.println(count);

            String noThread = ("IN " + (timenew - timeold));


            count = 0;
            timeold = System.currentTimeMillis();
            states = sf.generateTreeLevelThreaded();


            states2 = new ArrayList<>();


            for (BoardState a : states) {
                AmazonSuccessorFunction sf2 = new AmazonSuccessorFunction(a);
                count++;

                ArrayList<BoardState> bs = sf2.generateTreeLevelThreaded();
                count += bs.size();

            }

            timenew = System.currentTimeMillis();


            System.out.println(count);

            String Thread = ("IN " + (timenew - timeold));

            System.out.println(Thread);
            System.out.println(noThread);
        }

//        long old = System.nanoTime();
//        for(Queen q: newstate.getFriendlyQueens()){
//            sf.getAllPositions(q.position);
//        }
//        long newT = System.nanoTime();
//        System.out.println("IN " + (newT - old));
//
//         old = System.nanoTime();
//        for(Queen q: newstate.getFriendlyQueens()){
//            sf.getAllPositionsThreaded(q.position);
//        }
//         newT = System.nanoTime();
//        System.out.println("IN " + (newT - old));








        sleep(1000);


    }





//        }
    }

