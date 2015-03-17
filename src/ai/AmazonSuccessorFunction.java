package ai;

import game.*;
import ubco.ai.games.Amazon;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by jakedavies on 15-02-22.
 */
public class AmazonSuccessorFunction {
    private BoardState currentState;
    private ConcurrentLinkedQueue<Vector> successors;

    public AmazonSuccessorFunction(BoardState curState){
        this.currentState = curState;
    }
    int amount = 0;



    public ConcurrentLinkedQueue<Vector> getAllPositions (Vector position)
    {
        ConcurrentLinkedQueue<Vector> possiblePositions = new ConcurrentLinkedQueue<Vector>();


        /*
         X,Y Modifiers:
         -1, 0 = LEFT
         -1,-1 = LEFT DIAGONAL
         0,-1 =  UP
         1, -1 = RIGHT DIAGONAL
         1,0 = RIGHT
         1, 1 = RIGHT BOTTOM DIAGONAL
         0, 1 = DOWN
         -1, 1 = LEFT BOTTOM DIAGONAL
         Down, Down-Forward, Forward + Punch
         */
        possiblePositions.addAll(this.getDirectionMoves(position, -1,0));
        possiblePositions.addAll(this.getDirectionMoves(position, -1,-1));
        possiblePositions.addAll(this.getDirectionMoves(position, 0,-1));
        possiblePositions.addAll(this.getDirectionMoves(position, 1,-1));
        possiblePositions.addAll(this.getDirectionMoves(position, 1,0));
        possiblePositions.addAll(this.getDirectionMoves(position, 1,1));
        possiblePositions.addAll(this.getDirectionMoves(position, 0,1));
        possiblePositions.addAll(this.getDirectionMoves(position, -1,1));

        amount += possiblePositions.size();


        return possiblePositions;

    }


    private ConcurrentLinkedQueue<Vector> getDirectionMoves(Vector position, int x, int y) {

        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        int i = 1;
        boolean run = true;
        while(run) {
            Vector move = getNewPositionVector(position, i*x, i*y);
            run = currentState.isValidPosition(move);
            if(run) {
                moves.add(move);
                i++;
            }
        }



        return  moves;
    }

    private Vector getNewPositionVector(Vector currentPosition,int xMove,int yMove) {
        return new Vector(currentPosition.x+xMove,currentPosition.y+yMove);
    }


    public ArrayList<ArrayList<BoardState>> generateTreeLevel(){
        ArrayList<ArrayList<BoardState>> states = new ArrayList<>();

        for(Queen q: currentState.getFriendlyQueens()){

            for(Vector q2 : this.getAllPositions(q.position)){
                BoardState newState = currentState.clone();
                newState.moveQueen(q.position, q2);
                ArrayList<BoardState> moveSet = new ArrayList<>();

                for(Vector ti : new AmazonSuccessorFunction(newState).getAllPositions(q2)){
                    newState.throwStone(ti);
                    moveSet.add(newState);
                }
                states.add(moveSet);
            }
        }
        return states;
    }


}