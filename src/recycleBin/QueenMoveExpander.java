package game;

import recycleBin.AmazonSuccessorFunction;

import java.util.ArrayList;

/**
 * Created by nolan on 17/03/15.
 */
public class QueenMoveExpander {
    private BoardState board;
    private ArrayList<BoardState> states = new ArrayList<>();
    private Queen queen;
    public QueenMoveExpander(BoardState b, Queen q){

        this.board = b;
        this.queen = q;
    }

    public void run(){
        AmazonSuccessorFunction sf = new AmazonSuccessorFunction(this.board);

        for(Vector q2 : sf.getAllPositions(queen.position)){
            BoardState newState = board.clone();
            newState.moveQueen(queen.position, q2);
            newState.QueenToMovePositionInitial = queen.position;
            newState.QueenToMovePositionFinal = q2;

            //Loop for every possible throw for each move.

            for(Vector ti : new AmazonSuccessorFunction(newState).getAllPositions(q2)){

                //Throw the stone
                newState.throwStone(ti);
                newState.stoneToThrow = ti;

                //Add the new move with stones throw to the set
                states.add(newState);
            }
        }


    }
    public ArrayList<BoardState> start(){
        this.run();
        return states;
    }




}
