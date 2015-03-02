package game;

import ai.AmazonSuccessorFunction;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by nolan on 27/02/15.
 */
public class BoardState {

    private ArrayList<Queen> queens = new ArrayList<>();
    private ArrayList<Queen> friendlyQueens = new ArrayList<>();
    private ArrayList<Queen> enemyQueens = new ArrayList<>();
    private ArrayList<Stone> stones = new ArrayList<>();
    private Position[][] board = new Position[10][10];

    public BoardState(){
        boolean black = false;
        boolean white = true;


        this.addQueen(false, black, new Vector(3,0));
        this.addQueen(false, black, new Vector(6,0));
        this.addQueen(false, black, new Vector(0,3));
        this.addQueen(false, black, new Vector(9,3));

        this.addQueen(true, white, new Vector(0,6));
        this.addQueen(true, white, new Vector(9,6));
        this.addQueen(true, white, new Vector(3,9));
        this.addQueen(true, white, new Vector(6,9));

    }


    public BoardState(BoardState board){
        for(Queen q: queens){
            Queen add = q.clone();
            if(q.isWhite()){
                queens.add(add);
                friendlyQueens.add(add);
            }
            else{
                queens.add(add);
                enemyQueens.add(add);
            }
        }
        for(Queen q : queens){
            this.board[q.position.x][q.position.y] = q;
        }
        for(Stone s: board.getStones()) {
            this.stones.add(new Stone(s.position.clone()));
        }
    }





    public boolean isPositionEmpty(Vector checkPos)
    {
        return board[checkPos.x][checkPos.y] == null;
    }


    public boolean isValidPosition(Vector checkPos){

        if(checkPos.x < 10 && checkPos.x >= 0){
            if(checkPos.y < 10 && checkPos.y >= 0){
                return isPositionEmpty(checkPos);
            }
            else{return false;}
        }
        else{return false;}

    }



    public void moveQueen(Vector queenCurr, Vector queenFinal){
            board[queenCurr.x][queenCurr.y].move(queenFinal, this);
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
