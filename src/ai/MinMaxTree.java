package ai;

import game.Action;
import game.BoardStateByte;

import java.util.*;

/**
 * Created by jakedavies on 15-03-22.
 */
public class MinMaxTree {
    final long startTime = System.currentTimeMillis();
    AmazonSuccessorByte successorByte;
    BoardStateByte initialBoardState;
    ArrayList<Action> actions;
    int depth;
    int global_count = 0;
    public MinMaxTree(int depth, BoardStateByte initialBoardState)
    {
        initialBoardState = (initialBoardState != null ? initialBoardState: new BoardStateByte());
        successorByte = new AmazonSuccessorByte();
        actions = successorByte.generateTreeLevel(initialBoardState, true); //TRUE FLAG ADDED FOR TESTING
//        actions.sort(Action.ID_ASC_FRIENDLY); //Friendly sort... Only friendly queens!
        this.depth = depth;
        generateToDepth(actions,this.depth,1);
    }
    public ArrayList<Action> getRootLevelActions()
    {
        return actions;
    }
    public ArrayList<Action> generateToDepth(ArrayList<Action> actions,int depth, int level){
        if(depth == level){
            return actions;
        }
        else{
            for(Action action : actions)
            {
                action.generateChildren(true); //TRUE FLAG ADDED FOR TESTING
                generateToDepth(action.getChildActions(), depth, level + 1);
            }
        }
        return actions;
    }
    public int countTotalActions(ArrayList<Action> actions)
    {
        if(actions == null)
            return 0;
        else
        {
            int totalActions = actions.size();
            for(Action action : actions)
            {
                totalActions+=countTotalActions(action.getChildActions());
            }
            return totalActions;
        }

    }
    public Action getBestMove()
    {
        Action bestMove = IDFS(0,actions);
        System.out.println("Best move is "+bestMove.getXFinal()+","+bestMove.getyFinal());
        return  bestMove;
    }

    public Action IDFS(int currentCost, ArrayList<Action> childActions)
    {
        if(childActions == null) {
            return null;
        }
        int currentMax = Integer.MIN_VALUE;
        Action currentMaxAction = null;
        for(Action childAction : childActions)
        {
            long currTime = System.currentTimeMillis();
            if(currTime-startTime > 27000){
                System.out.println(currTime-startTime);
                break;
            }
            int currentCost2 = currentCost+childAction.getValue(true);//TRUE FLAG ADDED FOR TESTING
            Action currentChild = IDFS(currentCost2+childAction.getValue(true),childAction.getChildActions()); //TRUE FLAG ADDED FOR TESTING

            if(currentChild != null){
                System.out.println("Current max is "+(currentCost2+currentChild.getValue(true)));//TRUE FLAG ADDED FOR TESTING

                if(currentMax<(currentCost2+currentChild.getValue(true))) //TRUE FLAG ADDED FOR TESTING
                {
                    currentMax = currentCost2+currentChild.getValue(true); //TRUE FLAG ADDED FOR TESTING
                    currentMaxAction = currentChild;
                    System.out.println("New max is "+ currentMax + " for destination "+currentMaxAction.getXFinal()+","+currentMaxAction.getyFinal());
                }
            }
        }
        if(currentMaxAction == null) {
            currentMax = Integer.MIN_VALUE;
            int loop_count = 0;
            for (Action childAction : childActions) {
                if (childAction.getValue(true) > currentMax) { //TRUE FLAG ADDED FOR TESTING
                    currentMax = childAction.getValue(true); //TRUE FLAG ADDED FOR TESTING
                    currentMaxAction = childAction;
                }
            }
            global_count++;
            System.out.println("No child actions found, best action for node " + global_count + " was destination " + currentMaxAction.getXFinal() + "," + currentMaxAction.getyFinal());
        }
        return currentMaxAction;
    }
}