package game;

import ai.AmazonSuccessorFunction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nolan on 27/02/15.
 */
public class BoardState {

    private ArrayList<Queen> queens = new ArrayList<>();
    private ArrayList<Queen> friendlyQueens = new ArrayList<>();
    private ArrayList<Queen> enemyQueens = new ArrayList<>();
    private ArrayList<Stone> stones = new ArrayList<>();
    public Position[][] board = new Position[10][10];

    public BoardState(){
        boolean black = false;
        boolean white = true;


        this.addQueen(false, black, new Vector(3,0));
        this.addQueen(false, black, new Vector(6,0));
        this.addQueen(false, black, new Vector(0,4));
        this.addQueen(false, black, new Vector(9,4));

        this.addQueen(true, white, new Vector(0,5));
        this.addQueen(true, white, new Vector(9,5));
        this.addQueen(true, white, new Vector(3,9));
        this.addQueen(true, white, new Vector(6,9));

    }
    public boolean isPositionEmpty(Vector checkPos)
    {
        return board[checkPos.x][checkPos.y] == null;
    }


    public boolean isValidPosition(Vector checkPos){

        System.out.println(checkPos.toString());

        if(checkPos.x < 10 && checkPos.x >= 0){
            if(checkPos.y < 10 && checkPos.y >= 0){
                return isPositionEmpty(checkPos);
            }
            else{return false;}
        }
        else{return false;}

    }



    public void moveQueen(Vector queenCurr, Vector queenFinal){

        for(int i = 0; i < this.board.length; i ++){
            System.out.print(this.board[i].toString());
        }


        System.out.println(Arrays.toString(this.board));
        System.out.println(this.friendlyQueens);
        System.out.println(this.enemyQueens);
        System.out.println("QUEEN CURR: " + queenCurr);
        System.out.println("QUEEN CFIN: " + queenFinal);

        System.out.println("------------------------------");

        System.out.println(board[queenCurr.x][queenCurr.y]);
        System.out.println(board[queenFinal.x][queenFinal.y]);

        board[queenCurr.x][queenCurr.y].move(queenFinal, this, (Queen) board[queenCurr.x][queenCurr.y]);
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