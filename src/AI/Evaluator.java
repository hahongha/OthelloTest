package AI;

import model.Board;

public interface Evaluator {

    int eval(Board board, int player);

}
