package ai;

import game.Action;
import game.BoardStateByte;
import game.IDFSThreaded;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by jakedavies on 15-03-22.
 */
public class MinMaxTree {
    ArrayList<Action> generated = new ArrayList<>();
    final long startTime = System.currentTimeMillis();
    AmazonSuccessorByte successorByte;
    BoardStateByte initialBoardState;
    ArrayList<Action> actions;
    int depth;
    int global_count = 0;

    public ArrayList<Action> getChildActions(){
        return this.actions;
    }

    public ArrayList<Action> getGenerated(){
        return this.generated;
    }


    public MinMaxTree(int depth, BoardStateByte initialBoardState)
    {
        initialBoardState = (initialBoardState != null ? initialBoardState: new BoardStateByte());
        successorByte = new AmazonSuccessorByte();
        actions = successorByte.generateTreeLevelThreaded(initialBoardState, true); //TRUE FLAG ADDED FOR TESTING
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
        Action bestMove = IDFS(0,actions,0);
        System.out.println("Best move is "+bestMove.getXFinal()+","+bestMove.getyFinal());
        return  bestMove;
    }
    
    public Action getBestMoveThreaded(int threadCount, boolean friendly)
    {
        Action bestMove = getBestThreaded(threadCount, friendly);
        System.out.println("Best move Threaded is "+bestMove.getXFinal()+","+bestMove.getyFinal());
        return  bestMove;
    }

    public Action IDFS(int currentCost, ArrayList<Action> childActions,int depth)
    {
        boolean ourMove = depth%2!=1;

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
            int currentCost2 = currentCost+childAction.getValue(ourMove); //TRUE FLAG ADDED FOR TESTING
//            ArrayList<Action> l = childAction.getChildActions();
//            l.sort(Action.ID_ASC_FRIENDLY);
//            Action currentChild = l.get(0);
            Action currentChild = IDFS(currentCost2+childAction.getValue(ourMove),childAction.getChildActions(),depth++); //TRUE FLAG ADDED FOR TESTING

            if(currentChild != null){
//                System.out.println("Current max is "+(currentCost2+currentChild.getValue(true)));//TRUE FLAG ADDED FOR TESTING

                if(currentMax<(currentCost2+currentChild.getValue(ourMove))) //TRUE FLAG ADDED FOR TESTING
                {
                    currentMax = currentCost2+currentChild.getValue(ourMove); //TRUE FLAG ADDED FOR TESTING
                    currentMaxAction = currentChild;
                    System.out.println("New max is "+ currentMax + " for destination "+currentMaxAction.getXFinal()+","+currentMaxAction.getyFinal());
                }
            }
        }
        if(currentMaxAction == null) {
            currentMax = Integer.MIN_VALUE;
            int loop_count = 0;
            for (Action childAction : childActions) {
                if (childAction.getValue(ourMove) > currentMax) { //TRUE FLAG ADDED FOR TESTING
                    currentMax = childAction.getValue(ourMove); //TRUE FLAG ADDED FOR TESTING
                    currentMaxAction = childAction;
                }
            }
            global_count++;
//            System.out.println("No child actions found, best action for node " + global_count + " was destination " + currentMaxAction.getXFinal() + "," + currentMaxAction.getyFinal());
        }
        return currentMaxAction;
    }

    public Action getBestThreaded(int threadCount, boolean friendly){
    	
    	final int MAX_THREAD = threadCount;
    	ExecutorService executorService = Executors.newWorkStealingPool(MAX_THREAD);
    	
    	List<Callable<Action>> threads = new ArrayList<>();
    	for(int i = 1; i <= MAX_THREAD; i++){
    		threads.add(new IDFSThreaded(this, i, MAX_THREAD, friendly));
    	}
        	
    	try {
        	List<Future<Action>> futures = executorService.invokeAll(threads);
        	System.out.println(futures.size());

            ArrayList<Action> l = new ArrayList<>();
            for(Future<Action> f: futures){
            	l.add(f.get());
            }
            
            l.sort(Action.ID_ASC_FRIENDLY);

    		return l.get(0);

    	} catch (InterruptedException |ExecutionException  e) {
    		e.printStackTrace();
    	}   
    	
    	return null;
    }


}