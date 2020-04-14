package si.lista3.engine;

import si.lista3.engine.model.Field;
import si.lista3.exception.FieldNotEmptyException;
import si.lista3.exception.FieldOutOfStageException;
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
        if (x >= COLS || y >= ROWS) {
            throw new FieldOutOfStageException(
                    "Requested move refers to field[x = " + x + ", y = " + y + "] which does not belong to stage.");
        }
        if (stage[y][x].getState() != Field.State.EMPTY) {
            throw new FieldNotEmptyException(stage[y][x] + " is not empty!");
        }
        if (player == 1) {
            stage[y][x].setState(Field.State.PLAYER_X);
        } else if (player == 2) {
            stage[y][x].setState(Field.State.PLAYER_O);
        }
        if (!checkIfGameIsFinished(x, y, player)) {
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

    private boolean checkIfGameIsFinished(int x, int y, int player) {
        boolean allFieldsOccupied =
                Arrays.stream(stage)
                .flatMap(Arrays::stream)
                .allMatch(Field::isOccupied);
        if (allFieldsOccupied) {
            finishGame(0);
            return true;
        }
        if (checkIfPlayerWon(x, y, player)) {
            finishGame(player);
            return true;
        }
        return false;
    }

    private boolean checkIfPlayerWon(int x, int y, int player) {
        Field.State desirableState = (player == 1) ? Field.State.PLAYER_X : Field.State.PLAYER_O;
        // horizontal
        int matchesX = 0;
        for (int k = 0; k < 4; k++) {
            int startX = x - k;
            if (startX < 0) {
                continue;
            }
            matchesX = 0;
            for (int l = 0; l < 4; l++) {
                if (startX + l >= COLS) {
                    break;
                }
                if (stage[y][startX + l].getState() == desirableState) {
                    matchesX++;
                } else {
                    break;
                }
            }
            if (matchesX == 4) {
                return true;
            }
        }

        // horizontal
        int matchesY = 0;
        for (int k = 0; k < 4; k++) {
            int startY = y - k;
            if (startY < 0) {
                continue;
            }
            matchesY = 0;
            for (int l = 0; l < 4; l++) {
                if (startY + l >= ROWS) {
                    break;
                }
                if (stage[startY + l][x].getState() == desirableState) {
                    matchesY++;
                } else {
                    break;
                }
            }
            if (matchesY == 4) {
                return true;
            }
        }
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

    public boolean isFinished() {
        return finished;
    }

    public int getResult() {
        return result;
    }
}
