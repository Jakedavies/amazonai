package tests;

import ai.MinMaxTree;
import game.*;

import java.util.ArrayList;

/**
 * Created by jakedavies on 15-03-22.
 */
public class MinMaxTreeTest {


    public static void main(String[] args){
        GameBoard gb = new GameBoard(true);

        BoardStateByte b = new BoardStateByte();
        BoardStateByte bcontinued = null;

for(int i = 0 ; i < 5; i ++) {

    long timeOld = System.currentTimeMillis();
    MinMaxTree tree;
    if(bcontinued == null){
        tree = new MinMaxTree(10, b);
    }
    else
    tree = new MinMaxTree(10, bcontinued);


    System.out.println(tree.getRootLevelActions().size());
    System.out.println("Total actions generatated" + tree.countTotalActions(tree.getRootLevelActions()));
    Action bestMove = tree.getBestMove();
    long timeNew = System.currentTimeMillis();
    System.out.println("TOTAL TIME: " + (timeOld - timeNew));

    GameBoard gb2 = new GameBoard(false);
    gb2.setNewBoard(bestMove.getParent());
    gb2.pack();
    gb2.setResizable(true);
    gb2.setVisible(true);


    gb2.setNewBoard(bestMove.getParent());
    bcontinued = bestMove.getParent();
    gb2.reDraw();
}


    }
}
