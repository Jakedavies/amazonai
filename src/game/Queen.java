package game;

import javax.swing.*;

/**
 * Created by nolan on 16/02/15.
 */
public class Queen  extends  Position{

    private boolean friendly;
    private JLabel icon;
    private final String iconWhite = "./images/chessQueenW2.png";
    private String iconBlack = "./images/chessQueenB.png";
    private final String iconWhitePAC = "./images/wakaQueen.png";
    private String iconBlackPAC = "./images/wakaQueenB.png";


    public Queen(boolean friendly, boolean white, Vector pos){
        super(pos);
        this.friendly = friendly;

        if(white){
            icon = new JLabel(new ImageIcon(iconWhitePAC));
        }
        else{
            icon = new JLabel(new ImageIcon(iconBlackPAC));
        }

    }



    public JLabel getIcon(){
        return icon;
    }





}
