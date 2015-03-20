package ai;

import game.Action;
import game.BoardStateByte;
import game.ByteArrayQueenExpander;

import java.util.ArrayList;

/**
 * Created by nolan on 18/03/15.
 * Successor function for amazonai.
 */
public class AmazonSuccessorByte {


    /**
     * Method which, given a position and boardstate will return every position reachable from it.
     *
     * @param v byte[] position to be evaluated.
     * @param b boardstate that the position to be evaluated exists in.
     * @return ArrayList<byte[]> all available moves.
     */
    public ArrayList<byte[]> getAllDirections(byte[] v, BoardStateByte b )  {
        ArrayList<byte[]> possiblePositions = new ArrayList<>();

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
        byte x = -1;
        byte y = 0;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
         x = -1;
         y = -1;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
        x = 0;
        y = -1;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
        x = 1;
        y = -1;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
        x = 1;
        y = 0;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
        x = 1;
        y = 1;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
        x = 0;
        y = 1;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
        x = -1;
        y = 1;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));

        return possiblePositions;
    }

    /**
     *
     * @param position The position of the queen that you would like to generate moves for.
     * @param x integer x modifier.
     * @param y integer y modifier.
     * @return
     */
    private ArrayList<byte[]> getDirectionMoves(byte[] position, byte x, byte y, BoardStateByte b) {
        ArrayList<byte[]> moves = new ArrayList<>();

        int i = 1;
        boolean run = true;
        while(run) {
            byte xMod = 0;
            byte yMod = 0;

            //Manually do the multiplication. For the X and Y shift.
            for(int j = 0; j < i; j ++){
                xMod += x;
                yMod += y;
            }

            //Apply the X and Y shift.
            byte xVal = 0;
            xVal += position[0];
            xVal += xMod;
            byte yVal = 0;
            yVal += position[1];
            yVal += yMod;


            //Check if the new position is actually valid.
            run = b.isValidPosition(xVal, yVal);
            if(run) {
                byte[] move = new byte[2];
                move[0] = xVal;
                move[1] = yVal;
                moves.add(move); // if valid add it to the collection.
                i++;
            }
        }
        return  moves;

    }


    /**
     * Method that generates a full expansion of a state.
     * For every queen returns every possible board state.
     * @param board
     * @return
     */
    public ArrayList<Action> generateTreeLevel(BoardStateByte board) {
        ArrayList<Action> level = new ArrayList<>();

        //get the boards Friendly Queens
        byte[][] queens = board.getWhiteQueens();

        //For each friendly queen.
        for (int i = 0; i < 4; i++) {
            byte[] queen = queens[i];
           //For the queen being evaluated; for each position available to it.
            ArrayList<byte[]> moves = this.getAllDirections(queen, board);
            for(byte[] moveForQueen: moves){

                //Clone the board
                Action movedQueen = new Action(queen, moveForQueen, board);
                movedQueen.setUpForThrowEvaluation();

                //For every possible stone throw from that position.
                ArrayList<byte[]> possibleStoneThrows = this.getAllDirections(moveForQueen, board);
                    for(byte[] throwPos: possibleStoneThrows) {

                        //clone the board.
                        Action thrownStone = new Action(movedQueen);
                        thrownStone.addThrow(throwPos);
                        level.add(thrownStone); //add the permutation.
                    }
                movedQueen.tearDownFromThrowEvaluation();

                }
            }
        return level;

        }

    /**
     * Multithreaded node expansion.
     * @param board state that is to be expanded.
     * @return arraylist of every possible state reachable from that state.
     */
    public ArrayList<Action> generateTreeLevelThreaded(BoardStateByte board) {
        ArrayList<Action> level = new ArrayList<>();

        byte[][] queens = board.getWhiteQueens();

            /*
                For every queen that is in the state, create a new thread.
             */
            ByteArrayQueenExpander t1 = new ByteArrayQueenExpander(board, queens[0]);
            ByteArrayQueenExpander t2 = new ByteArrayQueenExpander(board, queens[1]);
            ByteArrayQueenExpander t3 = new ByteArrayQueenExpander(board, queens[2]);
            ByteArrayQueenExpander t4 = new ByteArrayQueenExpander(board, queens[3]);


        /*
            Start all of the threads.
         */
        level.addAll(t1.start());
        level.addAll(t2.start());
        level.addAll(t3.start());
        level.addAll(t4.start());


        return level;

    }

 //end of class
}



