package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

public class Board {
    protected Piece[][] board;
    protected Piece[][] beforeboard;
    public static final int ROWS = 8;
    public static final int COLS = 8;
    private int scoreWhite;
    private int scoreBlack;
    private int i,j,count=0;

    public boolean isBlack = true;//hien tai dang la quan den
    public boolean isWhite=false;//hien tai dang la quan den

    public boolean WhiteContinue= false;

    private boolean gameOn;

    private boolean gameContinue;

    private Stack<Piece> dudoan;

    public Board(){
        board= new Piece[ROWS][COLS];
        gameOn= true;
        gameContinue = true;
        startBoard();
    }
    public Board(Piece[][] piece){
        board= piece;
        gameOn= true;
        gameContinue = true;
    }

    private void startBoard(){
        scoreBlack=2;
        scoreWhite=2;
        for (i = 0; i < ROWS; i++) {
            for (j = 0; j < COLS; j++) {
                    Piece piece = new Piece(i, j, 0);
                    board[i][j] = piece;
            }
        }

        board[ROWS/2-1][COLS/2-1].setValue(2);
        board[ROWS/2][COLS/2].setValue(2);
        board[ROWS/2][COLS/2-1].setValue(1);
        board[ROWS/2-1][COLS/2].setValue(1);
    }

    public void update(int row, int col) {
        if(gameContinue){

            if(isBlack && !isWhite) {
                Piece newPiece = new Piece(row, col, 1);
                if (canPutPiece(row, col, 1)) {
                    board[row][col] = newPiece;
                    checkBoard(1);
                }else System.out.println("black is fail");

                isWhite= true;
                isBlack= false;
                WhiteContinue=true;
            }
            else if(!isBlack && isWhite) {
                Piece newPiece = new Piece(row, col, 2);
                if (canPutPiece(row, col, 2)) {
                    board[row][col] = newPiece;
                    checkBoard(2);
                    System.out.println("white");
                }else System.out.println("white is fail");
                isWhite= false;
                isBlack= true;
                WhiteContinue= false;
            }

            gameOn = setFinish();
        }
    }

    public int getScore(int value){
        int score=0;
        for (i = 0; i < Board.ROWS; i++) {
            for (j = 0; j < Board.COLS; j++) {
                if(board[i][j].getValue()==value) score+=1;
            }
        }
        return score;
    }

    //tinh so quan trang trong moi lan lat sau do su dung minimax tinh maxWhite moi lan quan trang chay va minWhite moi lan quan dem=n chay
    //chay het ban co return ra cot va hang co gia tri score tang len thi duoc phep chay
    //su dung 1 map (list) de luu tru hang va cot co the chay
    //phat trien theo huong lua chon max

    public Piece[][] getBoard() {
        return board;
    }
    //kiem tra xem co dat co duoc khong
    public boolean canPutPiece(int row, int col, int value){
        if(board[row][col].getValue() !=0) return false;//kiem tra xem vi tri nay co dat duoc quan co khong
        return checkPiece(row,col,value);
    }

    private boolean checkPiece(int row, int col, int value){
        if(canputTop(row,col,value)) return true;//neu xac nhan vi tri nay dat duoc co thi lat co

        if(canputBottom(row, col, value)) return true;

        if(canputLeft(row, col, value)) return true;

        if(canputRight(row, col, value)) return true;

        if(canputRightTop(row, col, value)) return true;

        if(canputleftTop(row, col, value)) return true;

        if(canputleftBottom(row, col, value)) return true;

        if (canputRightBottom(row,col,value)) return true;


        return false;
    }

    private void checkBoard(int value){
        for (int k = 0; k < Board.ROWS*Board.COLS; k++) {
            int row = k/Board.COLS;
            int col = k/Board.ROWS;
            if(board[row][col].getValue()==value && checkPiece(row,col,value)) board[row][col].setValue(value);
        }
    }

    //kiem tra xem neu tu vi tri dat di len co lat duoc quan co nao khong
    private boolean canputTop(int row, int col, int value) {
        for (i = row-1; i >=0 ; i--) {
            if(board[i][col].getValue()==0){//neu gap phai vi tri chua dat co thi sai-> khong dat dc co
                return false;
            }
            if(board[i][col].getValue()==value){//neu
                if(flipPieceTop(row,col,value,i)>0) return true;
                break;
            }
        }
        return false;
    }
    private int flipPieceTop(int row, int col, int value, int row2){
        count=0;
        for (i = row-1; i > row2 ; i--) {
            board[i][col].setValue(value);
            count++;
        }
        return count;
    }

    private boolean canputBottom(int row, int col, int value) {
        for (i = row+1; i <Board.ROWS ; i++) {
            if (board[i][col].getValue() == 0) {
                return false;
            }
            if (board[i][col].getValue() == value) {
                if(flipPieceBottom(row, col, value, i)>0) return true;
               break;
            }
        }
        return false;
    }

    private int flipPieceBottom(int row, int col, int value, int row2){
        count=0;
        for (i = row+1; i <row2 ; i++) {
            board[i][col].setValue(value);
            count++;
        }
        return count;
    }

    private boolean canputLeft(int row, int col, int value) {
        for (i = col-1; i >=0 ; i--) {
            if(board[row][i].getValue()==0){
                return false;
            }
            if(board[row][i].getValue()==value){
                if(flipPieceLeft(row,col,value, i)>0) return true;
                break;
            }
        }
        return false;
    }

    private int flipPieceLeft(int row, int col, int value, int col2){
        count =0;
        for (i = col-1; i >col2 ; i--) {
           board[row][i].setValue(value);
           count++;
        }
        return count;
    }
    private boolean canputRight(int row, int col, int value) {
        for (i = col+1; i < Board.COLS ; i++) {
            if(board[row][i].getValue()==0){
                return false;
            }
            if(board[row][i].getValue()==value){
                if (flipPieceRight(row,col,value,i)>0) return true;
                break;
            }
        }
        return false;
    }
    private int flipPieceRight(int row, int col, int value, int col2){
        count =0;
        for (int i = col+1; i < col2 ; i++) {
            board[row][i].setValue(value);
            count++;
        }
        return count;
    }

    private boolean canputRightTop(int row, int col, int value) {
        for (i = row-1, j= col+1; i >=0&&j < Board.COLS ; i--,j++) {
            if(board[i][j].getValue()==0){
                return false;
            }
            if(board[i][j].getValue()==value){
                if(flipPieceTopRight(row,col,i,j,value)>0) return true;
                break;
            }
        }
        return false;
    }

    private int flipPieceTopRight(int row, int col,int row2, int col2, int value) {
        count=0;
        for (i = row-1, j= col+1; i >row2&&j < col2 ; i--,j++) {
            board[i][j].setValue(value);
            count++;
        }
        return count;
    }

    private boolean canputleftTop(int row, int col, int value) {
        for (i = row-1, j = col-1; i >=0 && j>=0 ; i--, j--){
            if (board[i][j].getValue() == 0) {
                return false;
            }
            if (board[i][j].getValue() == value) {
                if(flipPieceTopLeft(row,col,i,j,value)>0) return true;
                break;
            }
        }
        return false;
    }
    private int flipPieceTopLeft(int row, int col,int row2, int col2, int value){
        count =0;
        for (i = row-1, j = col-1; i >= row2 && j >= col2 ; i--, j--) {
            board[i][j].setValue(value);
            count++;
        }
        return count;
    }

    private boolean canputleftBottom(int row, int col, int value) {
        for (i = row+1, j= col-1; i < Board.ROWS && j>=0 ; i++,j--) {
            if (board[i][j].getValue() == 0) {
                return false;
            }
            if (board[i][j].getValue() == value) {
                if(flipPieceBottomLeft(row,col,i,j,value)>0) return true;

                break;
            }
        }
        return false;
    }
    private int flipPieceBottomLeft(int row, int col,int row2, int col2, int value){
        count =0;
        for (i = row+1, j= col-1; i < row2 && j> col2 ; i++,j--){
            board[i][j].setValue(value);
            count++;
        }
        return count;
    }

    private boolean canputRightBottom(int row, int col, int value) {
        for (i = row+1,j = col + 1; i <Board.ROWS && j < Board.COLS ; i++,j++) {
            if (board[i][j].getValue() == 0) {
                return false;
            }
            if (board[i][j].getValue() == value) {
                if(flipPieceBottomRight(row,col,i,j,value) >0) return true;
                break;
            }
        }
        return false;
    }
    private int flipPieceBottomRight(int row, int col,int row2, int col2, int value) {
        count =0;
        for (i = row+1,j = col + 1; i <row2 && j < col2 ; i++,j++){
            board[i][j].setValue(value);
            count++;
        }
        return count;
    }


    //neu con cho trong thi setDead
    private boolean setFinish(){
        for (int i = 0; i < Board.ROWS; i++) {
            for (int j = 0; j < Board.COLS; j++) {
                if(board[i][j].getValue() ==0) return true;
                //neu con ton
            }
        }
        return false;
    }

    public void reset(){
        board= new Piece[ROWS][COLS];
        gameOn= true;
        gameContinue = true;
        startBoard();

    }

    public void printBoard(){
        for (int k = 0; k < Board.COLS; k++) {
            for (int l = 0; l < Board.ROWS; l++) {
                System.out.print("" + board[k][l].getValue()+" ");
            }
            System.out.println();
        }
        System.out.println("game on:"+ gameOn);
        System.out.println("game Continue:"+ gameContinue);
    }
    public void printBoard1(Piece[][] board){
        for (int k = 0; k < Board.COLS; k++) {
            for (int l = 0; l < Board.ROWS; l++) {
                System.out.print("" + board[k][l].getValue()+" ");
            }
            System.out.println();
        }
        System.out.println("game on:"+ gameOn);
        System.out.println("game Continue:"+ gameContinue);
    }


    public void setBoard(Piece[][] board) {
        this.board = board;
    }
    public int getScoreWhite(){return scoreWhite;}
    public int getScoreBlack(){return scoreBlack;}

    public boolean isGameOn() {
        return gameOn;
    }

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    public boolean isGameContinue() {
        return gameContinue;
    }

    public void setGameContinue(boolean gameContinue) {
        this.gameContinue = gameContinue;
    }

    public Stack<Piece> dudoanvitri(int value){
        dudoan = new Stack<>();
        beforeboard = new Piece[ROWS][COLS];
        beforeboard= board;
        printBoard1(beforeboard);
        for (int i = 0; i < Board.ROWS*Board.COLS; i++) {
            int row = i / Board.ROWS;
            int col = i % Board.COLS;
            if (canPutPiece(row, col, value)) {
                dudoan.push(new Piece(row, col, value));
            }
            board= beforeboard;
        }
        return dudoan;
    }
}
