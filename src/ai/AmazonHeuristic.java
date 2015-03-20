package ai;

import game.Action;


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
    public int getValue(Action state){
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
        ArrayList<Action> list = sf.generateTreeLevel(state.getParent());

        //Undo the stones throw
        state.getParent().undoThrowStone(stoneX,stoneY);

        //Manually perform the tear down on the state. (THIS MOVES THE ACTUAL QUEEN IN THE PARENT STATE BACK)
        state.tearDownFromThrowEvaluation();

        return list.size();
    }





}