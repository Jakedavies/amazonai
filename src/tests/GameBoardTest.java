//package tests;
//
//import ai.MinMaxTree;
//import game.Action;
//import game.GameBoard;
//
//import javax.swing.*;
//import java.util.Arrays;
//
///**
// * Created by nolan on 26/03/15.
// */
//public class GameBoardTest {
//    public static void main(String[] args){
//
//        GameBoard frame = new GameBoard(true);
//
//            frame.pack();
//            frame.setResizable(true);
//            frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setBounds(900,900,900,900);
//            frame.setVisible(true);
//
//
//        MinMaxTree tree = new MinMaxTree(2, frame.getBoard());
//        Action a = tree.getBestMoveThreaded(7);
//
//
//        System.out.println(a.getParent().getStones()[a.getParent().getLastStone()][0]);
//        System.out.println(a.getParent().getStones()[a.getParent().getLastStone()][1]);
//
//        frame.setNewBoard(a.getParent());
//        frame.reDraw();
//
//    int count = 0;
//        try {
//            for (int i = 0; i < 100; i++) {
//                tree = new MinMaxTree(2, frame.getBoard());
//                a = tree.getBestMoveThreaded(7);
//
//
//                System.out.println(a.getParent().getStones()[a.getParent().getLastStone()][0]);
//                System.out.println(a.getParent().getStones()[a.getParent().getLastStone()][1]);
//
//                frame.setNewBoard(a.getParent());
//                frame.reDraw();
//                count = i;
//            }
//        }
//        catch(NullPointerException e){
//            System.out.println("GAME OVER!!");
//        }
//        System.out.println(count);
//
//
//
//
//
//    }
//
//
//
//}
