package AI;

import model.Board;
import model.Piece;

import java.awt.*;

public class MinimaxAi {

    public static Piece play(Board board) {
        Board newboard = board;
        return Minimax.solve(newboard,7,2);
    }
}
