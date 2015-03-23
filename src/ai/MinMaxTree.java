package ai;

import game.Action;
import game.BoardStateByte;

import java.util.*;

/**
 * Created by jakedavies on 15-03-22.
 */
public class MinMaxTree {
    AmazonSuccessorByte successorByte;
    BoardStateByte initialBoardState;
    ArrayList<Action> actions;
    int depth;
    int global_count = 0;
    public MinMaxTree(int depth, BoardStateByte initialBoardState)
    {
        initialBoardState = (initialBoardState != null ? initialBoardState: new BoardStateByte());
        successorByte = new AmazonSuccessorByte();
        actions = successorByte.generateTreeLevel(initialBoardState);
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
                action.generateChildren();
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
            int currentCost2 = currentCost+childAction.getValue();
            Action currentChild = IDFS(currentCost2+childAction.getValue(),childAction.getChildActions());

            if(currentChild != null){
                System.out.println("Current max is "+(currentCost2+currentChild.getValue()));

                if(currentMax<(currentCost2+currentChild.getValue()))
                {
                    currentMax = currentCost2+currentChild.getValue();
                    currentMaxAction = currentChild;
                    System.out.println("New max is "+ currentMax + " for destination "+currentMaxAction.getXFinal()+","+currentMaxAction.getyFinal());
                }
            }
        }
        if(currentMaxAction == null) {
            currentMax = Integer.MIN_VALUE;
            int loop_count = 0;
            for (Action childAction : childActions) {
                if (childAction.getValue() > currentMax) {
                    currentMax = childAction.getValue();
                    currentMaxAction = childAction;
                }
            }
            global_count++;
            System.out.println("No child actions found, best action for node " + global_count + " was destination " + currentMaxAction.getXFinal() + "," + currentMaxAction.getyFinal());
        }
        return currentMaxAction;
    }
}
