package si.lista3.algorithm;

import org.junit.Test;
import si.lista3.engine.Connect4Engine;

import static org.junit.Assert.*;

public class MinMaxAlgorithmTest {
    Connect4Engine engine;
    MinMaxAlgorithm minMaxAlgorithm;

    @Test
    public void getBestMove() {
        engine = new Connect4Engine();
        engine.nextMove(4,1);
        engine.nextMove(3,2);
//        engine.nextMove(5,1);
//        engine.nextMove(4,2);
        engine.nextMove(5,1);
        engine.nextMove(5,2);
//        engine.nextMove(6,1);
//        engine.nextMove(0,2);
        engine.printStage();

        minMaxAlgorithm = new MinMaxAlgorithm();
//        minMaxAlgorithm.setDepth(5);
        for (int i = 0; i < 100; i++) {
            int player = 1 + i % 2;
            int nextMove = minMaxAlgorithm.getBestMove(engine, player);
            engine.nextMove(nextMove, player);
            engine.printStage();
            if (engine.isFinished()) {
                break;
            }
        }
//        System.out.println(nextMove);
    }
}