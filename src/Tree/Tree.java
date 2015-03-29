package Tree;

import ai.AmazonSuccessorByte;
import game.Action;
import game.BoardStateByte;

import java.util.ArrayList;

/**
 * Created by nolan on 28/03/15.
 */
public class Tree {
    private BoardStateByte initialState;
   long createTime;
    private Node root;
    private AmazonSuccessorByte sf = new AmazonSuccessorByte();



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

        while(run){
            System.out.println("NEW LEVEL");
            System.out.println(toLoopThrough.size());
            white = !white;
            ArrayList<Action> level = new ArrayList<>();
            ArrayList<Node> levelNodes = new ArrayList<>();
            int count = 0;

            for(Node n : toLoopThrough){
                count++;
                if(System.currentTimeMillis()-createTime <= 15000) {
                    level.addAll(sf.generateTreeLevelThreaded(n.getAction().makeThisMove(), white));
                    for (Action a : level) {
                        Node adder = new Node(a, white);
                        levelNodes.add(adder);
                        adder.setParent(n); //Links up but not back down so that tree wont see non full gen levels
                    }
                    System.out.println(count);
                }
                else{
                    run = false;
                    broken = true;
                }

            }

            if(!broken){
                toLoopThrough = levelNodes;
                System.out.println(levelNodes.size());
                for(Node n : levelNodes){
                    n.getparent().addChild(n);
                }
            }




        }


    }


}
