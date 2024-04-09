package model;

import model.Board;
import model.Piece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class BoardHelper {
    static int i,j=0;

    //lay ra cac vi tri co the danh
    public static ArrayList<Piece> getPointMove(Piece[][] afterboard, int value){
        Piece[][] board = afterboard;
        ArrayList<Piece> dudoan = new ArrayList<>();
        for (int i = 0; i < Board.ROWS*Board.COLS; i++) {
            int row = i / Board.ROWS;
            int col = i % Board.COLS;
            if (canPlay(row,col,value, board)) {
                dudoan.add(new Piece(row, col, value));
            }
        }
        return dudoan;
    }

    //kiem tra xem vi tri do co the danh hay khong
    public static boolean canPlay(int row, int col, int value, Piece[][] board) {
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

    //kiem tra xem co con vi tri nao de danh khong-> tro choi ket thuc
    public static boolean isGameFinished(Piece[][] board, int value){
        ArrayList<Piece> pieces = new ArrayList<>();
        pieces= getPointMove(board,value);
        if(pieces.isEmpty()) return false;
        return true;
    }


    //tao ra 1 cai Board moi sau 1 buoc di chuyen
    public static Board getNewBoardAfterMove(Board board, Point point, int value){
        //get clone of old board
        Board newboard = new Board(board.getBoard());
        Piece move = new Piece(point.x, point.y, value);

        //dat quan co va lat co
        if(newboard.canPutPiece(move.getRow(),move.getCol(),move.getValue())) {
            newboard.getBoard()[move.getRow()][move.getCol()] = move;
            newboard.checkBoard(move.getValue());
        }

        return newboard;
    }

    public static Board getNewBoardAfterMove(Board board,Piece move){
        //get clone of old board
        Board newboard = new Board(board.getBoard());

        //dat quan co va lat co
        if(newboard.canPutPiece(move.getRow(),move.getCol(),move.getValue())) {
            newboard.getBoard()[move.getRow()][move.getCol()] = move;
            newboard.checkBoard(move.getValue());
        }

        return newboard;
    }

    public static int getTotalStoneCount(Board board){
        return board.getScore(1)+ board.getScore(2);
    }

    public static int getPlayerStoneCount(Board board, int player){
        if(player==1) return board.getScore(1);
        return board.getScore(2);
    }

    //kiem tra xem co con vi tri nao co the di chuyen nua khong
    public static boolean hasAnyMoves(Piece[][] board, int value){
        return getPointMove(board,value).size() > 0;
    }
    //neu khong con vi tri nao di chuyen
    public static boolean isGameFinished(Piece[][] board){

        return !(hasAnyMoves(board,1) || hasAnyMoves(board,2));
    }

}
