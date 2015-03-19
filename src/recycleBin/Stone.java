package game;

import javax.swing.*;

/**
 * Created by nolan on 16/02/15.
 */
public class Stone extends Position {

    private final String ic = "./images/stone.png";
    private final String icWB = "./images/wakaBlue.png";
    private final String icWR = "./images/wakaRed.png";
    private final String icO = "./images/wakaOrange.png";
    private final String icWP = "./images/wakaPink.png";


    private JLabel icon = new JLabel(new ImageIcon(ic));


    public Stone(Vector pos){
        super(pos);

        if(pos.getOneDimensional()%6 ==0 ){
            icon = new JLabel(new ImageIcon(icWB));
        }
        else if(pos.getOneDimensional()%5 ==0){
            icon = new JLabel(new ImageIcon(icWR));
        }
        else if(pos.getOneDimensional()%4 ==0){
            icon = new JLabel(new ImageIcon(icO));
        }
        else {
            icon = new JLabel(new ImageIcon(icWP));
        }


    }


    public JLabel getIcon(){
        return icon;
    }




}
