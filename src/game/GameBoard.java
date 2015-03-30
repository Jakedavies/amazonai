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
    final String[] labels = new String[]{
    		"A,9", "B,9", "C,9", "D,9", "E,9", "F,9", "G,9", "H,9", "I,9", "J,9",
    		"A,8", "B,8", "C,8", "D,8", "E,8", "F,8", "G,8", "H,8", "I,8", "J,8",
    		"A,7", "B,7", "C,7", "D,7", "E,7", "F,7", "G,7", "H,7", "I,7", "J,7",
    		"A,6", "B,6", "C,6", "D,6", "E,6", "F,6", "G,6", "H,6", "I,6", "J,6",
    		"A,5", "B,5", "C,5", "D,5", "E,5", "F,5", "G,5", "H,5", "I,5", "J,5",
    		"A,4", "B,4", "C,4", "D,4", "E,4", "F,4", "G,4", "H,4", "I,4", "J,4",
    		"A,3", "B,3", "C,3", "D,3", "E,3", "F,3", "G,3", "H,3", "I,3", "J,3",
    		"A,2", "B,2", "C,2", "D,2", "E,2", "F,2", "G,2", "H,2", "I,2", "J,2",
    		"A,1", "B,1", "C,1", "D,1", "E,1", "F,1", "G,1", "H,1", "I,1", "J,1",
    		"A,0", "B,0", "C,0", "D,0", "E,0", "F,0", "G,0", "H,0", "I,0", "J,0",
    };
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
            }else {
                square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
            }
            square.add(new JLabel(labels[i]));

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
            }else {
                square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
            }
            square.add(new JLabel(labels[i]));

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
            JPanel panel1 = (JPanel) Board.getComponent((9-xWhite)*10 + yWhite);
            panel1.add(new JLabel(new ImageIcon(iconWhitePAC)));

            JPanel panel2 = (JPanel) Board.getComponent((9-xBlack)*10 + yBlack);
            panel2.add(new JLabel(new ImageIcon(iconBlackPAC)));

            removal.add(panel1);
            removal.add(panel2);

            panel1.revalidate();
            panel2.revalidate();

        }

        for(int i = 0; i < state.getLastStone(); i ++ ){
            int sx = Stones[i][0];
            int sy = Stones[i][1];

            JPanel panel = (JPanel) Board.getComponent((9-sx)*10+sy);
            panel.add(new JLabel(new ImageIcon(icWB)));
            panel.repaint();
        }


        getContentPane().add(Pane);


        Board.repaint();
        Board.updateUI();
        Board.repaint();



    }








}
