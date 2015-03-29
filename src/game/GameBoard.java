package game; /**
* Created by nolan on 20/01/15.
* GUI for project.
*
*/


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;


public class GameBoard extends JFrame {

    JDesktopPane Pane;
    JPanel Board;

    JTextArea console = new JTextArea();
    final Dimension paneSize = new Dimension(900, 900);
    final Dimension boardSize = new Dimension(600,600);
    final Dimension textAreaSize = new Dimension(200,200);
    public BoardStateByte state;

    public void setNewBoard(BoardStateByte state){
        this.state = state;
    }


    public void write(String message){
        console.append(message);
    }

    public Scanner getScanner(){
        String s = console.getText();
        Scanner scan = new Scanner(s);
        return  scan;
    }




    // Set up board
    public GameBoard(boolean isnew){

        if(isnew) {
            state = new BoardStateByte();
        }

        //Set up board
        Board = new JPanel();
        Board.setLayout(new GridLayout(10, 10));
        Board.setPreferredSize(boardSize);
        Board.setBounds(0, 0, boardSize.width, boardSize.height);


        /*
            Draw the squares for the chess board.
         */
        for (int i = 0; i < 100; i++) {
            JPanel square = new JPanel(new BorderLayout());
            Board.add(square);

            int row = (i / 10) % 2;
            if (row == 0){
                square.setBackground( i % 2 == 0 ? Color.white : Color.gray);
            square.add(new JLabel(String.valueOf(i)));}
            else {
                square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
                square.add(new JLabel(String.valueOf(i)));
            }

        }

        // Add Text Area
        console.setBounds(0,0, textAreaSize.width, textAreaSize.height);


        //Create split pane
        Pane = new JDesktopPane();
        Pane.add(Board);
        Pane.add(console);
        Pane.setPreferredSize(paneSize);

        if(isnew)
            this.reDraw();


    }


    public BoardStateByte getBoard(){
        return this.state;
    }




    ArrayList<JPanel> removal = new ArrayList<>();
    private final String iconWhitePAC = "./images/wakaQueen.png";
    private String iconBlackPAC = "./images/wakaQueenB.png";
    private final String icWB = "./images/wakaBlue.png";


    public void reDraw(){

        Board.removeAll();
          /*
            Draw the squares for the chess board.
         */
        for (int i = 0; i < 100; i++) {
            JPanel square = new JPanel(new BorderLayout());
            Board.add(square);

            int row = (i / 10) % 2;
            if (row == 0){
                square.setBackground( i % 2 == 0 ? Color.white : Color.gray);
                square.add(new JLabel(String.valueOf(i)));}
            else {
                square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
                square.add(new JLabel(String.valueOf(i)));
            }

        }

             /*
        Test Display Pieces
         */

//        for(int i = 0; i < removal.size(); i ++){
//
//            JComponent j = removal.remove(i);
//            remove(j);
//            j.revalidate();
//
//            Board.updateUI();
//            Board.revalidate();
//            Board.repaint();
//
//        }

        byte[][] WhiteQueens = state.getWhiteQueens();
        byte[][] BlackQueens = state.getBlackQueens();
        byte[][] Stones = state.getStones();


        for(int i =0; i < 4; i ++){
            int xWhite = WhiteQueens[i][0];
            int yWhite = WhiteQueens[i][1];

            int xBlack = BlackQueens[i][0];
            int yBlack = BlackQueens[i][1];

            //xWhite + yWhite
            JPanel panel1 = (JPanel) Board.getComponent(xWhite*10 + yWhite);
            panel1.add(new JLabel(new ImageIcon(iconWhitePAC)));

            JPanel panel2 = (JPanel) Board.getComponent(xBlack*10 + yBlack);
            panel2.add(new JLabel(new ImageIcon(iconBlackPAC)));

            removal.add(panel1);
            removal.add(panel2);

            panel1.revalidate();
            panel2.revalidate();

        }

        for(int i = 0; i < state.getLastStone(); i ++ ){
            int sx = Stones[i][0];
            int sy = Stones[i][1];

            JPanel panel = (JPanel) Board.getComponent(sx*10+sy);
            panel.add(new JLabel(new ImageIcon(icWB)));
            panel.repaint();
        }


        getContentPane().add(Pane);


        Board.repaint();
        Board.updateUI();
        Board.repaint();



    }








}
