package ai;

import game.BoardState;
import game.BoardStateByte;
import game.ByteArrayQueenExpander;

import java.util.ArrayList;

/**
 * Created by nolan on 18/03/15.
 */
public class AmazonSuccessorByte {

    public ArrayList<byte[]> getAllDirections(byte[] v, BoardStateByte b )  {
        ArrayList<byte[]> possiblePositions = new ArrayList<>();
      /*
         X,Y Modifiers:
         -1, 0 = LEFT
         -1,-1 = LEFT DIAGONAL
         0,-1 =  UP
         1, -1 = RIGHT DIAGONAL
         1,0 = RIGHT
         1, 1 = RIGHT BOTTOM DIAGONAL
         0, 1 = DOWN
         -1, 1 = LEFT BOTTOM DIAGONAL
         Down, Down-Forward, Forward + Punch
         */
        byte x = -1;
        byte y = 0;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
         x = -1;
         y = -1;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
        x = 0;
        y = -1;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
        x = 1;
        y = -1;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
        x = 1;
        y = 0;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
        x = 1;
        y = 1;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
        x = 0;
        y = 1;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));
        x = -1;
        y = 1;
        possiblePositions.addAll(this.getDirectionMoves(v,x, y, b));

        return possiblePositions;
    }

    /**
     *
     * @param position The position of the queen that you would like to generate moves for.
     * @param x integer x modifier.
     * @param y integer y modifier.
     * @return
     */
    private ArrayList<byte[]> getDirectionMoves(byte[] position, byte x, byte y, BoardStateByte b) {
        ArrayList<byte[]> moves = new ArrayList<>();

        int i = 1;
        boolean run = true;
        while(run) {
            byte xMod = 0;
            byte yMod = 0;
            for(int j = 0; j < i; j ++){
                xMod += x;
                yMod += y;
            }


            byte xVal = 0;
            xVal += position[0];
            xVal += xMod;
            byte yVal = 0;
            yVal += position[1];
            yVal += yMod;


            run = b.isValidPosition(xVal, yVal);
            if(run) {
                byte[] move = new byte[2];
                move[0] = xVal;
                move[1] = yVal;
                moves.add(move);
                i++;
            }
        }
        return  moves;

    }

    public ArrayList<BoardStateByte> generateTreeLevel(BoardStateByte board) {
        ArrayList<BoardStateByte> level = new ArrayList<>();

        byte[][] queens = board.getWhiteQueens();
        for (int i = 0; i < 4; i++) {
            byte[] queen = queens[i];
            ArrayList<byte[]> moves = this.getAllDirections(queen, board);
            for(byte[] moveForQueen: moves){
                BoardStateByte movedQueen = new BoardStateByte(board);
                movedQueen.moveQueen(queen[0], queen[1], moveForQueen[0], moveForQueen[1]);
                ArrayList<byte[]> possibleStoneThrows = this.getAllDirections(moveForQueen, movedQueen);
                    for(byte[] throwPos: possibleStoneThrows) {
                        BoardStateByte thrownStone = new BoardStateByte(movedQueen);
                        thrownStone.throwStone(throwPos[0], throwPos[1]);
                        level.add(thrownStone);
                    }

                }
            }
        return level;

        }
    public ArrayList<BoardStateByte> generateTreeLevelThreaded(BoardStateByte board) {
        ArrayList<BoardStateByte> level = new ArrayList<>();

        byte[][] queens = board.getWhiteQueens();

            ByteArrayQueenExpander t1 = new ByteArrayQueenExpander(board, queens[0]);
            ByteArrayQueenExpander t2 = new ByteArrayQueenExpander(board, queens[1]);
            ByteArrayQueenExpander t3 = new ByteArrayQueenExpander(board, queens[2]);
            ByteArrayQueenExpander t4 = new ByteArrayQueenExpander(board, queens[3]);

        level.addAll(t1.start());
        level.addAll(t2.start());
        level.addAll(t3.start());
        level.addAll(t4.start());



        return level;

    }








}



