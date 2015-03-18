package ai;

import game.*;
import ubco.ai.games.Amazon;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Last Modified:
 * Nolan Koriath
 * March. 17th / 2015
 *
 *
 */
public class AmazonSuccessorFunction {
    private BoardState currentState;
    private ConcurrentLinkedQueue<Vector> successors;

    public AmazonSuccessorFunction(BoardState curState){
        this.currentState = curState;
    }
    int amount = 0;


    /**
     * Generates all positions given a vectors position.
     * @param position Position to generate list of positions for.
     * @return ConcurrentLinkedQue of vector positions that are available.
     */
    public ConcurrentLinkedQueue<Vector> getAllPositions (Vector position)
    {
        ConcurrentLinkedQueue<Vector> possiblePositions = new ConcurrentLinkedQueue<Vector>();
        /*
         X,Y Modifiers:
         -1, 0 = LEFT
         -1,-1 = LEFT DIAGONAL
         0,-1 =  UP
         1, -1 = RIGHT DIAGONAL
         1,0 = RIGHT
         1, 1 = RIGHT BOTTOM DIAGONAL
         0, 1 = DOWN
         -1, 1 = LEFT BOTTOM DIAGONAL
         Down, Down-Forward, Forward + Punch
         */
        possiblePositions.addAll(this.getDirectionMoves(position, -1,0));
        possiblePositions.addAll(this.getDirectionMoves(position, -1,-1));
        possiblePositions.addAll(this.getDirectionMoves(position, 0,-1));
        possiblePositions.addAll(this.getDirectionMoves(position, 1,-1));
        possiblePositions.addAll(this.getDirectionMoves(position, 1,0));
        possiblePositions.addAll(this.getDirectionMoves(position, 1,1));
        possiblePositions.addAll(this.getDirectionMoves(position, 0,1));
        possiblePositions.addAll(this.getDirectionMoves(position, -1,1));


        amount += possiblePositions.size();
        return possiblePositions;
    }


    /**
     *
     * @param position The position of the queen that you would like to generate moves for.
     * @param x integer x modifier.
     * @param y integer y modifier.
     * @return
     */
    private ConcurrentLinkedQueue<Vector> getDirectionMoves(Vector position, int x, int y) {
        ConcurrentLinkedQueue<Vector> moves = new ConcurrentLinkedQueue<>();

        int i = 1;
        boolean run = true;
        while(run) {
            Vector move = getNewPositionVector(position, i*x, i*y);
            run = currentState.isValidPosition(move);
            if(run) {
                moves.add(move);
                i++;
            }
        }
        return  moves;
    }

    /*
        The generates a new position vector given position and modifiers.
        Up is - down is +, left - right +.
     */
    private Vector getNewPositionVector(Vector currentPosition,int xMove,int yMove) {
        return new Vector(currentPosition.x+xMove,currentPosition.y+yMove);
    }


    /**
     *
     * Returns an arraylist of arraylists of board states.
     * This is to be used to expand a nodes childrens.
     * @return Returns an arraylist of arraylists of board states.
     */
    public ArrayList<BoardState>  generateTreeLevel(){
        ArrayList<BoardState> state = new ArrayList<>();

        // Outer loop through each queen
        for(Queen q: currentState.getFriendlyQueens()){

            //loop for each move that the queen can make
            for(Vector q2 : this.getAllPositions(q.position)){
                BoardState newState = currentState.clone();
                newState.moveQueen(q.position, q2);
                newState.QueenToMovePositionInitial = q.position;
                newState.QueenToMovePositionFinal = q2;

                //Loop for every possible throw for each move.

                for(Vector ti : new AmazonSuccessorFunction(newState).getAllPositions(q2)){

                    //Throw the stone
                    newState.throwStone(ti);
                    newState.stoneToThrow = ti;


                    //Add the new move with stones throw to the set
                    state.add(newState);
                }
            }
        }
        return state;
    }

    /**
     * Multithreaded expand, should never be used for first node as non-threaded is faster.
     *
     * @return ArrayList of board states for possible moves.
     */
    public ArrayList<BoardState> generateTreeLevelThreaded(){
        ArrayList<BoardState> moves = new ArrayList<>();

        QueenMoveExpander t1 = new QueenMoveExpander(this.currentState, this.currentState.getFriendlyQueens().get(0));
        QueenMoveExpander t2 = new QueenMoveExpander(this.currentState, this.currentState.getFriendlyQueens().get(0));
        QueenMoveExpander t3 = new QueenMoveExpander(this.currentState, this.currentState.getFriendlyQueens().get(0));
        QueenMoveExpander t4 = new QueenMoveExpander(this.currentState, this.currentState.getFriendlyQueens().get(0));

        moves.addAll(t1.start());
        moves.addAll(t2.start());
        moves.addAll(t3.start());
        moves.addAll(t4.start());

        return  moves;

    }






}