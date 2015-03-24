package tests;

import ai.MinMaxTree;
import game.*;

import java.util.ArrayList;

/**
 * Created by jakedavies on 15-03-22.
 */
public class MinMaxTreeTest {
    public static void main(String[] args){
        long start = System.currentTimeMillis();
        BoardStateByte b = new BoardStateByte();
        MinMaxTree tree = new MinMaxTree(2,b);
        System.out.println(tree.getRootLevelActions().size());
        System.out.println("Total actions generatated" + tree.countTotalActions(tree.getRootLevelActions()));
        Action bestMove = tree.getBestMove();
        System.out.println(bestMove.getXFinal() + "," + bestMove.getyFinal());
        System.out.println(System.currentTimeMillis()-start);



    }
}