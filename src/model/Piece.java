package model;

import java.awt.*;

public class Piece {
    private int value;
    private int col, row;

    public Piece(int row, int col, int value){
        this.col = col;
        this.row = row;
        this.value= value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public void setRow(int row){
        this.row= row;
    }
    public void setCol(int col){
        this.col = col;
    }
    public int getValue(){return value;}

    public int getCol() {
        return col;
    }
    public int getRow(){
        return row;
    }
    public Color setBackGround() {
        Color background = new Color(0x3C6255);
        if (value == 1) {
            background = Color.BLACK;
        } else if (value == 2) {
            background = Color.WHITE;
        }
        return background;
    }
}
