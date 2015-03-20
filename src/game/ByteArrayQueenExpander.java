package game;

import ai.AmazonSuccessorByte;

import java.util.ArrayList;

/**
 * Created by nolan on 18/03/15.
 * Queen expander to be used by multithreaded search.
 * Will expand one queens position.
 */
public class ByteArrayQueenExpander {
    private byte[] queen;
    private BoardStateByte board;
    private ArrayList<Action> level = new ArrayList<>();

    public ByteArrayQueenExpander(BoardStateByte b, byte[] queen) {
        this.queen = queen;
        this.board = new BoardStateByte(b);
    }



    /*
        Expands
     */
    private void run(){
        AmazonSuccessorByte sf = new AmazonSuccessorByte();

            //For every position available to the one queen.
            ArrayList<byte[]> moves = sf.getAllDirections(queen, board);
            for(byte[] moveForQueen: moves){

                //Create a clone of the board.
                Action movedQueen = new Action(queen,moveForQueen,board);
                movedQueen.setUpForThrowEvaluation();

                //For every stone that can be thrown from that move.
                ArrayList<byte[]> possibleStoneThrows = sf.getAllDirections(moveForQueen, board);
                for(byte[] throwPos: possibleStoneThrows) {

                    //Make a clone of the board
                    Action thrownStone = new Action(movedQueen);
                    thrownStone.addThrow(throwPos); //Throw the stone.

                    //Add to the results set.
                    level.add(thrownStone);
                }
                movedQueen.tearDownFromThrowEvaluation();

            }
        }


    /**
     * Start method for queen.
     * @return returns the results of the node expansion.
     */
    public ArrayList<Action> start(){
        this.run();
        return this.level;
    }

}
