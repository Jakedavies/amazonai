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

    public SuccessorState[] getSuccessors(){
        //TODO: iterate through possible successor states and add them to the successors list

        SuccessorState[] successorStates = new  SuccessorState[4];

        int i = 0;
        for(Queen q : currentState.getFriendlyQueens()){
            successorStates[i++] = new SuccessorState(q, this.getAllPositions(q.position), currentState);
        }

        System.out.println("NUMBER OF STATES: " + amount);

        return successorStates;
    }

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
                System.out.println("ADDED " + move.toString());
                moves.add(move);
                i++;
            }
        }



        return  moves;
    }

    private Vector getNewPositionVector(Vector currentPosition,int xMove,int yMove) {
        return new Vector(currentPosition.x+xMove,currentPosition.y+yMove);
    }
}
