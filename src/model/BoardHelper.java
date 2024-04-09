package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class BoardHelper {
    private Piece[][] board;
    private ArrayList<Piece> dudoan;

    private int i,j=0;

    public BoardHelper(Piece[][] board, ArrayList<Piece> dudoan){
        this.board = new Piece[Board.ROWS][Board.COLS];
        this.dudoan = dudoan;
    }

    public ArrayList<Piece> dudoanvitri(Piece[][] afterboard, int value){
        board = afterboard;
        dudoan = new ArrayList<>();
        for (int i = 0; i < Board.ROWS*Board.COLS; i++) {
            int row = i / Board.ROWS;
            int col = i % Board.COLS;
            if (canPlay(row,col,value)) {
                dudoan.add(new Piece(row, col, value));
            }
        }
        return dudoan;
    }

    public boolean canPlay(int row, int col, int value) {
        if(board[row][col].getValue() !=0) return false;
        //top
        for (i = row - 1; i >= 0; i--) {
            if (board[i][col].getValue() == 0) {//neu gap phai vi tri chua dat co thi sai-> khong dat dc co
                break;
            }
            if (board[i][col].getValue() == value) {//neu
                if (Math.abs(i-row) > 1) return true;
                break;
            }
        }
        //bottom
        for (i = row+1; i <Board.ROWS ; i++) {
            if (board[i][col].getValue() == 0) {
                break;
            }
            if (board[i][col].getValue() == value) {
                if (Math.abs(i-row) > 1) return true;
                break;
            }
        }
        //left
        for (i = col-1; i >=0 ; i--) {
            if(board[row][i].getValue()==0){
                break;
            }
            if(board[row][i].getValue()==value){
                if (Math.abs(i-col) > 1) return true;
                break;
            }
        }
        //right
        for (i = col+1; i < Board.COLS ; i++) {
            if(board[row][i].getValue()==0){
                break;
            }
            if(board[row][i].getValue()==value){
                if (Math.abs(i-col) > 1) return true;
                break;
            }
        }
        //bottomright
        for (i = row+1,j = col + 1; i <Board.ROWS && j < Board.COLS ; i++,j++) {
            if (board[i][j].getValue() == 0) {
                break;
            }
            if (board[i][j].getValue() == value) {
                if(Math.abs(i-row)>1&& Math.abs(i-col)>1) return true;
                break;
            }
        }
        //bottomleft
        for (i = row+1, j= col-1; i < Board.ROWS && j>=0 ; i++,j--) {
            if (board[i][j].getValue() == 0) {
                break;
            }
            if (board[i][j].getValue() == value) {
                if(Math.abs(i-row)>1&& Math.abs(i-col)>1) return true;

                break;
            }
        }
        //topright
        for (i = row-1, j= col+1; i >=0&&j < Board.COLS ; i--,j++) {
            if(board[i][j].getValue()==0){
                break;
            }
            if(board[i][j].getValue()==value){
                if(Math.abs(i-row)>1&& Math.abs(i-col)>1) return true;
                break;
            }
        }
        //topleft
        for (i = row-1, j = col-1; i >=0 && j>=0 ; i--, j--){
            if (board[i][j].getValue() == 0) {
                break;
            }
            if (board[i][j].getValue() == value) {
                if(Math.abs(i-row)>1&& Math.abs(i-col)>1) return true;
                break;
            }
        }
        return false;
    }

    //tao ra 1 cai Board moi
}
