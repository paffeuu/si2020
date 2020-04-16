package si.lista3.engine;

import si.lista3.engine.model.Field;
import si.lista3.exception.ColumnFullException;
import si.lista3.exception.ColumnOutOfStageException;
import si.lista3.exception.GameFinishedException;
import si.lista3.exception.UnauthorizedMoveException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Connect4Engine {
    public final static int COLS = 7;
    public final static int ROWS = 6;

    private int nextMovePlayer = 1;
    private boolean finished = false;
    private int result = -1;
    private boolean simulation;

    private int[] columnPointers;
    private Field[][] stage;

    public Connect4Engine() {
        columnPointers = new int[COLS];
        for (int i = 0; i < COLS; i++) {
            columnPointers[i] = ROWS - 1;
        }
        stage = new Field[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                stage[i][j] = new Field(i, j);
            }
        }
    }

    public void nextMove(int col, int player) {
        if (finished) {
            throw new GameFinishedException();
        }
        if (player != nextMovePlayer) {
            throw new UnauthorizedMoveException(
                    "New move of player " + player + " registered! Expected move of player "+ nextMovePlayer);
        }
        if (col >= COLS) {
            throw new ColumnOutOfStageException(
                    "Requested move refers to column[x = " + col + "] which does not belong to stage.");
        }
        int row = columnPointers[col];
        if (columnPointers[col] == -1) {
            throw new ColumnFullException("column[x = " + col + "] is full!");
        }
        if (player == 1) {
            stage[row][col].setState(Field.State.PLAYER_X);
        } else if (player == 2) {
            stage[row][col].setState(Field.State.PLAYER_O);
        }
        columnPointers[col]--;
        if (!checkIfGameIsFinished(col, row, player)) {
            nextMovePlayer = nextMovePlayer != 1 ? 1 : 2;
        }
        if (!simulation) {
            System.out.println(col + "enigne" + columnPointers[col]);
        }

    }

    private void finishGame(int winner) {
        finished = true;
        nextMovePlayer = 0;
        result = winner;
        if (!simulation) {
            printResult();
        }
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

        // vertical
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

        // diagonal like backslash \
        int matchesDBs = 0;
        for (int k = 0; k < 4; k++) {
            int startX = x - k;
            int startY = y - k;
            if (startX < 0 || startY < 0) {
                continue;
            }
            matchesDBs = 0;
            for (int l = 0; l < 4; l++) {
                if (startX + l >= COLS || startY + l >= ROWS) {
                    break;
                }
                if (stage[startY + l][startX + l].getState() == desirableState) {
                    matchesDBs++;
                } else {
                    break;
                }
            }
            if (matchesDBs == 4) {
                return true;
            }
        }

        // diagonal like slash /
        int matchesDS = 0;
        for (int k = 0; k < 4; k++) {
            int startX = x + k;
            int startY = y - k;
            if (startX >= COLS || startY < 0) {
                continue;
            }
            matchesDS = 0;
            for (int l = 0; l < 4; l++) {
                if (startX - l < 0 || startY + l >= ROWS) {
                    break;
                }
                if (stage[startY + l][startX - l].getState() == desirableState) {
                    matchesDS++;
                } else {
                    break;
                }
            }
            if (matchesDS == 4) {
                return true;
            }
        }
        return false;
    }

    public Connect4Engine clone()  {
        Connect4Engine engine = new Connect4Engine();
        engine.stage = cloneStage();
        engine.nextMovePlayer = nextMovePlayer;
        engine.columnPointers = Arrays.copyOf(columnPointers, columnPointers.length);
        engine.simulation = true;
        engine.finished = finished;
        return engine;
    }

        private Field[][] cloneStage() {
        Field[][] clonedStage = new Field[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                clonedStage[i][j] = stage[i][j].clone();
            }
        }
        return clonedStage;
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

    public Field[][] getStage() {
        return stage;
    }

    public int[] getColumnPointers() {
        return columnPointers;
    }

    public List<Integer> getNotFullColumns() {
        List<Integer> notFullColumns = new ArrayList<>();
        for (int i = 0; i < COLS; i++) {
            if (columnPointers[i] >= 0) {
                notFullColumns.add(i);
            }
        }
        return notFullColumns;
    }
}
