/**
 * Created by nolan on 20/01/15.
 * GUI for project.
 *
 */

import javax.swing.*;
import java.awt.*;
public class GameBoard extends JFrame {

    JSplitPane Pane;
    JPanel Board;
    JLabel Piece;
    private int xShift;
    private int yShift;
    Dimension paneSize = new Dimension(800, 800);
    Dimension boardSize = new Dimension(600,600);
    Dimension textAreaSize = new Dimension(200,200);




    // Set up board
    public GameBoard(){

        //Set up board
        Board = new JPanel();
        Board.setLayout(new GridLayout(8, 8));
        Board.setPreferredSize(boardSize);
        Board.setBounds(0, 0, boardSize.width, boardSize.height);


        /*
            Draw the squares for the chess board.
         */
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            Board.add(square);

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground( i % 2 == 0 ? Color.white : Color.gray);
            else
                square.setBackground( i % 2 == 0 ? Color.gray: Color.white);
        }

        // Add Text Area
        JTextArea console = new JTextArea();
        console.setBounds(0,0, textAreaSize.width, textAreaSize.height);


        //Create split pane
        Pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, Board, console);
        Pane.setPreferredSize(paneSize);

        //add to display
        getContentPane().add(Pane);






    }







}
