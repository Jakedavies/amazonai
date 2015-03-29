package Tree;

import ai.AmazonSuccessorByte;
import game.Action;
import game.BoardStateByte;

import java.util.ArrayList;
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


    public void generateDepthsOurMoveIsWhite(boolean us){
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
            int count = 0;


            for(Node n : toLoopThrough){
                ArrayList<Action> level = new ArrayList<>();

                count++;
                if(System.currentTimeMillis()-createTime <= 15000) {
                    level.addAll(sf.generateTreeLevelThreaded(n.getAction().makeThisMove(), white));
                    for (Action a : level) {
                        Node adder = new Node(a, white);
                        levelNodes.add(adder);
                        adder.setParent(n); //Links up but not back down so that tree wont see non full gen levels
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

    public Node getBestMove(){
       Node best =  IDFS(0, root.getChildren());
        Node ret = best;
        System.out.println(root.getChildren().get(0));
        while(!ret.getparent().isRoot()){
            ret = ret.getparent();
        }
        return ret;
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
