package tests;

import ai.AmazonSuccessorByte;
//import ai.AmazonSuccessorFunctionVector2;
import game.*;
import game.BoardState;

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


    long old = System.currentTimeMillis();

    BoardStateByte b = new BoardStateByte();
    AmazonSuccessorByte sf = new AmazonSuccessorByte();
    int count = 0;

        ArrayList<Action> moves = new ArrayList<>();
        ArrayList<Action> moves2 = new ArrayList<>();
        ArrayList<Action> moves3 = new ArrayList<>();


    moves.addAll(sf.generateTreeLevel(b));

        count = 1;
        for(Action b2 : moves){

//            for(byte[] bt : b2.getParent().getStones()){
//                System.out.println(bt[0] + "," + bt[1]);
//
//            }

            BoardStateByte v = b2.makeThisMove();
            moves2.addAll(sf.generateTreeLevel(v));

//            for(Action b3: moves2){
//                System.out.println(b3.getValue());
//
//            }

        }

        long fin = System.currentTimeMillis();
        System.out.println(fin - old);
        System.out.println("-------------------");
    System.out.println(moves2.size());
        System.out.println(moves.size() * moves.size());
}



//        ArrayList<BoardState2> tot = new ArrayList<>();





        }

//
















//        }


