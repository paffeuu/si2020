package si.lista3.engine;

import org.junit.Before;
import org.junit.Test;
import si.lista3.exception.FieldNotEmptyException;
import si.lista3.exception.FieldOutOfStageException;
import si.lista3.exception.GameFinishedException;
import si.lista3.exception.UnauthorizedMoveException;

import static org.junit.Assert.*;

public class Connect4EngineTest {
    Connect4Engine engine;

    @Test
    public void nextMove() {
        engine = new Connect4Engine();

        engine.nextMove(0,0, 1);
        engine.nextMove(1,0, 2);
        try {
            engine.nextMove(0,0,1);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof FieldNotEmptyException);
        }
        try {
            engine.nextMove(3,6,1);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof FieldOutOfStageException);
        }
        try {
            engine.nextMove(7,0,1);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof FieldOutOfStageException);
        }
        try {
            engine.nextMove(3,3,2);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof UnauthorizedMoveException);
        }
        int player = 1;
        engine.nextMove(2,0, 1);
        engine.nextMove(3,0, 2);
        engine.nextMove(4,0, 1);
        engine.nextMove(5,0, 2);
        engine.nextMove(6,0, 1);
        engine.nextMove(0,1, 2);
        engine.nextMove(1,1, 1);
        engine.nextMove(2,1, 2);
        engine.nextMove(3,1, 1);
        engine.nextMove(4,1, 2);
        engine.nextMove(5,1, 1);
        engine.nextMove(6,1, 2);
        engine.nextMove(0,4, 1);
        engine.nextMove(1,4, 2);
        engine.nextMove(2,4, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(4,4, 1);
        engine.nextMove(5,4, 2);
        engine.nextMove(6,4, 1);
        engine.nextMove(0,3, 2);
        engine.nextMove(1,3, 1);
        engine.nextMove(2,3, 2);
        engine.nextMove(3,3, 1);
        engine.nextMove(4,3, 2);
        engine.nextMove(5,3, 1);
        engine.nextMove(6,3, 2);
        engine.nextMove(1,2, 1);
        engine.nextMove(0,2, 2);
        engine.nextMove(3,2, 1);
        engine.nextMove(2,2, 2);
        engine.nextMove(5,2, 1);
        engine.nextMove(4,2, 2);
        engine.nextMove(6,2, 1);
        engine.nextMove(0,5, 2);
        engine.nextMove(1,5, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(3,5, 1);
        engine.nextMove(4,5, 2);
        engine.nextMove(5,5, 1);
        engine.nextMove(6,5, 2);

        try {
            engine.nextMove(3,4,1);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof GameFinishedException);
        }
        engine.printStage();
    }

    @Test
    public void checkIfPlayerWonHorizontal() {
        engine = new Connect4Engine();
        engine.nextMove(0,0, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(1,0, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(2,0, 1);
        engine.nextMove(4,1, 2);
        engine.nextMove(3,0, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(3,0, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(1,0, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(0,0, 1);
        engine.nextMove(4,1, 2);
        engine.nextMove(2,0, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(3,1, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(4,1, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(5,1, 1);
        engine.nextMove(4,2, 2);
        engine.nextMove(2,1, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(0,2, 1);
        engine.nextMove(4,3, 2);
        engine.nextMove(1,5, 1);
        engine.nextMove(5,3, 2);
        engine.nextMove(2,4, 1);
        engine.nextMove(2,3, 2);
        engine.nextMove(3,1, 1);
        engine.nextMove(3,3, 2);
        engine.printStage();
        assertEquals(2, engine.getResult());

    }

    @Test
    public void checkIfPlayerWonVertical() {
        engine = new Connect4Engine();
        engine.nextMove(0,0, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(0,1, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(0,2, 1);
        engine.nextMove(4,1, 2);
        engine.nextMove(0,3, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(0,3, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(0,1, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(0,0, 1);
        engine.nextMove(4,1, 2);
        engine.nextMove(0,2, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(1,5, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(1,3, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(1,4, 1);
        engine.nextMove(4,2, 2);
        engine.nextMove(1,2, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(0,2, 1);
        engine.nextMove(3,3, 2);
        engine.nextMove(1,5, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(2,4, 1);
        engine.nextMove(3,5, 2);
        engine.nextMove(3,1, 1);
        engine.nextMove(3,2, 2);
        engine.printStage();
        assertEquals(2, engine.getResult());

    }

    @Test
    public void checkIfPlayerWonDiagonalBackslash() {
        engine = new Connect4Engine();
        engine.nextMove(0,0, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(1,1, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(2,2, 1);
        engine.nextMove(4,1, 2);
        engine.nextMove(3,3, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(3,3, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(1,1, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(0,0, 1);
        engine.nextMove(4,1, 2);
        engine.nextMove(2,2, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(1,2, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(4,5, 1);
        engine.nextMove(3,5, 2);
        engine.nextMove(2,3, 1);
        engine.nextMove(4,2, 2);
        engine.nextMove(3,4, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(0,2, 1);
        engine.nextMove(4,3, 2);
        engine.nextMove(1,5, 1);
        engine.nextMove(6,5, 2);
        engine.nextMove(2,4, 1);
        engine.nextMove(3,2, 2);
        engine.nextMove(3,1, 1);
        engine.nextMove(5,4, 2);
        engine.printStage();
        assertEquals(2, engine.getResult());

    }

    @Test
    public void checkIfPlayerWonDiagonalSlash() {
        engine = new Connect4Engine();
        engine.nextMove(6,0, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(5,1, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(4,2, 1);
        engine.nextMove(4,1, 2);
        engine.nextMove(3,3, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(3,3, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(5,1, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(6,0, 1);
        engine.nextMove(4,1, 2);
        engine.nextMove(4,2, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(2,4, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(1,5, 1);
        engine.nextMove(3,5, 2);
        engine.nextMove(3,3, 1);
        engine.nextMove(4,1, 2);
        engine.nextMove(4,2, 1);
        engine.printStage();
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(0,2, 1);
        engine.nextMove(1,4, 2);
        engine.nextMove(1,5, 1);
        engine.nextMove(0,5, 2);
        engine.nextMove(2,4, 1);
        engine.nextMove(3,2, 2);
        engine.nextMove(3,1, 1);
        engine.nextMove(2,3, 2);
        engine.printStage();
        assertEquals(2, engine.getResult());

    }



}