package ai;

import game.Action;
import game.BoardStateByte;
import game.Constants;


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
       if(Constants.moveCount > 8) {
            territory = getTerritoryValue(state.getParent(), friendly);
            territory*=2;
        }

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


    /**
     * A method that adds territories to our heuristic, currently, it examines three hops for each queens.
     * @param gameBoard The board on which the state should be evaluated.
     * @param friendly If we are white or black for the board.
     * @return An integer, to be scaled in calling method for the importance placed on square ownership.
     */
    public int getTerritoryValue(BoardStateByte gameBoard, boolean friendly){

        BoardStateByte valueBoard = new BoardStateByte(gameBoard); //Clone the game board

        byte[][] whiteQueens = gameBoard.getWhiteQueens();
        byte[][] blackQueens = gameBoard.getBlackQueens();

        byte[][] maxValOwner = new byte[10][10];
        byte[][] ownage = new byte[10][10];


        /*
        Constant bytes used for keeping who controls what space.
         */
        final byte OWNED_BY_WHITE = 4;
        final byte OWNED_BY_BLACK = 5;
        final byte CONTESTED_SPACE = 6;


        /*
        For each White queen on the board.
         */
        for(int i = 0; i < 4; i ++ ) {
            byte[] queen = whiteQueens[i];

            ArrayList<byte[]> oneHop = sf.getAllDirections(queen, valueBoard);
            ArrayList<byte[]> twoHop = new ArrayList<>();
            ArrayList<byte[]> threeHop = new ArrayList<>();
            //First Hop
            for (byte[] b : oneHop) {
                if (maxValOwner[b[0]][b[1]] < 3) {
                    maxValOwner[b[0]][b[1]] = 3;
                    ownage[b[0]][b[1]] = OWNED_BY_WHITE;
                }
                twoHop.addAll(sf.getAllDirections(b, valueBoard));
            }
            //Second Hop
            for (byte[] b : twoHop) {
                if (maxValOwner[b[0]][b[1]] < 2) {
                    maxValOwner[b[0]][b[1]] = 2;
                    ownage[b[0]][b[1]] = OWNED_BY_WHITE;
                }
                threeHop.addAll(sf.getAllDirections(b,valueBoard));
            }
            //Final Hop
            for (byte[] b : threeHop) {
                if (maxValOwner[b[0]][b[1]] < 1) {
                    maxValOwner[b[0]][b[1]] = 1;
                    ownage[b[0]][b[1]] = OWNED_BY_WHITE; //We do not need to check for whit contestion yet as have not yet evaluated black.
                }
            }
        }

        /*
        For every black queen on the board.
         */
            for(int i = 0; i < 4; i ++ ) {
                byte[] queen = blackQueens[i];
                ArrayList<byte[]> twoHop = new ArrayList<>();
                ArrayList<byte[]> threeHop = new ArrayList<>();
                ArrayList<byte[]> oneHop = sf.getAllDirections(queen, valueBoard);
                //First hop for the queens
                for (byte[] b : oneHop) {
                    if (maxValOwner[b[0]][b[1]] < 3) {
                        maxValOwner[b[0]][b[1]] = 3;
                        ownage[b[0]][b[1]] = OWNED_BY_BLACK;
                    } else if (maxValOwner[b[0]][b[1]] == 3 && ownage[b[0]][b[1]] != OWNED_BY_BLACK) { //Check to see if the square is owned
                        ownage[b[0]][b[1]] = CONTESTED_SPACE;
                    }
                    twoHop.addAll(sf.getAllDirections(b, valueBoard));
                }
                //Secon hop
                for (byte[] b : twoHop) {
                    if (maxValOwner[b[0]][b[1]] < 2) { //One is used as a hop by 1 is more valuable than a two moves to a space.
                        maxValOwner[b[0]][b[1]] = 2;
                        ownage[b[0]][b[1]] = OWNED_BY_BLACK;
                    } else if (maxValOwner[b[0]][b[1]] == 2 && ownage[b[0]][b[1]] != OWNED_BY_BLACK) {
                        ownage[b[0]][b[1]] = CONTESTED_SPACE;
                    }
                    //Do another hop iff  stones on board
                    if (Constants.moveCount > Constants.startThirdHop) {
                        threeHop.addAll(sf.getAllDirections(b, valueBoard));
                    }
                }

                if (Constants.moveCount > Constants.startThirdHop) {
                    for (byte[] b : threeHop) {
                        if (oneHop.contains(b)) {
                            continue;
                        }
                        if (maxValOwner[b[0]][b[1]] < 1) { //One is used as a hop by 1 is more valuable than a two moves to a space.
                            maxValOwner[b[0]][b[1]] = 1;
                            ownage[b[0]][b[1]] = OWNED_BY_BLACK;
                        } else if (maxValOwner[b[0]][b[1]] == 1 && ownage[b[0]][b[1]] != OWNED_BY_BLACK) {
                            ownage[b[0]][b[1]] = CONTESTED_SPACE;
                        }
                    }
                }
            }

        int whiteCount = 0;
        int blackCount = 0;
        int contested = 0;

        for(int i = 0; i < 10; i ++){
            for(int j = 0; j < 10; j ++){
                if(ownage[i][j] == OWNED_BY_BLACK){
                    blackCount++;
                }
                if(ownage[i][j] == OWNED_BY_WHITE){

                    whiteCount++;
                }
                if(ownage[i][j] == CONTESTED_SPACE){
                    contested++;
                }
            }
        }



        if(friendly){
            return whiteCount-blackCount;
        }
            return blackCount-whiteCount;


    }








}