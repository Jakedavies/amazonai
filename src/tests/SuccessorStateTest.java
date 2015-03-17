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

        String randomJoin = "" + randomWithRange(2,90);
        GamePlayer gamePlayer = new GamePlayer("nsusssSd" + randomJoin, "password");

        GameBoard board = gamePlayer.getGameBoard();

            BoardState newstate = board.state.clone();

            AmazonSuccessorFunction sf = new AmazonSuccessorFunction(newstate);


        long timeold = System.currentTimeMillis();


                ArrayList<ArrayList<BoardState>> moves = sf.generateTreeLevel();
                for (ArrayList<BoardState> a : moves) {
                    for(BoardState b : a){
                        AmazonSuccessorFunction sf2 = new AmazonSuccessorFunction(b);
                        sf2.generateTreeLevel();
                    }
                }

        long timenew= System.currentTimeMillis();

        System.out.println("IN " + (timenew - timeold));






            sleep(1000);


    }





//        }
    }

