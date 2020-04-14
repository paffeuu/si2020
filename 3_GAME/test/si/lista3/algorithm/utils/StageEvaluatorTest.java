package si.lista3.algorithm.utils;

import org.junit.Test;
import si.lista3.engine.Connect4Engine;

import static org.junit.Assert.*;

public class StageEvaluatorTest {
    Connect4Engine engine;
    StageEvaluator evaluator = new StageEvaluator();

    @Test
    public void evaluateStageForPlayerHorizontal() {
        engine = new Connect4Engine();
        engine.nextMove(0,1);
        engine.nextMove(0,2);
        engine.nextMove(1,1);
        engine.nextMove(1,2);
        engine.nextMove(3,1);
        engine.nextMove(3,2);
        engine.nextMove(4,1);
        engine.nextMove(2,2);
        engine.printStage();
        assertEquals(4, evaluator.evaluateStage(engine.getStage(), 1));
        assertEquals(13, evaluator.evaluateStage(engine.getStage(), 2));
    }


    @Test
    public void evaluateStageForPlayerMixed() {
        engine = new Connect4Engine();
        engine.nextMove(4,1);
        engine.nextMove(3,2);
        engine.nextMove(5,1);
        engine.nextMove(4,2);
        engine.nextMove(5,1);
        engine.nextMove(2,2);
        engine.nextMove(6,1);
        engine.nextMove(0,2);
        engine.nextMove(6,1);
        engine.nextMove(0,2);
        engine.printStage();
        assertEquals(12, evaluator.evaluateStage(engine.getStage(), 1));
        assertEquals(17, evaluator.evaluateStage(engine.getStage(), 2));
    }
}