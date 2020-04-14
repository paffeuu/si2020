package si.lista3.engine;

import si.lista3.engine.model.Field;
import si.lista3.exception.FieldNotEmptyException;
import si.lista3.exception.GameFinishedException;
import si.lista3.exception.UnauthorizedMoveException;

import java.util.Arrays;

public class Connect4Engine {
    public final static int COLS = 6;
    public final static int ROWS = 7;

    private int nextMovePlayer = 1;
    private boolean finished = false;
    private int result = -1;

    private Field[][] stage;

    public Connect4Engine() {
        stage = new Field[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                stage[i][j] = new Field(i, j);
            }
        }
    }

    public void nextMove(int x, int y, int player) {
        if (finished) {
            throw new GameFinishedException();
        }
        if (player != nextMovePlayer) {
            throw new UnauthorizedMoveException(
                    "New move of player " + player + " registered! Expected move of player "+ nextMovePlayer);
        }
        if (stage[y][x].getState() != Field.State.EMPTY) {
            throw new FieldNotEmptyException(stage[y][x] + " is not empty!");
        }
        if (player == 1) {
            stage[y][x].setState(Field.State.PLAYER_X);
        } else if (player == 2) {
            stage[y][x].setState(Field.State.PLAYER_O);
        }
        if (!checkIfGameIsFinished()) {
            nextMovePlayer = nextMovePlayer != 1 ? 1 : 2;
        }
    }

    private void finishGame(int winner) {
        finished = true;
        nextMovePlayer = 0;
        result = winner;
        printResult();
        //TODO: finish the game
     }

    private boolean checkIfGameIsFinished() {
        boolean allFieldsOccupied =
                Arrays.stream(stage)
                .flatMap(Arrays::stream)
                .allMatch(Field::isOccupied);
        if (allFieldsOccupied) {
            finishGame(0);
            return true;
        }
        //TODO: another conditions
        return false;
    }

    public void printStage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Status: ");
        switch (nextMovePlayer) {
            case 0:
                sb.append("Game finished");
                break;
            case 1:
                sb.append("Next move - Player X");
                break;
            case 2:
                sb.append("Next move - PLayer O");
                break;
        }
        sb.append("\n");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                switch (stage[i][j].getState()) {
                    case EMPTY:
                        sb.append("_");
                        break;
                    case PLAYER_X:
                        sb.append("X");
                        break;
                    case PLAYER_O:
                        sb.append("O");
                        break;
                }
                sb.append("  ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    private void printResult() {
        StringBuilder sb = new StringBuilder();
        sb.append("====================");
        sb.append("\n");
        sb.append("Game finished!");
        sb.append("\n");
        sb.append("Result: ");
        switch (result) {
            case 0:
                sb.append("Draw");
                break;
            case 1:
                sb.append("Player X won!");
                break;
            case 2:
                sb.append("Player O won!");
                break;
        }
        sb.append("\n");
        sb.append("====================");
        System.out.println(sb.toString());
    }

}
