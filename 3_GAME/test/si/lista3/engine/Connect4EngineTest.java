package si.lista3.engine;

import org.junit.Test;
import si.lista3.exception.ColumnFullException;
import si.lista3.exception.ColumnOutOfStageException;
import si.lista3.exception.GameFinishedException;
import si.lista3.exception.UnauthorizedMoveException;

import static org.junit.Assert.*;

public class Connect4EngineTest {
    Connect4Engine engine;

    @Test
    public void nextMove() {
        engine = new Connect4Engine();

        engine.nextMove(0, 1);
        engine.nextMove(0, 2);
        engine.nextMove(0, 1);
        engine.nextMove(0, 2);
        engine.nextMove(0, 1);
        engine.nextMove(0, 2);
        try {
            engine.nextMove(0,1);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof ColumnFullException);
        }
        try {
            engine.nextMove(7,1);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof ColumnOutOfStageException);
        }
        try {
            engine.nextMove(1,2);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof UnauthorizedMoveException);
        }
        engine.nextMove(1, 1);
        engine.nextMove(1, 2);
        engine.nextMove(1, 1);
        engine.nextMove(1, 2);
        engine.nextMove(1, 1);
        engine.nextMove(1, 2);
        engine.nextMove(2, 1);
        engine.nextMove(2, 2);
        engine.nextMove(2, 1);
        engine.nextMove(2, 2);
        engine.nextMove(2, 1);
        engine.nextMove(2, 2);
        engine.nextMove(4, 1);
        engine.nextMove(3, 2);
        engine.nextMove(3, 1);
        engine.nextMove(3, 2);
        engine.nextMove(3, 1);
        engine.nextMove(3, 2);
        engine.nextMove(3, 1);
        engine.nextMove(4, 2);
        engine.nextMove(4, 1);
        engine.nextMove(4, 2);
        engine.nextMove(4, 1);
        engine.nextMove(4, 2);
        engine.nextMove(5, 1);
        engine.nextMove(5, 2);
        engine.nextMove(5, 1);
        engine.nextMove(5, 2);
        engine.nextMove(5, 1);
        engine.nextMove(5, 2);
        engine.nextMove(6, 1);
        engine.nextMove(6, 2);
        engine.nextMove(6, 1);
        engine.nextMove(6, 2);
        engine.nextMove(6, 1);
        engine.nextMove(6, 2);
        try {
            engine.nextMove(0,1);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof GameFinishedException);
        }
        engine.printStage();
    }

    @Test
    public void checkIfPlayerWonHorizontal() {
        engine = new Connect4Engine();
        engine.nextMove(0, 1);
        engine.nextMove(0, 2);
        engine.nextMove(1, 1);
        engine.nextMove(1, 2);
        engine.nextMove(2, 1);
        engine.nextMove(2, 2);
        engine.nextMove(3, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(2, 1);
        engine.nextMove(2, 2);
        engine.nextMove(0, 1);
        engine.nextMove(0, 2);
        engine.nextMove(3, 1);
        engine.nextMove(3, 2);
        engine.nextMove(1, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(6, 1);
        engine.nextMove(6, 2);
        engine.nextMove(5, 1);
        engine.nextMove(5, 2);
        engine.nextMove(3, 1);
        engine.nextMove(3, 2);
        engine.nextMove(4, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(0, 1);
        engine.nextMove(0, 2);
        engine.nextMove(2, 1);
        engine.nextMove(2, 2);
        engine.nextMove(4, 1);
        engine.nextMove(3, 2);
        engine.nextMove(3, 1);
        engine.nextMove(1, 2);
        engine.nextMove(1, 1);
        engine.nextMove(0, 2);
        engine.nextMove(5, 1);
        engine.nextMove(1, 2);
        engine.nextMove(5, 1);
        engine.nextMove(2, 2);
        engine.nextMove(5, 1);
        engine.nextMove(3, 2);
        engine.printStage();
        assertEquals(2, engine.getResult());
    }

    @Test
    public void checkIfPlayerWonVertical() {
        engine = new Connect4Engine();
        engine.nextMove(0,1);
        engine.nextMove(1,2);
        engine.nextMove(0,1);
        engine.nextMove(1,2);
        engine.nextMove(0,1);
        engine.nextMove(1,2);
        engine.nextMove(0,1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(0,1);
        engine.nextMove(1,2);
        engine.nextMove(0,1);
        engine.nextMove(1,2);
        engine.nextMove(1,1);
        engine.nextMove(2,2);
        engine.nextMove(1,1);
        engine.nextMove(2,2);
        engine.nextMove(1,1);
        engine.nextMove(2,2);
        engine.nextMove(1,1);
        engine.printStage();
        assertEquals(1, engine.getResult());
    }

    @Test
    public void checkIfPlayerWonDiagonalBackslash() {
        engine = new Connect4Engine();
        engine.nextMove(2,1);
        engine.nextMove(3,2);
        engine.nextMove(1,1);
        engine.nextMove(2,2);
        engine.nextMove(1,1);
        engine.nextMove(1,2);
        engine.nextMove(0,1);
        engine.nextMove(5,2);
        engine.nextMove(0,1);
        engine.nextMove(5,2);
        engine.nextMove(0,1);
        engine.nextMove(0,2);
        engine.printStage();
        assertEquals(2, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(5,1);
        engine.nextMove(6,2);
        engine.nextMove(4,1);
        engine.nextMove(5,2);
        engine.nextMove(4,1);
        engine.nextMove(4,2);
        engine.nextMove(3,1);
        engine.nextMove(0,2);
        engine.nextMove(3,1);
        engine.nextMove(0,2);
        engine.nextMove(3,1);
        engine.nextMove(3,2);
        engine.printStage();
        assertEquals(2, engine.getResult());

    }

    @Test
    public void checkIfPlayerWonDiagonalSlash() {
        engine = new Connect4Engine();
        engine.nextMove(1,1);
        engine.nextMove(0,2);
        engine.nextMove(2,1);
        engine.nextMove(1,2);
        engine.nextMove(2,1);
        engine.nextMove(2,2);
        engine.nextMove(3,1);
        engine.nextMove(5,2);
        engine.nextMove(3,1);
        engine.nextMove(5,2);
        engine.nextMove(3,1);
        engine.nextMove(3,2);
        engine.printStage();
        assertEquals(2, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(4,1);
        engine.nextMove(3,2);
        engine.nextMove(5,1);
        engine.nextMove(4,2);
        engine.nextMove(5,1);
        engine.nextMove(5,2);
        engine.nextMove(6,1);
        engine.nextMove(0,2);
        engine.nextMove(6,1);
        engine.nextMove(0,2);
        engine.nextMove(6,1);
        engine.nextMove(6,2);
        engine.printStage();
        assertEquals(2, engine.getResult());

    }

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
        assertEquals(4, engine.evaluateStageForPlayer(1));
        assertEquals(13, engine.evaluateStageForPlayer(2));
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
        assertEquals(12, engine.evaluateStageForPlayer(1));
        assertEquals(17, engine.evaluateStageForPlayer(2));
    }



}