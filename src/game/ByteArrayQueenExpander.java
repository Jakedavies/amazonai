package game;

import ai.AmazonSuccessorByte;

import java.util.ArrayList;

/**
 * Created by nolan on 18/03/15.
 */
public class ByteArrayQueenExpander {
    private byte[] queen;
    private BoardStateByte board;
    private ArrayList<BoardStateByte> level = new ArrayList<>();

    public ByteArrayQueenExpander(BoardStateByte b, byte[] queen) {
        this.queen = queen;
        this.board = b;
    }



    private void run(){
        AmazonSuccessorByte sf = new AmazonSuccessorByte();

            ArrayList<byte[]> moves = sf.getAllDirections(queen, board);
            for(byte[] moveForQueen: moves){
                BoardStateByte movedQueen = new BoardStateByte(board);
                movedQueen.moveQueen(queen[0], queen[1], moveForQueen[0], moveForQueen[1]);
                ArrayList<byte[]> possibleStoneThrows = sf.getAllDirections(moveForQueen, movedQueen);
                for(byte[] throwPos: possibleStoneThrows) {
                    BoardStateByte thrownStone = new BoardStateByte(movedQueen);
                    thrownStone.throwStone(throwPos[0], throwPos[1]);
                    level.add(thrownStone);
                }

            }
        }

    public ArrayList<BoardStateByte> start(){
        this.run();
        return this.level;
    }





}
