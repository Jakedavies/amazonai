package game;

import ai.MinMaxTree;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
* Created by nolan on 3/24/2015.
*/
public class IDFSThreaded implements Callable<Action>{
    final long startTime = System.currentTimeMillis();
    int global_count = 0;
    MinMaxTree tree;
    ArrayList<Action> childActions;
    Action bestMove;
    int quartile;
    public IDFSThreaded(MinMaxTree m, int quartile){
        this.tree = m;
        this.quartile = quartile;
        this.childActions = m.getChildActions();
        int to = childActions.size()/4;

        ArrayList<Action> examine = new ArrayList<>();

        switch(quartile){
            case 1:
                for(int i = 0; i < to; i ++){
                    examine.add(childActions.get(i));
                }
                break;
            case 2:
                for(int i = to; i < to*2; i ++ ){
                    examine.add(childActions.get(i));
                }
                break;
            case 3:
                for(int i = to*2; i < to*3; i ++){
                    examine.add(childActions.get(i));
                }
                break;
            case 4:
                for(int i = to*3; i < childActions.size(); i ++){
                    examine.add(childActions.get(i));
                }
                break;


        }
        this.childActions = examine;
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
            int currentCost2 = currentCost+childAction.getValue(true); //TRUE FLAG ADDED FOR TESTING
//            ArrayList<Action> l = childAction.getChildActions();
//            l.sort(Action.ID_ASC_FRIENDLY);
//            Action currentChild = l.get(0);
            Action currentChild = IDFS(currentCost2+childAction.getValue(true),childAction.getChildActions()); //TRUE FLAG ADDED FOR TESTING

            if(currentChild != null){
//                System.out.println("Current max is "+(currentCost2+currentChild.getValue(true)));//TRUE FLAG ADDED FOR TESTING

                if(currentMax<(currentCost2+currentChild.getValue(true))) //TRUE FLAG ADDED FOR TESTING
                {
                    currentMax = currentCost2+currentChild.getValue(true); //TRUE FLAG ADDED FOR TESTING
                    currentMaxAction = currentChild;
//                    System.out.println("New max is "+ currentMax + " for destination "+currentMaxAction.getXFinal()+","+currentMaxAction.getyFinal());
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
//            System.out.println("No child actions found, best action for node " + global_count + " was destination " + currentMaxAction.getXFinal() + "," + currentMaxAction.getyFinal());
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

	@Override
	public Action call() throws Exception {
		return this.IDFS(0,childActions);
	}






}
