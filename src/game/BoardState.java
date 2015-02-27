package game;

import java.util.ArrayList;

/**
 * Created by nolan on 27/02/15.
 */
public class BoardState {

    private ArrayList<Queen> queens = new ArrayList<>();
    private ArrayList<Stone> stones = new ArrayList<>();
    private Position[] board = new Position[100];

    public BoardState(){
       boolean black = false;
        boolean white = true;

        this.addQueen(false, black, 3);
        this.addQueen(false, black, 6);
        this.addQueen(false, black, 30);
        this.addQueen(false, black, 39);

        this.addQueen(true, white, 40);
        this.addQueen(true, white, 49);
        this.addQueen(true, white, 93);
        this.addQueen(true, white, 96);

    }



    public void moveQueen(int queenCurr, int queenFinal){
            board[queenCurr].move(queenFinal, this);
    }


    public void throwStone(int pos){
        Stone stone = new Stone(pos);
        stones.add(stone);
        board[pos] = stone;
    }

    public void addQueen(boolean friendly, boolean white, int pos){
        Queen queen = new Queen(friendly, white, pos);
        queens.add(queen);
        board[pos] = queen;
    }

    public ArrayList<Queen> getQueens(){ return this.queens;}
    public ArrayList<Stone> getStones(){return this.stones;}



}
