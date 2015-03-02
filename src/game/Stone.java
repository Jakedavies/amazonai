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


    }

    public JLabel getIcon(){
        return icon;
    }




}
