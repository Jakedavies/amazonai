package game;

import ai.MinMaxTree;

import java.util.ArrayList;

/**
* Created by nolan on 3/24/2015.
*/
public class IDFSThreaded implements Runnable{
    ArrayList<Action> generated = new ArrayList<>();
    final long startTime = System.currentTimeMillis();
    int global_count = 0;
    MinMaxTree tree;
    ArrayList<Action> childActions;
    Action bestMove;
    boolean Forwards = false;

    public IDFSThreaded(MinMaxTree m, boolean Forwards){
        this.tree = m;
        this.childActions = m.getChildActions();
        this.Forwards = Forwards;


    }

    public Action IDFS(int currentCost, ArrayList<Action> childActions){
        if(childActions == null || childActions.size() == 0) {
            return null;
        }
        int currentMax = Integer.MIN_VALUE;
        Action currentMaxAction = null;


        int to = childActions.size()/2;


        if(Forwards){
            for(int i = to; i < childActions.size(); i ++){
                childActions.remove(i);
            }
        }
        else{
            for(int i = 0; i < to; i ++){
                childActions.remove(i);
            }
        }
        boolean halt = false;

        for(Action childAction : childActions)
        {


            long currTime = System.currentTimeMillis();
            if(currTime-startTime > 27000){
                System.out.println(currTime-startTime);
                break;
            }
            int currentCost2 = currentCost+childAction.getValue(true); //TRUE FLAG ADDED FOR TESTING
//            ArrayList<Action> l = childAction.getChildActions();
//            l.sort(Action.ID_ASC_FRIENDLY);
//            Action currentChild = l.get(0);
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

    public Action start(){
        this.run();
        return bestMove;
    }

    public void run(){
            bestMove = this.IDFS(0,childActions);
    }






}
