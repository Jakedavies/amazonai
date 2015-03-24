package tests;

import ai.AmazonSuccessorByte;
//import ai.AmazonSuccessorFunctionVector2;
import ai.MinMaxTree;
import game.*;

import java.util.ArrayList;
import java.util.Collections;

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


        GameBoard gb = new GameBoard(true);
        gb.pack();
        gb.setResizable(true);
        gb.setVisible(true);


    long old = System.currentTimeMillis();

    BoardStateByte b = new BoardStateByte();
    AmazonSuccessorByte sf = new AmazonSuccessorByte();
    int count = 0;

        ArrayList<Action> moves = new ArrayList<>();
      ArrayList<Action> moves2 = new ArrayList<>();
        moves.addAll(sf.generateTreeLevel(b, true));

//        Collections.sort(moves, Action.ID_ASC);
//        for(int i = 0; i < moves.size(); i ++){
//            System.out.print(moves.remove(0).getValue() + "||");
//        }


//        ArrayList<Action> moves3 = new ArrayList<>();

        count = 1;
        for(Action b2 : moves){
            BoardStateByte v = b2.makeThisMove();
            moves2.addAll(sf.generateTreeLevel(v, true));

       }
//        for(Action b3 : moves2){
//            BoardStateByte v2 = b3.makeThisMove();
//            moves3.addAll(sf.generateTreeLevel(v2));
//            System.out.println(moves3.size());
//            long fin = System.currentTimeMillis();
//            System.out.printf("-----------------------------");
//            System.out.println("TOTAL TIME: " + (fin - old)/1000 + "s");
//        }


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
        System.out.println("Number of moves in L1:  " +  (moves.size()));
        System.out.println("Number of moves in L2:  " +  (moves2.size()));

}



//        ArrayList<BoardState2> tot = new ArrayList<>();
BoardStateByte b = new BoardStateByte();







        }

//
















//        }


