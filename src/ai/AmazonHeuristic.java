package ai;

import game.Action;
import game.BoardState;
import game.BoardStateByte;
import game.Queen;

import java.util.ArrayList;

/**
* Created by jakedavies on 15-02-22.
*/
public class AmazonHeuristic {
    AmazonSuccessorByte sf = new AmazonSuccessorByte();

    public int getValue(Action state){
        state.setUpForThrowEvaluation();
        byte stoneX = state.getStoneThrow()[0];
        byte stoneY = state.getStoneThrow()[1];
        state.getParent().throwStone(stoneX,stoneY);
        byte[] pos = new byte[2];
        pos[0] = state.getXFinal();
        pos[1] = state.getyFinal();
        ArrayList<Action> list = sf.generateTreeLevel(state.getParent());
        state.getParent().undoThrowStone(stoneX,stoneY);
        state.tearDownFromThrowEvaluation();

        return list.size();
    }





}