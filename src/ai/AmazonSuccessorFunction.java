package ai;

import game.*;
import javafx.geometry.Pos;
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

    public SuccessorState[] getSuccessors(){
        //TODO: iterate through possible successor states and add them to the successors list

        SuccessorState[] successorStates = new  SuccessorState[4];

        int i = 0;
        for(Queen q : currentState.getFriendlyQueens()){

            successorStates[i++] = new SuccessorState(q , this.getAllPositions(q.position), this.currentState);
        }

        return successorStates;
    }

    public ConcurrentLinkedQueue<Vector> getAllPositions (Vector position)
    {
        ConcurrentLinkedQueue<Vector> possiblePositions = new ConcurrentLinkedQueue<Vector>();
        possiblePositions.addAll(this.getLeftMoves(position));
        possiblePositions.addAll(this.getRightMoves(position));
        possiblePositions.addAll(this.getUpMoves(position));
        possiblePositions.addAll(this.getDownMoves(position));
        possiblePositions.addAll(this.getUpRightMoves(position));
        possiblePositions.addAll(this.getUpLeftMoves(position));
        possiblePositions.addAll(this.getDownLeftMoves(position));
        possiblePositions.addAll(this.getDownRightMoves(position));


        return possiblePositions;

    }





    private ConcurrentLinkedQueue<Vector> getLeftMoves(Vector position) {
        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        int i = 1;
            boolean run = true;
            while(run) {
                Vector move = getNewPositionVector(position, -i, 0);
                run = currentState.isValidPosition(move);
                if(run) {
                    moves.add(move);
                    i++;
                }
            }




        return  moves;

    }

    private ConcurrentLinkedQueue<Vector> getRightMoves(Vector position)
    {

        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        int i = 1;
            boolean run = true;
            while(run) {
                Vector move = getNewPositionVector(position, i, 0);
                run = currentState.isValidPosition(move);
                if(run) {
                    moves.add(move);
                    i++;
                }
            }



        return  moves;
    }
    private ConcurrentLinkedQueue<Vector> getUpMoves(Vector position)
    {

        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        int i = 1;
            boolean run = true;
            while(run) {
                Vector move = getNewPositionVector(position, 0, -i);
                run = currentState.isValidPosition(move);
                if(run) {
                    moves.add(move);
                    i++;
                }
            }



        return  moves;
    }
    private ConcurrentLinkedQueue<Vector> getDownMoves(Vector position)
    {

        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        int i = 1;
            boolean run = true;
            while(run) {
                Vector move = getNewPositionVector(position, 0, i);
                run = currentState.isValidPosition(move);
                if(run) {
                    moves.add(move);
                    i++;
                }
            }



        return  moves;
    }
    private ConcurrentLinkedQueue<Vector> getUpRightMoves(Vector position)
    {

        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        int i = 1;
            boolean run = true;
            while(run) {
                Vector move = getNewPositionVector(position, i, -i);
                run = currentState.isValidPosition(move);
                if(run) {
                    moves.add(move);
                    i++;
                }
            }



        return  moves;
    }
    private ConcurrentLinkedQueue<Vector> getUpLeftMoves(Vector position)
    {

        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        int i = 1;
            boolean run = true;
            while(run) {
                Vector move = getNewPositionVector(position, -i, -i);
                run = currentState.isValidPosition(move);
                if(run) {
                    moves.add(move);
                    i++;
                }
            }



        return  moves;
    }
    private ConcurrentLinkedQueue<Vector> getDownRightMoves(Vector position)
    {

        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        int i = 1;
            boolean run = true;
            while(run) {
                Vector move = getNewPositionVector(position, i, i);
                run = currentState.isValidPosition(move);
                if(run) {
                    moves.add(move);
                    i++;
                }


        }

        return  moves;
    }
    private ConcurrentLinkedQueue<Vector> getDownLeftMoves(Vector position)
    {

        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        int i = 1;
            boolean run = true;
            while(run) {
                Vector move = getNewPositionVector(position, -i, i);
                run = currentState.isValidPosition(move);
                if(run) {
                    moves.add(move);
                    i++;
                }
            }



        return  moves;
    }
    private Vector getNewPositionVector(Vector currentPosition,int xMove,int yMove)
    {
        return new Vector(currentPosition.getXPos()+xMove,currentPosition.getYPos()+yMove);
    }
}
