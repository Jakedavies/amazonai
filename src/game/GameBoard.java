//package game; /**
// * Created by nolan on 20/01/15.
// * GUI for project.
// *
// */
//
//import recycleBin.BoardState2;
//
//import javax.swing.*;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//
//public class GameBoard extends JFrame {
//
//    JSplitPane Pane;
//    JPanel Board;
//
//    JTextArea console = new JTextArea();
//    final Dimension paneSize = new Dimension(800, 800);
//    final Dimension boardSize = new Dimension(600,600);
//    final Dimension textAreaSize = new Dimension(200,200);
//    public BoardState2 state;
//
//
//    public void write(String message){
//        console.append(message);
//    }
//
//    public Scanner getScanner(){
//        String s = console.getText();
//        Scanner scan = new Scanner(s);
//        return  scan;
//    }
//
//
//
//
//    // Set up board
//    public GameBoard(){
//
//        state = new BoardState2();
//
//        //Set up board
//        Board = new JPanel();
//        Board.setLayout(new GridLayout(10, 10));
//        Board.setPreferredSize(boardSize);
//        Board.setBounds(0, 0, boardSize.width, boardSize.height);
//
//
//        /*
//            Draw the squares for the chess board.
//         */
//        for (int i = 0; i < 99; i++) {
//            JPanel square = new JPanel(new BorderLayout());
//            Board.add(square);
//
//            int row = (i / 10) % 2;
//            if (row == 0)
//                square.setBackground( i % 2 == 0 ? Color.white : Color.gray);
//            else
//                square.setBackground( i % 2 == 0 ? Color.gray: Color.white);
//        }
//
//        // Add Text Area
//        console.setBounds(0,0, textAreaSize.width, textAreaSize.height);
//
//
//        //Create split pane
//        Pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, Board, console);
//        Pane.setPreferredSize(paneSize);
//        reDraw();
//
//
//
//    }
//
//    ArrayList<JPanel> removal = new ArrayList<>();
//
//
//    public void reDraw(){
////             /*
////        Test Display Pieces
////         */
////
////        for(int i = 0; i < removal.size(); i ++){
////            JComponent j = removal.remove(i);
////            remove(j);
////            Board.updateUI();
////            Board.revalidate();
////            Board.repaint();
////
////        }
////
////        for(Queen s : state.){
////            JPanel panel = (JPanel) Board.getComponent(s.position.getOneDimensional());
////            panel.add(s.getIcon());
////            panel.revalidate();
////            removal.add(panel);
////
////
////
////        }
////
////        for(Stone s: state.getStones()){
////            JPanel panel = (JPanel) Board.getComponent(s.position.getOneDimensional());
////            panel.add(s.getIcon());
////            panel.revalidate();
////
////        }
////
////        //add to display
////        getContentPane().add(Pane);
////
////        Board.repaint();
//
//
//
//    }
//
//
//
//
//
//
//
//
//}
