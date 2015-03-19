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
    private ArrayList<BoardStateByte> level = new ArrayList<>();

    public ByteArrayQueenExpander(BoardStateByte b, byte[] queen) {
        this.queen = queen;
        this.board = b;
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
                BoardStateByte movedQueen = new BoardStateByte(board);
                movedQueen.moveQueen(queen[0], queen[1], moveForQueen[0], moveForQueen[1]); //make the move.

                //For every stone that can be thrown from that move.
                ArrayList<byte[]> possibleStoneThrows = sf.getAllDirections(moveForQueen, movedQueen);
                for(byte[] throwPos: possibleStoneThrows) {

                    //Make a clone of the board
                    BoardStateByte thrownStone = new BoardStateByte(movedQueen);
                    thrownStone.throwStone(throwPos[0], throwPos[1]); //Throw the stone.

                    //Add to the results set.
                    level.add(thrownStone);
                }

            }
        }


    /**
     * Start method for queen.
     * @return returns the results of the node expansion.
     */
    public ArrayList<BoardStateByte> start(){
        this.run();
        return this.level;
    }

}
