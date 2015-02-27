package game;

/**
 * Created by nolan on 16/02/15.
 */
public class Position {

    public int position;

    public Position(int pos) {
        this.position = pos;
    }

    public void move(int pos, BoardState b){
        this.position = pos;
    }


}

