package game;

import ai.AmazonSuccessorFunction;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by nolan on 2/28/2015.
 */
public class SuccessorState {

    private Position position;
    private ConcurrentLinkedQueue<Vector> moves;
    private Vector bestMove;
    private BoardState state;

    public SuccessorState(Position position, ConcurrentLinkedQueue<Vector> moves, BoardState state){
        this.moves = moves;
        this.position = position;
        this.state = state;
    }

    public Position getPositionObject(){
        return this.position;
    }
    public ConcurrentLinkedQueue<Vector> getMoves(){
        return this.moves;
    }

    public String toString(){
        return moves.toString();
    }

    public Vector bestMove(){
        int maxScore =0;
        Vector bMove = null;

        for(Vector v: moves){

            AmazonSuccessorFunction sf = new AmazonSuccessorFunction(state);
            int score1 = sf.getAllPositions(v).size();
            if(score1 > maxScore){
                maxScore = score1;
                bMove = v;
            }

        }
        System.out.println(maxScore);
        return bMove;

    }










}
