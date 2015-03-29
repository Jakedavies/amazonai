package Tree;

import ai.AmazonSuccessorByte;
import game.Action;
import game.BoardStateByte;
import game.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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


    /**
     * 
     * @param us - True if White, False if Black
     */
    public void generateDepthsOurMoveIsWhite(boolean us){
        ArrayList<Action> lastGenerated = new ArrayList<>();
        ArrayList<Node> toLoopThrough = new ArrayList<>();
        
        Comparator<Node> comp;
        
        if(us){
        	comp = Node.ID_ASC_FRIENDLY;
        }else{
        	comp = Node.ID_ASC_BLACK;
        }
        
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
            ArrayList<Node> levelNodes = new ArrayList<>();
            if(toLoopThrough.size() == 0){
                run = false;
                break;
            }

            for(Node n : toLoopThrough){
                ArrayList<Action> level = new ArrayList<>();

                if(System.currentTimeMillis()-createTime <= 6000) {

                    level.addAll(sf.generateTreeLevelThreaded(n.getAction().makeThisMove(), white));
                    for (Action a : level) {
                        Node adder = new Node(a, white);
                        levelNodes.add(adder);
                        adder.setParent(n); //Links up but not back down so that tree wont see non full gen levels
                    }
                    
                    if(!levelNodes.isEmpty()){
	                    //IF WE SORT AND TRIM, DO IT HERE..
	                    Node[] trim = new Node[levelNodes.size()];
	                    trim = levelNodes.toArray(trim);
	                    Arrays.parallelSort(trim, Node.ID_ASC_FRIENDLY);
	                    levelNodes = new ArrayList<Node>();
	                    for( int i = 0; i <= trim.length/Constants.TRIM_VALUE; i++){
	                    	levelNodes.add(trim[i]);
	                    }
                    }
                }
                else{
                    run = false;
                    broken = true;
                    maxDepth--;
                }
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
    	ArrayList<Node> toLoopThrough = new ArrayList<>();
    	lastGenerated.addAll(sf.generateTreeLevelThreaded(this.initialState, us));
    	for(Action a : lastGenerated){
    		Node n = new Node(a, us);
    		n.setParent(root);
    		root.addChild(n);
    		toLoopThrough.add(n);
    		a.getValue(us); //Moves calculation time to here.
    	}
    	boolean white = us;
    	boolean run = true;
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
    		ArrayList<Node> levelNodes = new ArrayList<>();
    		int count = 0;
    		if(toLoopThrough.size() == 0){
    			run = false;
    			break;
    		}
    		if(toLoopThrough.size() > 100000){
    			List<Node> l;
    			toLoopThrough.sort(Node.ID_ASC_BLACK);
    			l = toLoopThrough.subList(0, toLoopThrough.size()/1000);
    			toLoopThrough = new ArrayList<>();
    			toLoopThrough.addAll(l);
    		}
    		else{
    			List<Node> l;
    			toLoopThrough.sort(Node.ID_ASC_BLACK);
    			l = toLoopThrough.subList(0, toLoopThrough.size()/100);
    			toLoopThrough = new ArrayList<>();
    			toLoopThrough.addAll(l);
    		}
    		for(Node n : toLoopThrough){
    			ArrayList<Action> level = new ArrayList<>();
    			count++;
    			if(System.currentTimeMillis()-createTime <= Constants.CUT_OUT_TIME) {
    				//IF WE SORT AND TRIM, DO IT HERE..
    				level.addAll(sf.generateTreeLevelThreaded(n.getAction().makeThisMove(), white));
    				for (Action a : level) {
    					Node adder = new Node(a, white);
    					levelNodes.add(adder);
    					adder.setParent(n); //Links up but not back down so that tree wont see non full gen levels
    				}
    				 if(!levelNodes.isEmpty()){
 	                    //IF WE SORT AND TRIM, DO IT HERE..
 	                    Node[] trim = new Node[levelNodes.size()];
 	                    trim = levelNodes.toArray(trim);
 	                    Arrays.parallelSort(trim, Node.ID_ASC_BLACK);
 	                    levelNodes = new ArrayList<Node>();
 	                    for( int i = 0; i <= trim.length/Constants.TRIM_VALUE; i++){
 	                    	levelNodes.add(trim[i]);
 	                    }
                     }
    			}
    			else{
    				run = false;
    				broken = true;
    				maxDepth--;
    			}
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

            if (currTime - createTime > 27000) {
                System.out.println(currTime - createTime);
                break;
            }
            int currentCost2 = currentCost + childAction.getValue();
//            ArrayList<Action> l = childAction.getChildActions();
//            l.sort(Action.ID_ASC_FRIENDLY);
//            Action currentChild = l.get(0);
            Node currentChild = IDFS(currentCost2 + childAction.getValue(), childAction.getChildren());

            if (currentChild != null) {
                System.out.println("Current max is "+(currentCost2+currentChild.getValue()));//TRUE FLAG ADDED FOR TESTING

                if (currentMin > (currentCost2 + currentChild.getValue())) //TRUE FLAG ADDED FOR TESTING
                {
                    currentMin = currentCost2 + currentChild.getValue(); //TRUE FLAG ADDED FOR TESTING
                    currentminAction = currentChild;
                    System.out.println("New max is "+ currentMin + " for destination "+currentminAction.getAction().getXFinal()+","+currentminAction.getAction().getyFinal());
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

            if (currTime - createTime > 27000) {
                System.out.println(currTime - createTime);
                break;
            }
            int currentCost2 = currentCost + childAction.getValue();
//            ArrayList<Action> l = childAction.getChildActions();
//            l.sort(Action.ID_ASC_FRIENDLY);
//            Action currentChild = l.get(0);
            Node currentChild = IDFS(currentCost2 + childAction.getValue(), childAction.getChildren());

            if (currentChild != null) {
                System.out.println("Current max is "+(currentCost2+currentChild.getValue()));//TRUE FLAG ADDED FOR TESTING

                if (currentMax < (currentCost2 + currentChild.getValue())) //TRUE FLAG ADDED FOR TESTING
                {
                    currentMax = currentCost2 + currentChild.getValue(); //TRUE FLAG ADDED FOR TESTING
                    currentMaxAction = currentChild;
                    System.out.println("New max is "+ currentMax + " for destination "+currentMaxAction.getAction().getXFinal()+","+currentMaxAction.getAction().getyFinal());
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
