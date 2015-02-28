package game;

/**
 * Created by nolan on 16/02/15.
 */
public class Position {

    public Vector position;

    public Position(Vector pos) {
        this.position = pos;
    }

    public void move(Vector pos, BoardState b){
        this.position = pos;
    }


}

