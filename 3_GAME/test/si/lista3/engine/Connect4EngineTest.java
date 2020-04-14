package si.lista3.engine;

import org.junit.Before;
import org.junit.Test;
import si.lista3.exception.FieldNotEmptyException;
import si.lista3.exception.FieldOutOfStageException;
import si.lista3.exception.GameFinishedException;
import si.lista3.exception.UnauthorizedMoveException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Connect4EngineTest {
    Connect4Engine engine;

    @Test
    public void nextMove() {
        engine = new Connect4Engine();

        engine.nextMove(0,0, 1);
        engine.nextMove(1,0, 2);
        try {
            engine.nextMove(0,0,1);
        } catch (Exception e) {
            assertTrue(e instanceof FieldNotEmptyException);
        }
        try {
            engine.nextMove(3,7,1);
        } catch (Exception e) {
            assertTrue(e instanceof FieldOutOfStageException);
        }
        try {
            engine.nextMove(6,0,1);
        } catch (Exception e) {
            assertTrue(e instanceof FieldOutOfStageException);
        }
        try {
            engine.nextMove(3,3,2);
        } catch (Exception e) {
            assertTrue(e instanceof UnauthorizedMoveException);
        }
        int player = 1;
        for (int i = 0; i < Connect4Engine.ROWS; i++) {
            int j = 0;
            if (i == 0) {
                j = 2;
            }
            for (; j < Connect4Engine.COLS; j++) {
                engine.nextMove(j, i, player);
                player = (player != 1) ? 1 : 2;
            }
        }
        try {
            engine.nextMove(3,4,1);
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
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(3,0, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(1,0, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(0,0, 1);
        engine.nextMove(4,1, 2);
        engine.nextMove(2,0, 1);
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(3,1, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(4,1, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(5,1, 1);
        engine.nextMove(4,2, 2);
        engine.nextMove(2,1, 1);
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
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(0,3, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(0,1, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(0,0, 1);
        engine.nextMove(4,1, 2);
        engine.nextMove(0,2, 1);
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(1,6, 1);
        engine.nextMove(2,5, 2);
        engine.nextMove(1,4, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(1,5, 1);
        engine.nextMove(4,2, 2);
        engine.nextMove(1,3, 1);
        assertEquals(1, engine.getResult());

        engine = new Connect4Engine();
        engine.nextMove(0,2, 1);
        engine.nextMove(3,4, 2);
        engine.nextMove(1,5, 1);
        engine.nextMove(3,5, 2);
        engine.nextMove(2,4, 1);
        engine.nextMove(3,6, 2);
        engine.nextMove(3,1, 1);
        engine.nextMove(3,3, 2);
        assertEquals(2, engine.getResult());

    }

}