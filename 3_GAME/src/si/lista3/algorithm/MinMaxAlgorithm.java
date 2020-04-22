package si.lista3.algorithm;

import si.lista3.algorithm.utils.StageEvaluator;
import si.lista3.engine.Connect4Engine;

import java.util.List;

public class MinMaxAlgorithm implements GameAlgorithm {
    private StageEvaluator evaluator;
    private int depth;

    public MinMaxAlgorithm() {
        evaluator = new StageEvaluator();
        depth = 5;
    }

    public int getBestMove(Connect4Engine engine, int player) {

        List<Integer> cols = engine.getNotFullColumns();
        int maxEval = Integer.MIN_VALUE;
        int maxCol = -1;
        for (int col : cols) {
            Connect4Engine clonedEngine = engine.clone();
            clonedEngine.nextMove(col, player);
            int eval = minMax(clonedEngine,depth, player == 1 ? 2 : 1,false);
            if (eval > maxEval) {
                maxEval = eval;
                maxCol = col;
            }
        }
        return maxCol;
    }

    private int minMax(Connect4Engine engine, int depth, int player, boolean max) {
        if (depth == 0) {
            return evaluator.evaluateStage(engine, player);
        }

        if (max) {
            int maxEval = Integer.MIN_VALUE;
            for (int col : engine.getNotFullColumns()) {
                Connect4Engine newEngine = engine.clone();
                if (!newEngine.isFinished()) {
                    newEngine.nextMove(col, player);
                    int eval = minMax(newEngine, depth - 1, player == 1 ? 2 : 1, false);
                    if (maxEval < eval) {
                        maxEval = eval;
                    }
                } else {
                    return evaluator.evaluateStage(engine, player);
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int col : engine.getNotFullColumns()) {
                Connect4Engine newEngine = engine.clone();
                if (!newEngine.isFinished()) {
                    newEngine.nextMove(col, player);
                    int eval = minMax(newEngine, depth - 1, player == 1 ? 2 : 1, true);
                    if (minEval > eval) {
                        minEval = eval;
                    }
                }
            }
            return minEval;
        }
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }
}
