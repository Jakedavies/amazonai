package game;


import ai.AmazonHeuristic;
import ai.AmazonSuccessorByte;

import java.util.ArrayList;

/**
 * Created by nolan on 19/03/15.
 * Action class, prevents us from having to make redundant states.
 */
public class Action {


    private BoardStateByte parent;
    private byte xOriginal;
    private byte yOriginal;
    private byte xFinal;
    private byte yFinal;
    private ArrayList<Action> children;
    private byte[] stoneThrow;


    /**
     * Constructor: Given a pieces position, potential move for that piece, and state of the board.
     * @param original Piece (position) that will be moved.
     * @param finalMove Final position of the piece
     * @param parent Current state of the board.
     */
    public Action(byte[] original, byte[] finalMove, BoardStateByte parent) {

       //Grab the original pieces x,y
        this.xOriginal = original[0];
        this.yOriginal = original[1];
        children = null;

        //Final pieces x,y
        this.xFinal = finalMove[0];
        this.yFinal = finalMove[1];

        //Set the parent state.
        this.parent = parent;
    }
    public void generateChildren()
    {
        AmazonSuccessorByte success = new AmazonSuccessorByte();
        children = success.generateTreeLevel(this.makeThisMove());
    }
    public ArrayList<Action> getChildActions()
    {
        return children;
    }

    /**
     * Gets the value of the state, this is generated by the heuristic function
     * @return: Returns the value of the potential state.
     */
    public int getValue(){
        AmazonHeuristic amazonHeuristic= new AmazonHeuristic();
        return amazonHeuristic.getValue(this);
    }


    /**
     * @return The final x-position of this moves state.
     */
    public byte getXFinal(){
        return this.xFinal;
    }

    /**
     * @return The final y-position of this moves state.
     */
    public byte getyFinal(){
        return this.yFinal;
    }


    /**
     *
     * @param parent creates a clone of an action.
     */
    public Action(Action parent){
        this.parent = parent.parent;
        this.xOriginal = parent.xOriginal;
        this.yOriginal = parent.yOriginal;
        this.xFinal = parent.xFinal;
        this.yFinal = parent.yFinal;
    }


    /**
     * @return the parent action.
     */
    public BoardStateByte getParent(){
        return this.parent;
    }

    /**
     *
     * @return the potential stone throw.
     */
    public byte[] getStoneThrow(){
        return this.stoneThrow;
    }


    /**
     * Actually make the move itself.
     * @return A new boardstate with the move having been made.
     */
    public BoardStateByte makeThisMove(){
        BoardStateByte descendant = new BoardStateByte(this.parent);
        descendant.moveQueen(xOriginal,yOriginal,xFinal,yFinal);
        descendant.throwStone(stoneThrow[0], stoneThrow[1]);
        return descendant;
    }

    /**
     * Sets the board up for the throw evaluation.
     */
    public void setUpForThrowEvaluation(){
        parent.moveQueen(xOriginal,yOriginal,xFinal,yFinal);
    }

    /**
     * Tears the board down from the throw evaluation.
     */
    public void tearDownFromThrowEvaluation(){
        parent.moveQueen(xFinal,yFinal,xOriginal,yOriginal);
    }

    /**
     * Sets the states throw.
     * @param throwPosition The potential throw that this action will make.
     */
    public void addThrow(byte[] throwPosition){
        this.stoneThrow = throwPosition;
    }


//End of class
}
