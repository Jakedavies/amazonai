package Tree;

import game.Action;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by nolan on 28/03/15.
 */
public class Node {
    private Action action;
    private Node parent;
    private boolean friendly;
    private ArrayList<Node> children ;
    private boolean isRoot;
    private boolean killDepth = false;


    public Node(){
        this.isRoot = true;
        this.parent = null;
        this.children = new ArrayList<>();
    }


    public Node(Action a, boolean friendly){
        this.action = a;
        this.friendly = friendly;
        this.isRoot = false;
        this.children = new ArrayList<>();
    }

    public String toString(){
        String s ="MOVE FROM: " + action.getOriginal()[0] +"," + action.getOriginal()[1] + "\n MOVE TO: " + action.getXFinal() + "," + action.getyFinal() + "\n VALUE: " + this.getValue();
        return s;
    }
    public boolean isRoot(){
        return this.isRoot;
    }



    public Action getAction(){
        return this.action;
    }
    public void addChild(Node n){
        this.children.add(n);
    }
    public Node getparent(){
        return this.parent;
    }
    public void setParent(Node n){
        this.parent = n;
    }


    public ArrayList<Node> getChildren(){
        return this.children;
    }

    public int getValue(){
        return this.action.getValue(this.friendly);
    }
    public void toggleKillDepth(){
        this.killDepth = !this.killDepth;
    }
    public boolean isKillDepth(){
        return this.killDepth;
    }



    public static final Comparator<Node> ID_ASC_FRIENDLY = new Comparator<Node>() {
        public int compare(Node a1, Node a2) {

            if(a1.getValue()>a2.getValue()){
                return -1;
            }
            if(a1.getValue() == a2.getValue()){
                return 0;
            }
            else return 1;
            // I use explicit -1 to be clear that the order is reversed
        }
    };




}
