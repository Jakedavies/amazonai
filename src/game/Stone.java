package game;

import javax.swing.*;

/**
 * Created by nolan on 16/02/15.
 */
public class Stone extends Position {

    private final String ic = "./images/stone.png";
    private JLabel icon = new JLabel(new ImageIcon(ic));


    public Stone(int pos){
        super(pos);
    }

    public JLabel getIcon(){
        return icon;
    }




}
