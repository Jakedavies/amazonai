package tests;

import ai.AmazonSuccessorByte;
//import ai.AmazonSuccessorFunctionVector2;
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


        GameBoard gb = new GameBoard();
        gb.pack();
        gb.setResizable(true);
        gb.setVisible(true);


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

            BoardStateByte v = b2.makeThisMove();
            moves2.addAll(sf.generateTreeLevel(v));




        }
        for(Action b3 : moves2){
            BoardStateByte v2 = b3.makeThisMove();
            moves3.addAll(sf.generateTreeLevel(v2));
            System.out.println(moves3.size());
            long fin = System.currentTimeMillis();
            System.out.printf("-----------------------------");
            System.out.println("TOTAL TIME: " + (fin - old)/1000 + "s");
        }


//        BoardStateByte bnew = moves2.get(1001).makeThisMove();
//        System.out.println(bnew.getLastStone());
//        System.out.println("-------------------");
//
//        for(int i = 0; i < bnew.getLastStone(); i ++){
//            System.out.println(bnew.getStones()[i][0]);
//            System.out.println(bnew.getStones()[i][1]);
//            System.out.println("-------------------");
//
//        }
//
//
//        gb.setNewBoard(bnew);





        long fin = System.currentTimeMillis();
        System.out.println("TOTAL TIME: " + (fin - old) + "ms");
        System.out.println("-------------------");
        System.out.println("ACTUAL VALUE CALCULATED: " + moves2.size());
        System.out.println("APPROXIMATED VALUE OF L1 SQUARED:  " +  (moves.size() * moves.size()));
}



//        ArrayList<BoardState2> tot = new ArrayList<>();





        }

//
















//        }


