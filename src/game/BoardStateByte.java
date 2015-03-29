package game;

/**
 * Created by nolan on 18/03/15.
 * Class that represents the state of the board,
 * All queen positions and stone positions.
 */
public class BoardStateByte {



    private final byte[][] board = new byte[10][10];
    private final byte FREE_SPACE = 0;
    private final byte WHITE_QUEEN = 1;
    private final byte BLACK_QUEEN = 2;
    private final byte STONE = 3;

    private byte[][] whiteQueens = new byte[4][2];
    private byte[][] blackQueens = new byte[4][2];
    private byte[][] stones = new byte[92][2];
    private int lastStone = 0;

    public byte[][] getStones(){
        return this.stones;
    }



    /**
     * Overloaded constructor to be used to generate a new state.
     * Takes a parent board state and creates an identicle  clone of that board.
     * @param b the parent board state.
     */
    public BoardStateByte(BoardStateByte b){
        byte[][] black = b.getBlackQueens();
        byte[][] white = b.getWhiteQueens();
        byte[][] stone = b.stones;
        int lastPos = b.lastStone;

        /*
        For every queen in the original board state.
         */
        for(int i = 0; i < 4; i ++){
            //Positions of the original queens.
            byte xW = white[i][0];
            byte yW = white[i][1];
            byte xB = black[i][0];
            byte yB = black[i][1];

            //adds white and black queen clones.
            this.whiteQueens[i][0] = xW;
            this.whiteQueens[i][1] = yW;
            this.blackQueens[i][0] = xB;
            this.blackQueens[i][1] = yB;


            //Add the two queens.
            board[xW][yW] = WHITE_QUEEN;
            board[xB][yB] = BLACK_QUEEN;
        }

        //Throw a new stone for every stone in parent.
        for(int i = 0; i < lastPos; i ++){
            byte xS = stone[i][0];
            byte yS = stone[i][1];
            this.throwStone(xS, yS);
        }


    }

    /**
     * Throws a new stone.
     * @param x byte: X-Position to throw the stone to.
     * @param y byte: Y-Position to throw the stone to.
     */
    public void throwStone(byte x, byte y){
        this.board[x][y] = STONE;
        stones[lastStone][0] = x;
        stones[lastStone][1] = y;
        lastStone++;
    }
    public void undoThrowStone(byte x, byte y){
        this.board[x][y] = FREE_SPACE;
        lastStone--;
    }

    public int getLastStone(){
        return this.lastStone;
    }



    /**
     * Constructor for the initial board.
     */
    public BoardStateByte(){
        /*
        Add all white Queens
         */
        board[0][3] = WHITE_QUEEN;
        whiteQueens[0][0] = 0;
        whiteQueens[0][1] = 3;
        board[0][6] = WHITE_QUEEN;
        whiteQueens[1][0] = 0;
        whiteQueens[1][1] = 6;
        board[3][0] = WHITE_QUEEN;
        whiteQueens[2][0] = 3;
        whiteQueens[2][1] = 0;
        board[3][9] = WHITE_QUEEN;
        whiteQueens[3][0] = 3;
        whiteQueens[3][1] = 9;

        /*
        Add all black queens.
         */
        board[6][0] = BLACK_QUEEN;
        blackQueens[0][0] = 6;
        blackQueens[0][1] = 0;
        board[6][9] = BLACK_QUEEN;
        blackQueens[1][0] = 6;
        blackQueens[1][1] = 9;
        board[9][3] = BLACK_QUEEN;
        blackQueens[2][0] = 9;
        blackQueens[2][1] = 3;
        board[9][6] = BLACK_QUEEN;
        blackQueens[3][0] = 9;
        blackQueens[3][1] = 6;

        //No stones for the original board.
    }

    /*
        Getters for queen.
     */
    public byte[][] getWhiteQueens(){
        return this.whiteQueens;
    }
    public byte[][] getBlackQueens(){
        return this.blackQueens;
    }


    /**
     * Move queen method.
     * Given an x and y position for queen and final x and y updates and moves queen.
     * Method auto-detects wether friendly or foe queen and adjusts accordingly.
     * @param xOld byte: x-position of the original queen.
     * @param yOld byte: y- posistion of the original queen.
     * @param xNew byte: x position of the final queen.
     * @param yNew byte: y position of the final queen.
     */
    public void moveQueen(byte xOld, byte yOld, byte xNew, byte yNew){
        this.board[xOld][yOld] = 0;
        byte whiteOrBlack = FREE_SPACE;
        boolean quickSkip = false;

        /*
            Check all of the white queens.
         */
        for(int i = 0; i < 4; i ++){
            boolean matchXWhite = false;
            boolean matchYWhite = false;
            if(whiteQueens[i][0] == xOld){
                matchXWhite = true;
            }
            if(whiteQueens[i][1] == yOld){
                matchYWhite = true;
            }
            /*
            We only want to move the queen if it is a total match (x,y)
             */
            if(matchXWhite && matchYWhite){
                whiteOrBlack = WHITE_QUEEN;
                whiteQueens[i][0] = xNew;
                whiteQueens[i][1] = yNew;



                quickSkip = true;
            }
        }

        if(!quickSkip) {
            for (int i = 0; i < 4; i++) {
                boolean matchXBlack = false;
                boolean matchYBlack = false;
                if (blackQueens[i][0] == xOld) {
                    matchXBlack = true;
                }
                if (blackQueens[i][1] == yOld) {
                    matchYBlack = true;
                }
                if (matchXBlack && matchYBlack) {
                    whiteOrBlack = BLACK_QUEEN;
                    whiteQueens[i][0] = xNew;
                    whiteQueens[i][1] = yNew;
                }
            }
        }
        board[xOld][yOld] = FREE_SPACE;
        board[xNew][yNew] = whiteOrBlack;
    }

    /**
     * Returns if a position is empty and within bounds.
     * @param x byte x position in which to check.
     * @param y byte y position in which to check.
     * @return boolean true if it is a valid move.
     */
    public boolean isValidPosition(byte x, byte y){
        if(x >= 0 && x < 10) {
            return y >= 0 && y < 10 && board[x][y] == FREE_SPACE;
        }
        else return false;
    }


    /*
        BELOW:
            Translational Methods for GUI
            TODO: Create translational methods for GUI
            Re-Add Queen and Vector objects for drawing.
     */




}
