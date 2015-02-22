package ai;

import ubco.ai.games.Amazon;

import java.util.ArrayList;

/**
 * Created by jakedavies on 15-02-22.
 */
public class AmazonSuccessorFunction {
    private AmazonState currentState;
    private ArrayList<AmazonState> successors;

    AmazonSuccessorFunction(AmazonState curState){
        this.currentState = curState;
    }
    public ArrayList<AmazonState> getSuccessors(){
        //TODO: iterate through possible successor states and add them to the successors list

        return successors;
    }
}
