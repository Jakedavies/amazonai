package ai;

import game.Action;
import game.BoardStateByte;


import java.util.ArrayList;

/**
* Amazon Heuristc function
 * Gives value of an action.
 * Performs set up and tear down for us.
*/
public class AmazonHeuristic {
    //Create a new successor function
    AmazonSuccessorByte sf = new AmazonSuccessorByte();

    /**
     * Get value method, given an action state, returns the total number of moves availabe for that board.
     * @param state Action state to be evaluated (potential move)
     * @return integer of the value of that move.
     */
    public int getValue(Action state, boolean friendly){
        int territory = 0;
        //Manually perform set up on state. (THIS MOVES THE ACTUAL QUEEN IN PARENT STATE)
        state.setUpForThrowEvaluation();

        //Grab the states potential throw
        byte stoneX = state.getStoneThrow()[0];
        byte stoneY = state.getStoneThrow()[1];

        //actually make throw on the parent state.
        state.getParent().throwStone(stoneX,stoneY);

        //Get the potential move for the queen's final position.
        byte[] pos = new byte[2];
        pos[0] = state.getXFinal();
        pos[1] = state.getyFinal();


        //Use the successor function to show the number of moves that are available to the queen.
        byte[][] Queens;
        if(friendly){
            Queens = state.getParent().getWhiteQueens();
        }
        else{
            Queens = state.getParent().getBlackQueens();
        }
        territory = getTerritoryValue(state.getParent(), friendly) * 4;

        ArrayList<byte[]> moves = new ArrayList<>();
        for(int i = 0; i < 4; i ++){
            moves.addAll(sf.getAllDirections(Queens[i], state.getParent()));
        }

        //Undo the stones throw
        state.getParent().undoThrowStone(stoneX,stoneY);

        //Manually perform the tear down on the state. (THIS MOVES THE ACTUAL QUEEN IN THE PARENT STATE BACK)
        state.tearDownFromThrowEvaluation();

        return moves.size() + territory;
    }

    public int getTerritoryValue(BoardStateByte gameBoard, boolean friendly){

        BoardStateByte valueBoard = new BoardStateByte(gameBoard); //Clone the game board
        byte[][] gboard = valueBoard.getGameBoard();

        byte[][] whiteQueens = gameBoard.getWhiteQueens();
        byte[][] blackQueens = gameBoard.getBlackQueens();

        byte[][] maxValOwner = new byte[10][10];


        int whiteCount = 0;
        int blackCount = 0;

        final byte OWNED_BY_WHITE = 4;
        final byte OWNED_BY_BLACK = 5;
        final byte CONTESTED_SPACE = 6;

        for(int i = 0; i < 4; i ++ ) {
            byte[] queen = whiteQueens[i];

            ArrayList<byte[]> oneHop = sf.getAllDirections(queen, valueBoard);
            ArrayList<byte[]> twoHop = new ArrayList<>();

            for (byte[] b : oneHop) {
                if (maxValOwner[b[0]][b[1]] > 1) {
                    gboard[b[0]][b[1]] = OWNED_BY_WHITE;
                    whiteCount++;
                }
                twoHop.addAll(sf.getAllDirections(b, valueBoard));
            }
            for (byte[] b : twoHop) {
                if (maxValOwner[b[0]][b[1]] > 2) {
                    gboard[b[0]][b[1]] = OWNED_BY_WHITE;
                    whiteCount++;
                }
            }
        }

            for(int i = 0; i < 4; i ++ ) {
                byte[] queen = blackQueens[i];
                ArrayList<byte[]> twoHop = new ArrayList<>();

                ArrayList<byte[]> oneHop = sf.getAllDirections(queen, valueBoard);
                for (byte[] b : oneHop) {
                    if (maxValOwner[b[0]][b[1]] > 1) {
                        gboard[b[0]][b[1]] = OWNED_BY_BLACK;
                        blackCount++;

                    } else if (maxValOwner[b[0]][b[1]] == 1) {
                        gboard[b[0]][b[1]] = CONTESTED_SPACE;
                        whiteCount--;
                    }
                    twoHop.addAll(sf.getAllDirections(b, valueBoard));
                }

                for (byte[] b : twoHop) {
                    if (maxValOwner[b[0]][b[1]] > 2) {
                        gboard[b[0]][b[1]] = OWNED_BY_BLACK;

                    } else if (maxValOwner[b[0]][b[1]] == 2) {
                        gboard[b[0]][b[1]] = CONTESTED_SPACE;
                    }
                }
            }

        if(friendly){
            return whiteCount-blackCount;
        }
        else
            return blackCount-whiteCount;


    }








}