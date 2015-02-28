package ai;

import game.BoardState;
import game.Queen;
import game.Vector;
import game.Position;
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

    public ConcurrentLinkedQueue<Vector> getSuccessors(){
        //TODO: iterate through possible successor states and add them to the successors list

        for(Queen queen : currentState.getQueens())
        {
            successors.addAll(getAllPositions(queen));

        }
        return successors;
    }
    private ConcurrentLinkedQueue<Vector> getLeftMoves(Position position)
    {
        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();
        for(int i= position.position.getXPos(); i>=0;i--)
        {
            Vector possiblePosition = getNewPositionVector(position.position,-1*(i),0);
            if(currentState.isPositionEmpty(possiblePosition))
            {
                moves.add(possiblePosition);
            }
            else{
                break;
            }
        }
        return  moves;

    }
    private ConcurrentLinkedQueue<Vector> getAllPositions (Position position)
    {
        ConcurrentLinkedQueue<Vector> possiblePositions = new ConcurrentLinkedQueue<Vector>();
        possiblePositions.addAll(getDownMoves(position));
        possiblePositions.addAll(getLeftMoves(position));
        possiblePositions.addAll(getRightMoves(position));
        possiblePositions.addAll(getUpMoves(position));
        possiblePositions.addAll(getUpLeftMoves(position));
        possiblePositions.addAll(getUpRightMoves(position));
        possiblePositions.addAll(getDownLeftMoves(position));
        possiblePositions.addAll(getDownRightMoves(position));
        return possiblePositions;
    }
    private ConcurrentLinkedQueue<Vector> getRightMoves(Position position)
    {
        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();
        for(int i= position.position.getXPos(); i<10;i++)
        {
            Vector possiblePosition = getNewPositionVector(position.position,i,0);
            if(currentState.isPositionEmpty(possiblePosition))
            {
                moves.add(possiblePosition);
            }
            else{
                break;
            }
        }
        return  moves;
    }
    private ConcurrentLinkedQueue<Vector> getUpMoves(Position position)
    {
        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();
        for(int i= position.position.getYPos(); i>=0;i--)
        {
            Vector possiblePosition = getNewPositionVector(position.position,0,-1*(i));
            if(currentState.isPositionEmpty(possiblePosition))
            {
                moves.add(possiblePosition);
            }
            else{
                break;
            }
        }
        return  moves;
    }
    private ConcurrentLinkedQueue<Vector> getDownMoves(Position position)
    {
        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();
        for(int i= position.position.getYPos(); i<10;i++)
        {
            Vector possiblePosition = getNewPositionVector(position.position,0,i);
            if(currentState.isPositionEmpty(possiblePosition))
            {
                moves.add(possiblePosition);
            }
            else{
                break;
            }
        }
        return  moves;
    }
    private ConcurrentLinkedQueue<Vector> getUpRightMoves(Position position)
    {
        int startingIterator = Math.min(9-position.position.getXPos(),position.position.getYPos()) == position.position.getYPos() ? position.position.getYPos() : position.position.getXPos();
        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        for(int i= startingIterator; i<10;i++)
        {
            Vector possiblePosition = getNewPositionVector(position.position,i,-1*i);
            if(currentState.isPositionEmpty(possiblePosition))
            {
                moves.add(possiblePosition);
            }
            else{
                break;
            }
        }
        return  moves;
    }
    private ConcurrentLinkedQueue<Vector> getUpLeftMoves(Position position)
    {
        int startingIterator = Math.min(position.position.getXPos(),position.position.getYPos());
        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        for(int i= startingIterator; i<10;i++)
        {
            Vector possiblePosition = getNewPositionVector(position.position,-1*i,-1*i);
            if(currentState.isPositionEmpty(possiblePosition))
            {
                moves.add(possiblePosition);
            }
            else{
                break;
            }
        }
        return  moves;
    }
    private ConcurrentLinkedQueue<Vector> getDownRightMoves(Position position)
    {
        int startingIterator = Math.max(position.position.getXPos(),position.position.getYPos());
        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        for(int i= startingIterator; i<10;i++)
        {
            Vector possiblePosition = getNewPositionVector(position.position,i,i);
            if(currentState.isPositionEmpty(possiblePosition))
            {
                moves.add(possiblePosition);
            }
            else{
                break;
            }
        }
        return  moves;
    }
    private ConcurrentLinkedQueue<Vector> getDownLeftMoves(Position position)
    {
        int startingIterator = Math.min(position.position.getXPos(),9-position.position.getYPos()) == position.position.getXPos() ? position.position.getXPos() : position.position.getYPos();
        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        for(int i= startingIterator; i<10;i++)
        {
            Vector possiblePosition = getNewPositionVector(position.position,-1*i,i);
            if(currentState.isPositionEmpty(possiblePosition))
            {
                moves.add(possiblePosition);
            }
            else{
                break;
            }
        }
        return  moves;
    }
    private Vector getNewPositionVector(Vector currentPosition,int xMove,int yMove)
    {
        return new Vector(currentPosition.getXPos()+xMove,currentPosition.getYPos()+yMove);
    }
}
