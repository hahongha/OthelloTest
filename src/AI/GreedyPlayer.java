package AI;

import model.Board;
import model.BoardHelper;
import model.Piece;

import java.awt.*;
import java.util.ArrayList;

public class GreedyPlayer {
    public static Piece newMove(Board board, int value){
        ArrayList<Piece>  listMove = BoardHelper.getPointMove(board.getBoard(), value);
        int bestScore =0;
        int score=0;
        if(listMove.isEmpty()) return null;
        Piece bestMove = listMove.get(0);
        Board newboard = BoardHelper.getNewBoardAfterMove(board,new Point(bestMove.getRow(), bestMove.getCol()),value);
        bestScore = newboard.getScore(value);
        for (Piece piece : listMove){
            newboard = BoardHelper.getNewBoardAfterMove(board,new Point(bestMove.getRow(), bestMove.getCol()),value);
            score = newboard.getScore(value);
            if(score> bestScore) {
                bestMove = piece;
                bestScore = score;
            }
        }
        return bestMove;
    }
}
