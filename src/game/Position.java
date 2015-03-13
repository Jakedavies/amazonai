package game;

/**
 * Created by nolan on 16/02/15.
 */
public class Position {



    public Vector position;

    public Position(Vector pos) {

        this.position = pos;
    }
    public Position(int x, int y){
        this.position = new Vector(x,y);
    }

    public void move(Vector pos, BoardState b, Queen q){
        if(b.isValidPosition(pos)) {
            this.position = pos;
            b.board[this.getX()][this.getY()] = q;
        }
        else{
            throw new NullPointerException();
        }
    }



    /*
    Wrapper function that exist to simplify accessing vector.
     */
    public int getX(){
        return position.x;
    }
    public int getY(){
        return position.y;
    }
    public int getOneDimensional(){
        return position.getOneDimensional();
    }







}

