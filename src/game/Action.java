package game;


import ai.AmazonHeuristic;

/**
 * Created by nolan on 19/03/15.
 */
public class Action {

    private BoardStateByte parent;
    private byte xOriginal;
    private byte yOriginal;
    private byte xFinal;
    private byte yFinal;
    private byte[] stoneThrow;


    public Action(byte[] original, byte[] finalMove, BoardStateByte parent) {
        this.xOriginal = original[0];
        this.yOriginal = original[1];

        this.xFinal = finalMove[0];
        this.yFinal = finalMove[1];
        this.parent = parent;

    }


    public int getValue(){
        AmazonHeuristic amazonHeuristic= new AmazonHeuristic();
        return amazonHeuristic.getValue(this);
    }


    public byte getXFinal(){
        return this.xFinal;
    }
    public byte getyFinal(){
        return this.yFinal;
    }


    public Action(Action parent){
        this.parent = parent.parent;
        this.xOriginal = parent.xOriginal;
        this.yOriginal = parent.yOriginal;
        this.xFinal = parent.xFinal;
        this.yFinal = parent.yFinal;
    }

    public BoardStateByte getParent(){
        return this.parent;
    }

    public byte[] getStoneThrow(){
        return this.stoneThrow;
    }


    public BoardStateByte makeThisMove(){
        BoardStateByte descendant = new BoardStateByte(this.parent);
        descendant.moveQueen(xOriginal,yOriginal,xFinal,yFinal);
        descendant.throwStone(stoneThrow[0], stoneThrow[1]);
        return descendant;
    }

    public void setUpForThrowEvaluation(){
        parent.moveQueen(xOriginal,yOriginal,xFinal,yFinal);
    }
    public void tearDownFromThrowEvaluation(){
        parent.moveQueen(xFinal,yFinal,xOriginal,yOriginal);
    }

    public void addThrow(byte[] throwPosition){
        this.stoneThrow = throwPosition;
    }

    public int getEvaluation(){
//        AmazonHeuristic heur = new AmazonHeuristic();
        this.setUpForThrowEvaluation();
        parent.throwStone(this.stoneThrow[0], this.stoneThrow[1]);
        //Do evaluation
        parent.undoThrowStone(this.stoneThrow[0], this.stoneThrow[1]);
        this.tearDownFromThrowEvaluation();

        return Integer.MIN_VALUE;
    }







}
