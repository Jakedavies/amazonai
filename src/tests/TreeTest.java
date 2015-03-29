package tests;

import game.BoardStateByte;
import Tree.Tree;
import Tree.Node;
import game.GameBoard;

/**
 * Created by nolan on 28/03/15.
 */
public class TreeTest {
    public static void main(String[] args){
        GameBoard frame = new GameBoard(true);

        frame.pack();
        frame.setResizable(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setBounds(900, 900, 900, 900);
        frame.setVisible(true);


        Tree t = new Tree(frame.getBoard());
        t.generateDepthsOurMoveIsWhite(true);
        Node n =t.getBestMoveAsWhite();
        System.out.println(n);

        frame.setNewBoard(n.getAction().makeThisMove());
        frame.reDraw();


        int count = 1;
        try {
            for (int i = 1; i < 100; i++) {
                long startTime = System.nanoTime();

                t = new Tree(frame.getBoard());
                if(i%2==0) {
                    t.generateDepthsOurMoveIsWhite(true);
                    n = t.getBestMoveAsWhite();
                    System.out.println(n);
                    count++;
                }
                else{
                    t.generateDepthsOurMoveIsBlack(false);
                    n = t.getBestMoveAsBlack();
                    System.out.println(n);
                    count++;
                }

                frame.setNewBoard(n.getAction().makeThisMove());
                frame.reDraw();
                System.out.println("MOVED IN: " + (System.currentTimeMillis()-startTime));
            }
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("MADE: " + count + " Moves");
        }
    }


}
