package game;

import ai.AmazonSuccessorFunction;

import java.util.ArrayList;

/**
 * Created by nolan on 27/02/15.
 */
public class BoardState {

    private ArrayList<Queen> queens = new ArrayList<>();
    private ArrayList<Stone> stones = new ArrayList<>();
    private Position[][] board = new Position[10][10];

    public BoardState(){
       boolean black = false;
        boolean white = true;

        this.addQueen(true,white, new Vector(3,7));


//        this.addQueen(false, black, new Vector(3,0));
//        this.addQueen(false, black, new Vector(6,0));
//        this.addQueen(false, black, new Vector(0,4));
//        this.addQueen(false, black, new Vector(9,4));
//
//        this.addQueen(true, white, new Vector(0,5));
//        this.addQueen(true, white, new Vector(9,5));
//        this.addQueen(true, white, new Vector(3,9));
//        this.addQueen(true, white, new Vector(6,9));

    }
    public boolean isPositionEmpty(Vector checkPos)
    {
        return board[checkPos.getXPos()][checkPos.getYPos()] == null;
    }


    public boolean isValidPosition(Vector checkPos){

        if(checkPos.getXPos() < 10 && checkPos.getXPos() >= 0){
            if(checkPos.getYPos() < 10 && checkPos.getYPos() >= 0){
                return isPositionEmpty(checkPos);
            }
            else{return false;}
        }
        else{return false;}

    }



    public void moveQueen(Vector queenCurr, Vector queenFinal){
            board[queenCurr.getXPos()][queenCurr.getYPos()].move(queenFinal, this);
    }


    public void throwStone(Vector pos){

        Stone stone = new Stone(pos);
        stones.add(stone);
        board[pos.getXPos()][pos.getYPos()] = stone;
    }

    public void addQueen(boolean friendly, boolean white, Vector pos){
        Queen queen = new Queen(friendly, white, pos);
        queens.add(queen);
        board[pos.getXPos()][pos.getYPos()] = queen;


    }

    public ArrayList<Queen> getQueens(){ return this.queens;}
    public ArrayList<Stone> getStones(){return this.stones;}



}
