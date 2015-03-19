package tests;

import ai.AmazonHeuristic;
import ai.AmazonSuccessorByte;
import ai.AmazonSuccessorFunction;
//import ai.AmazonSuccessorFunctionVector2;
import game.*;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.System.gc;
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


for(int i = 0; i < 999; i ++) {
    long old = System.currentTimeMillis();

    BoardStateByte b = new BoardStateByte();
    AmazonSuccessorByte sf = new AmazonSuccessorByte();
    int count = 0;

    ArrayList<BoardStateByte> moves = new ArrayList<>();
    ArrayList<BoardStateByte> moves2 = new ArrayList<>();


    moves.addAll(sf.generateTreeLevelThreaded(b));

        count = 1;
        for(BoardStateByte b2 : moves){
            moves2.addAll(sf.generateTreeLevelThreaded(b2));
            System.out.println(moves2.size());
            count ++;

        }


    long fin = System.currentTimeMillis();


    System.out.println(moves.size());
    System.out.println(fin - old);
}



//        ArrayList<BoardState2> tot = new ArrayList<>();





        }

//
















//        }
    }

