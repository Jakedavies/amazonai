package Tree;

import game.Action;

import java.util.ArrayList;

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







}
