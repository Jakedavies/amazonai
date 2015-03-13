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
    private int bestMoveValue = 0;
    private BoardState state;

    public SuccessorState(Position position, ConcurrentLinkedQueue<Vector> moves, BoardState state){
        this.moves = moves;
        this.position = position;
        this.state = state;
        this.bestMove(); //Calculate best move values.
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

    public int getBestMoveValue(){
        return this.bestMoveValue;
    }

    public Vector bestMove(){
        int maxScore = -Integer.MAX_VALUE;
        Vector bMove = null;

        for(Vector v: moves){

            AmazonSuccessorFunction sf = new AmazonSuccessorFunction(state);
            int score1 = sf.getAllPositions(v).size();

            if(score1 >= maxScore){
                maxScore = score1;
                bMove = v;
            }

            if(bestMove == null){
                bestMove = v;
                maxScore = score1;
            }


        }
        this.bestMoveValue = maxScore;
        bestMove = bMove;
        return bMove;

    }


    public Vector getBestMove(){
        return this.bestMove;
    }


    }











