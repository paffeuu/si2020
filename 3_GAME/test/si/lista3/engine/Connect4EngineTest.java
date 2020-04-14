package si.lista3.engine;

import org.junit.Before;
import org.junit.Test;
import si.lista3.exception.FieldNotEmptyException;
import si.lista3.exception.GameFinishedException;
import si.lista3.exception.UnauthorizedMoveException;

import static org.junit.Assert.assertTrue;

public class Connect4EngineTest {
    Connect4Engine engine;

    @Before
    public void setUp() throws Exception {
        engine = new Connect4Engine();
    }

    @Test
    public void nextMove() {
        engine.nextMove(0,0, 1);
        engine.nextMove(1,0, 2);
        try {
            engine.nextMove(0,0,1);
        } catch (Exception e) {
            assertTrue(e instanceof FieldNotEmptyException);
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
}