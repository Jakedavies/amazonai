package game;

import ai.AmazonSuccessorFunction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Last Updated: Nolan Koriath
 * March 17th 2015
 *
 * Board state class, contains all of the information on a state of the board.
 * This includes all positions of queens, and all positions of stones.
 */
public class BoardState {
    public Vector stoneToThrow;
    public Vector QueenToMovePositionInitial;
    public Vector QueenToMovePositionFinal;



    private ArrayList<Queen> queens = new ArrayList<>();
    private ArrayList<Queen> friendlyQueens = new ArrayList<>();
    private ArrayList<Queen> enemyQueens = new ArrayList<>();
    private ArrayList<Stone> stones = new ArrayList<>();
    public Position[][] board = new Position[10][10];

    public BoardState(){
        boolean black = false;
        boolean white = true;


        /*
            Add all new queens to the board.
         */
        this.addQueen(false, black, new Vector(3,0));
        this.addQueen(false, black, new Vector(6,0));
        this.addQueen(false, black, new Vector(0,3));
        this.addQueen(false, black, new Vector(9,3));

        this.addQueen(true, white, new Vector(0,6));
        this.addQueen(true, white, new Vector(9,6));
        this.addQueen(true, white, new Vector(3,9));
        this.addQueen(true, white, new Vector(6,9));

    }

    /*
        Checks if the board has anything at that position.
     */
    private boolean isPositionEmpty(Vector checkPos)
    {
        return board[checkPos.x][checkPos.y] == null;
    }

    public Vector getStoneToThrow(){
        return this.stoneToThrow;
    }
    public void setStoneToThrow(Vector thrower){
        this.stoneToThrow = thrower;
    }

    public Vector getQueenToMovePositionInitial(){
        return  this.QueenToMovePositionInitial;
    }
    public void setQueenToMovePositionInitial(Vector v){
        this.QueenToMovePositionInitial = v;
    }

    public Vector getQueenToMovePositionFinal(){
        return  this.QueenToMovePositionFinal;
    }
    public void setQueenToMovePositionFinal(Vector v){
        this.QueenToMovePositionFinal = v;
    }


    /**
     * Method that returns if a position is valid or not.
     * Criteria for valid is on the board,
     * As well as empty
     * @param checkPos Position to check
     * @return boolean true if valid.
     */
    public boolean isValidPosition(Vector checkPos){
        if(checkPos.x < 10 && checkPos.x >= 0){
            if(checkPos.y < 10 && checkPos.y >= 0){
                return isPositionEmpty(checkPos);
            }
            else{return false;}
        }
        else{return false;}

    }


    /**
     * Moves a queen from one position another.
     * @param queenCurr Position of the queen to move.
     * @param queenFinal Position the queen should be moved to .
     */
    public void moveQueen(Vector queenCurr, Vector queenFinal){
        board[queenCurr.x][queenCurr.y].move(queenFinal, this, (Queen) board[queenCurr.x][queenCurr.y]);
        // Sets original queen position to null;
        board[queenCurr.x][queenCurr.y] = null;
    }

    /*
        Empty constructor used in clone method.
     */
    private BoardState(boolean b){

    }





    /**
     * Clone method, will return an exact
     * @return
     */
    public BoardState clone(){
        BoardState b = new BoardState(false);
        boolean black = false;
        boolean white = true;

        for(Queen q: this.getFriendlyQueens()){
            b.addQueen(true, white, new Vector(q.getX(), q.getY()));
        }
        for(Queen q: this.getEnemyQueens()){
            b.addQueen(false, black,new Vector(q.getX(), q.getY()));
        }
        for(Stone s: this.getStones()){
            b.throwStone(s.position);
        }
        return b;
    }


    public void throwStone(Vector pos){

        Stone stone = new Stone(pos);
        stones.add(stone);
        board[pos.x][pos.y] = stone;
    }

    public void addQueen(boolean friendly, boolean white, Vector pos){
        Queen queen = new Queen(friendly, white, pos);
        if(friendly){
            friendlyQueens.add(queen);
        }
        else{
            enemyQueens.add(queen);
        }
        queens.add(queen);
        board[pos.x][pos.y] = queen;


    }

    public ArrayList<Queen> getEnemyQueens(){
        return this.enemyQueens;
    }
    public ArrayList<Queen> getFriendlyQueens(){
        return this.friendlyQueens;
    }
    public ArrayList<Queen> getQueens(){
        return this.queens;
    }


    public ArrayList<Stone> getStones(){return this.stones;}



}