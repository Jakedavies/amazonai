package Tree;

import ai.AmazonSuccessorByte;
import game.Action;
import game.BoardStateByte;
import game.Constants;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by nolan on 28/03/15.
 */
public class Tree {
    private BoardStateByte initialState;
    long createTime;
    private Node root;
    private AmazonSuccessorByte sf = new AmazonSuccessorByte();
    int maxDepth =0;
    int global_count=0;



    public Tree(BoardStateByte initialState){
        this.initialState = initialState;
        this.root = new Node();
        createTime = System.currentTimeMillis();

    }


    public void generateDepthsOurMoveIsWhite(boolean us){
        ArrayList<Action> lastGenerated = new ArrayList<>();
        ArrayDeque<Node> toLoopThrough = new ArrayDeque<>();

        lastGenerated.addAll(sf.generateTreeLevelThreaded(this.initialState, us));

        for(Action a : lastGenerated){
            Node n = new Node(a, us);
            n.setParent(root);
            root.addChild(n);
            toLoopThrough.add(n);
            a.getValue(us); //Moves calculation time to here.
        }

        boolean white = us;
        boolean run =  true;
        boolean broken = false;
        maxDepth++;
        
        ExecutorService executorService = Executors.newWorkStealingPool(Constants.MAX_THREADS);

        while(run){
            if(toLoopThrough.size() == 0){
                run = false;
                continue;
            }

            maxDepth++;
            System.out.println("NEW LEVEL");
            System.out.println(toLoopThrough.size());

            white = !white;
            ArrayDeque<Node> levelNodes = new ArrayDeque<>();
            int count = 0;

            if(toLoopThrough.size() == 0){
                run = false;
                break;
            }
    		int to = toLoopThrough.size() / Constants.MAX_THREADS;
    		
            List<Callable<ArrayList<Node>>> threads = new ArrayList<>();
        	for(int i = 1; i <= Constants.MAX_THREADS; i++){
        		ArrayList<Node>list = new ArrayList<Node>();
        		int end = to * i;
        		System.out.println("End =" +end);
        		int start = to * (i - 1);
        		System.out.println("Start =" +start);

        		for (int g = start; g <= end; g++) {
        			if(!toLoopThrough.isEmpty()){
        				list.add(toLoopThrough.removeFirst());
        			}
        		}
        		threads.add(new LevelCallable(white, sf, createTime, list));
        	}
        	
        	try {
            	List<Future<ArrayList<Node>>> futures = executorService.invokeAll(threads);
            	System.out.println(futures.size());

                for(Future<ArrayList<Node>> f: futures){
                	levelNodes.addAll(f.get());
                }

        	} catch (InterruptedException |ExecutionException  e) {
        		e.printStackTrace();
        		broken = true;
        		run = false;
        		maxDepth--;
        	}   
            System.out.println("LEVEL FINISHED");

            if(!broken){
                toLoopThrough = levelNodes;
                System.out.println(levelNodes.size());
                for(Node n : levelNodes){
                    n.getparent().addChild(n);
                }
            }
        }
    }


    public void generateDepthsOurMoveIsBlack(boolean us){
        ArrayList<Action> lastGenerated = new ArrayList<>();
        ArrayDeque<Node> toLoopThrough = new ArrayDeque<>();

        ExecutorService executorService = Executors.newWorkStealingPool(Constants.MAX_THREADS);
        
        lastGenerated.addAll(sf.generateTreeLevelThreaded(this.initialState, us));

        for(Action a : lastGenerated){
            Node n = new Node(a, us);
            n.setParent(root);
            root.addChild(n);
            toLoopThrough.add(n);
            a.getValue(us); //Moves calculation time to here.
        }
        boolean white = us;
        boolean run =  true;
        boolean broken = false;
        maxDepth++;

        while(run){

            if(toLoopThrough.size() == 0){
                run = false;
                continue;
            }
            maxDepth++;
            System.out.println("NEW LEVEL");
            System.out.println(toLoopThrough.size());

            white = !white;
            ArrayDeque<Node> levelNodes = new ArrayDeque<>();
            int count = 0;

            if(toLoopThrough.size() == 0){
                run = false;
                break;
            }
    		int to = toLoopThrough.size() / Constants.MAX_THREADS;

            List<Callable<ArrayList<Node>>> threads = new ArrayList<>();
        	for(int i = 1; i <= Constants.MAX_THREADS; i++){
        		ArrayList<Node>list = new ArrayList<Node>();
        		int end = to * i;
        		int start = to * (i - 1);

        		for (int g = start; g < end; g++) {
        			if(!toLoopThrough.isEmpty()){
        				list.add(toLoopThrough.removeFirst());
        			}
        		}
        		threads.add(new LevelCallable(white, sf, createTime, list));
        	}
        	
        	try {
            	List<Future<ArrayList<Node>>> futures = executorService.invokeAll(threads);
            	System.out.println(futures.size());
            	
                for(Future<ArrayList<Node>> f: futures){
                	levelNodes.addAll(f.get());
                }

        	} catch (InterruptedException |ExecutionException  e) {
        		e.printStackTrace();
        		broken = true;
        		run = false;
        		maxDepth--;
        	}   
            System.out.println("LEVEL FINISHED");

            if(!broken){
                toLoopThrough = levelNodes;
                System.out.println(levelNodes.size());
                for(Node n : levelNodes){
                    n.getparent().addChild(n);
                }
            }
        }
    }

    public Node getBestMoveAsWhite(){
        Node best =  IDFS(0, root.getChildren());
        Node ret = best;
        while(!ret.getparent().isRoot()){
            ret = ret.getparent();
            System.out.println("ROOTSWAP");
        }
        return ret;
    }

    public Node getBestMoveAsBlack(){
        Node best =  IDFSAsBlack(0, root.getChildren());
        Node ret = best;
        while(!ret.getparent().isRoot()){
            ret = ret.getparent();
            System.out.println("ROOTSWAP");
        }
        return ret;
    }





    public Node IDFSAsBlack(int currentCost, ArrayList<Node> childActions) {
        if (childActions == null) {
            return null;
        }

        int currentMin = Integer.MAX_VALUE;
        Node currentminAction = null;
        for (Node childAction : childActions) {
            long currTime = System.currentTimeMillis();

            if (currTime - createTime > Constants.TIME_OUT_SEARCH) {
                System.out.println(currTime - createTime);
                break;
            }
            int currentCost2 = currentCost + childAction.getValue();
//            ArrayList<Action> l = childAction.getChildActions();
//            l.sort(Action.ID_ASC_FRIENDLY);
//            Action currentChild = l.get(0);
            Node currentChild = IDFS(currentCost2 + childAction.getValue(), childAction.getChildren());

            if (currentChild != null) {
//                System.out.println("Current max is "+(currentCost2+currentChild.getValue()));//TRUE FLAG ADDED FOR TESTING

                if (currentMin > (currentCost2 + currentChild.getValue())) //TRUE FLAG ADDED FOR TESTING
                {
                    currentMin = currentCost2 + currentChild.getValue(); //TRUE FLAG ADDED FOR TESTING
                    currentminAction = currentChild;
//                    System.out.println("New max is "+ currentMin + " for destination "+currentminAction.getAction().getXFinal()+","+currentminAction.getAction().getyFinal());
                }
            }
        }
        if (currentminAction == null) {
            currentMin = Integer.MIN_VALUE;
            int loop_count = 0;
            for (Node childAction : childActions) {
                if (childAction.getValue() > currentMin) { //TRUE FLAG ADDED FOR TESTING
                    currentMin = childAction.getValue(); //TRUE FLAG ADDED FOR TESTING
                    currentminAction = childAction;
                }
            }
            global_count++;
        }
        return currentminAction;
    }

    public Node IDFS(int currentCost, ArrayList<Node> childActions) {
        if (childActions == null) {
            return null;
        }

        int currentMax = Integer.MIN_VALUE;
        Node currentMaxAction = null;
        for (Node childAction : childActions) {
            long currTime = System.currentTimeMillis();

            if (currTime - createTime > Constants.TIME_OUT_SEARCH) {
                System.out.println(currTime - createTime);
                break;
            }
            int currentCost2 = currentCost + childAction.getValue();
//            ArrayList<Action> l = childAction.getChildActions();
//            l.sort(Action.ID_ASC_FRIENDLY);
//            Action currentChild = l.get(0);
            Node currentChild = IDFS(currentCost2 + childAction.getValue(), childAction.getChildren());

            if (currentChild != null) {
//                System.out.println("Current max is "+(currentCost2+currentChild.getValue()));//TRUE FLAG ADDED FOR TESTING

                if (currentMax < (currentCost2 + currentChild.getValue())) //TRUE FLAG ADDED FOR TESTING
                {
                    currentMax = currentCost2 + currentChild.getValue(); //TRUE FLAG ADDED FOR TESTING
                    currentMaxAction = currentChild;
//                    System.out.println("New max is "+ currentMax + " for destination "+currentMaxAction.getAction().getXFinal()+","+currentMaxAction.getAction().getyFinal());
                }
            }
        }
        if (currentMaxAction == null) {
            currentMax = Integer.MIN_VALUE;
            int loop_count = 0;
            for (Node childAction : childActions) {
                if (childAction.getValue() > currentMax) { //TRUE FLAG ADDED FOR TESTING
                    currentMax = childAction.getValue(); //TRUE FLAG ADDED FOR TESTING
                    currentMaxAction = childAction;
                }
            }
            global_count++;
        }
        return currentMaxAction;
    }



}