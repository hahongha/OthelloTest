package AI;

import model.Board;
import model.BoardHelper;
import model.Piece;

import java.awt.*;
import java.util.ArrayList;

public class Minimax {
    static int nodesExplored = 0;


    //returns max score move
    public static Piece solve(Board board,int depth, int player){
        ArrayList<Piece> PossibleMoves = new ArrayList<>();
        PossibleMoves = BoardHelper.getPointMove(board.getBoard(),player);
        nodesExplored = 0;
        int bestScore = Integer.MIN_VALUE;
        Piece bestMove = null;
        for(Piece move : PossibleMoves){
            //create new node
            Point newPoint = new Point(move.getRow(), move.getCol());
            Board newNode = BoardHelper.getNewBoardAfterMove(board,newPoint, player);
            //recursive call
            int childScore = MMAB(newNode,depth-1,false,Integer.MIN_VALUE,Integer.MAX_VALUE, player);
            if(childScore > bestScore) {
                bestScore = childScore;
                bestMove = move;
            }
        }
        System.out.println("Nodes Explored : " + nodesExplored);
        return bestMove;
    }

    private static int MMAB(Board node, int depth, boolean max, int alpha, int beta, int player) {
        nodesExplored++;
        //if terminal reached or depth limit reached evaluate
        if(depth == 0 || BoardHelper.isGameFinished(node.getBoard(),player)){
            //BoardPrinter bpe = new BoardPrinter(node,"Depth : " + depth);
            return node.getScore(player);
        }
        int oplayer = (player==1) ? 2 : 1;
        //if no moves available then forfeit turn
        if((max && !BoardHelper.isGameFinished(node.getBoard(),player) || (!max && !BoardHelper.isGameFinished(node.getBoard(),oplayer)))){
            //System.out.println("Forfeit State Reached !");
            return MMAB(node,depth-1,!max,Integer.MIN_VALUE,Integer.MAX_VALUE, player);
        }
        int score;
        if(max){
            //maximizing
            score = Integer.MIN_VALUE;
            for(Piece move : BoardHelper.getPointMove(node.getBoard(), player)){ //my turn
                //create new nodeb
                Board newNode = BoardHelper.getNewBoardAfterMove(node,new Point(move.getRow(), move.getCol()),player);
                //recursive call
                int childScore = MMAB(newNode,depth-1,false,Integer.MIN_VALUE,Integer.MAX_VALUE, player);
                if(childScore > score) score = childScore;
                //update alpha
                if(score > alpha) alpha = score;
                if(beta <= alpha) break; //Beta Cutoff
            }
        }else{
            //minimizing
            score = Integer.MAX_VALUE;
            for(Piece move : BoardHelper.getPointMove(node.getBoard(), player)){//opponent turn
                //create new node
                Board newNode = BoardHelper.getNewBoardAfterMove(node,new Point(move.getRow(), move.getCol()),oplayer);
                //recursive call
                int childScore = MMAB(newNode,depth-1,false,Integer.MIN_VALUE,Integer.MAX_VALUE, oplayer);
                if(childScore < score) score = childScore;
                //update beta
                if(score < beta) beta = score;
                if(beta <= alpha) break; //Alpha Cutoff
            }
        }
        return score;
    }
}
