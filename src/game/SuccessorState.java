package game;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by nolan on 2/28/2015.
 */
public class SuccessorState {

    private Queen queen;
    private ConcurrentLinkedQueue<Vector> moves;

    public SuccessorState(Queen queen, ConcurrentLinkedQueue<Vector> moves){
        this.moves = moves;
        this.queen = queen;
    }

    public Queen getQueen(){
        return this.queen;
    }
    public ConcurrentLinkedQueue<Vector> getMoves(){
        return this.moves;
    }

    public String toString(){
        return moves.toString();
    }









}
