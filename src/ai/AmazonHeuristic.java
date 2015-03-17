package ai;

import game.BoardState;
import game.Queen;

/**
 * Created by jakedavies on 15-02-22.
 */
public class AmazonHeuristic {
    private AmazonSuccessorFunction sf = null;
    private BoardState b;

    public AmazonHeuristic(BoardState b){
        sf = new AmazonSuccessorFunction(b);
        this.b = b;
    }

    public int getValue(){
        int boardValue = 0;
        for(Queen q : b.getFriendlyQueens()){
            boardValue += getQueensValue(q);
        }
        return boardValue;

    }

    private int getQueensValue(Queen q){
        return sf.getAllPositions(q.position).size();
    }






}