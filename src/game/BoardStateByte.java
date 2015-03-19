package game;

/**
 * Created by nolan on 18/03/15.
 */
public class BoardStateByte {


    private  byte[][] board = new byte[10][10];
    private  byte FREE_SPACE = 0;
    private byte WHITE_QUEEN = 1;
    private byte BLACK_QUEEN = 2;
    private byte STONE = 3;

    private byte[][] whiteQueens = new byte[4][2];
    private byte[][] blackQueens = new byte[4][2];
    private byte[][] stones = new byte[92][2];
    private int lastStone = 0;

    public BoardStateByte(BoardStateByte b){
        byte[][] black = b.getBlackQueens();
        byte[][] white = b.getWhiteQueens();
        byte[][] stone = b.stones;
        int lastPos = b.lastStone;

        for(int i = 0; i < 4; i ++){
            byte xW = white[i][0];
            byte yW = white[i][1];
            byte xB = black[i][0];
            byte yB = black[i][1];

            this.whiteQueens[i][0] = xW;
            this.whiteQueens[i][1] = yW;
            this.blackQueens[i][0] = xB;
            this.blackQueens[i][1] = yB;

            board[xW][yW] = 1;
            board[xB][yB] = 2;
        }

        for(int i = 0; i < lastPos; i ++){
            byte xS = stone[i][0];
            byte yS = stone[i][1];
            this.throwStone(xS, yS);
        }


    }

    public void throwStone(byte x, byte y){
        this.board[x][y] = STONE;
        stones[lastStone][0] = x;
        stones[lastStone][1] = y;
        lastStone++;
    }



    public BoardStateByte(){
        /*
        Add all white Queens
         */
        board[3][0] = WHITE_QUEEN;
        whiteQueens[0][0] = 3;
        whiteQueens[0][1] = 0;
        board[6][0] = WHITE_QUEEN;
        whiteQueens[1][0] = 6;
        whiteQueens[1][1] = 0;
        board[9][3] = WHITE_QUEEN;
        whiteQueens[2][0] = 9;
        whiteQueens[2][1] = 3;
        board[0][3] = WHITE_QUEEN;
        whiteQueens[3][0] = 0;
        whiteQueens[3][1] = 3;

        /*
        Add all black queens.
         */
        board[0][6] = BLACK_QUEEN;
        blackQueens[0][0] = 0;
        blackQueens[0][1] = 6;
        board[9][6] = BLACK_QUEEN;
        blackQueens[1][0] = 9;
        blackQueens[1][1] = 6;
        board[3][9] = BLACK_QUEEN;
        blackQueens[2][0] = 3;
        blackQueens[2][1] = 9;
        board[6][9] = BLACK_QUEEN;
        blackQueens[3][0] = 6;
        blackQueens[3][1] = 9;
    }

    public byte[][] getWhiteQueens(){
        return this.whiteQueens;
    }
    public byte[][] getBlackQueens(){
        return this.blackQueens;
    }

    public void moveQueen(byte xOld, byte yOld, byte xNew, byte yNew){
        this.board[xOld][yOld] = 0;
        byte whiteOrBlack = FREE_SPACE;
        boolean quickSkip = false;

        for(int i = 0; i < 4; i ++){
            boolean matchXWhite = false;
            boolean matchYWhite = false;
            if(whiteQueens[i][0] == xOld){
                matchXWhite = true;
            }
            if(whiteQueens[i][1] == yOld){
                matchYWhite = true;
            }
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
        board[xNew][yNew] = whiteOrBlack;
    }

    public boolean isValidPosition(byte x, byte y){
        if(x >= 0 && x < 10){
            if(y >= 0 && y < 10){
                return board[x][y] == FREE_SPACE;
            }
            else return false;
        }
        else return false;
    }





}
