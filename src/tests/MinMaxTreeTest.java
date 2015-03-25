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
        System.out.println("-------- Non Threaded --------");
        System.out.println(tree.getRootLevelActions().size());
        System.out.println("Total actions generatated " + tree.countTotalActions(tree.getRootLevelActions()));
        Action bestMove = tree.getBestMove();
        System.out.println(System.currentTimeMillis()-start);
        
        
        for(int i = 2; i <= 10; i++){
        	start = System.currentTimeMillis();
        	b = new BoardStateByte();
        	tree = new MinMaxTree(2,b);
        	System.out.println("-------- Threaded --------");
        	System.out.println("\t Thread Count = " + i);
        	System.out.println(tree.getRootLevelActions().size());
        	System.out.println("Total actions generatated " + tree.countTotalActions(tree.getRootLevelActions()));
        	bestMove = tree.getBestMoveThreaded(i);
        	System.out.println(System.currentTimeMillis()-start);
        }



    }
}