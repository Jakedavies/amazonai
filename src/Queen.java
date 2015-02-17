import javax.swing.*;

/**
 * Created by nolan on 16/02/15.
 */
public class Queen  extends  Position{

    private boolean friendly;
    private JLabel icon;
    private final String iconWhite = "./images/chessQueenW2.png";
    private String iconBlack = "./images/chessQueenB.png";


    public Queen(boolean friendly, boolean white, int pos){
        super(pos);
        this.friendly = friendly;

        if(white){
            icon = new JLabel(new ImageIcon(iconWhite));
        }
        else{
            icon = new JLabel(new ImageIcon(iconBlack));
        }

    }

    public JLabel getIcon(){
        return icon;
    }





}
