import javax.swing.*;

public class Run {
    private static final int DISPOSE_ON_CLOSE = 1;

    public static void main(String[] args){

        JFrame frame = new GameBoard();
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);

        GamePlayer gamePlayer = new GamePlayer("nullpointers8s","password");



    }
}
