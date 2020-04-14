package si.lista3.engine;

import si.lista3.engine.model.Field;
import si.lista3.exception.ColumnFullException;
import si.lista3.exception.ColumnOutOfStageException;
import si.lista3.exception.GameFinishedException;
import si.lista3.exception.UnauthorizedMoveException;

import java.util.Arrays;

public class Connect4Engine {
    public final static int COLS = 7;
    public final static int ROWS = 6;

    private int nextMovePlayer = 1;
    private boolean finished = false;
    private int result = -1;

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

    public int evaluateStageForPlayer(int player) {
        String desiredStringThree[] = new String[4];
        String desiredStringTwo[] = new String[6];
        if (player == 1) {
            desiredStringThree[0] = " XXX";
            desiredStringThree[1] = "X XX";
            desiredStringThree[2] = "XX X";
            desiredStringThree[3] = "XXX ";

            desiredStringTwo[0] = "XX  ";
            desiredStringTwo[1] = "X X ";
            desiredStringTwo[2] = "X  X";
            desiredStringTwo[3] = " XX ";
            desiredStringTwo[4] = " X X";
            desiredStringTwo[5] = "  XX";
        } else if (player == 2) {
            desiredStringThree[0] = " OOO";
            desiredStringThree[1] = "O OO";
            desiredStringThree[2] = "OO O";
            desiredStringThree[3] = "OOO ";

            desiredStringTwo[0] = "OO  ";
            desiredStringTwo[1] = "O O ";
            desiredStringTwo[2] = "O  O";
            desiredStringTwo[3] = " OO ";
            desiredStringTwo[4] = " O O";
            desiredStringTwo[5] = "  OO";
        }

        int horizontalThrees = 0;
        int horizontalTwos = 0;
        // horizontal
        for (int i = 0; i < ROWS; i++) {
            char[] rowCh = new char[COLS];
            for (int j = 0; j < COLS; j++) {
                switch (stage[i][j].getState()) {
                    case EMPTY:
                        rowCh[j] = ' ';
                        break;
                    case PLAYER_X:
                        rowCh[j] = 'X';
                        break;
                    case PLAYER_O:
                        rowCh[j] = 'O';
                        break;
                }
            }
            String row = new String(rowCh);
            for (String desiredString : desiredStringThree) {
                if (row.contains(desiredString)) {
                    horizontalThrees++;
                    break;
                }
            }
            for (String desiredString : desiredStringTwo) {
                if (row.contains(desiredString)) {
                    horizontalTwos++;
                    break;
                }
            }
        }

        int verticalThrees = 0;
        int verticalTwos = 0;
        // vertical
        for (int j = 0; j < COLS; j++) {
            char[] colCh = new char[COLS];
            for (int i = 0; i < ROWS; i++) {
                switch (stage[i][j].getState()) {
                    case EMPTY:
                        colCh[i] = ' ';
                        break;
                    case PLAYER_X:
                        colCh[i] = 'X';
                        break;
                    case PLAYER_O:
                        colCh[i] = 'O';
                        break;
                }
            }
            String col = new String(colCh);
            for (String desiredString : desiredStringThree) {
                if (col.contains(desiredString)) {
                    verticalThrees++;
                    break;
                }
            }
            for (String desiredString : desiredStringTwo) {
                if (col.contains(desiredString)) {
                    verticalTwos++;
                    break;
                }
            }
        }

        int diagonalSlashThrees = 0;
        int diagonalSlashTwos = 0;
        // diagonal slash /
        for (int j = -ROWS + 1; j < COLS; j++) {
            char[] diagCh = new char[ROWS];
            int startX = j;
            int startY = ROWS - 1;
            for (int i = 0; i < ROWS; i++) {
                int x = startX + i;
                int y = startY - i;
                if (x < 0 || x >= COLS || y < 0 || y >= ROWS) {
                    diagCh[i] = '-';
                    continue;
                }
                switch (stage[y][x].getState()) {
                    case EMPTY:
                        diagCh[i] = ' ';
                        break;
                    case PLAYER_X:
                        diagCh[i] = 'X';
                        break;
                    case PLAYER_O:
                        diagCh[i] = 'O';
                        break;
                }
            }
            String diagonal = new String(diagCh);
            for (String desiredString : desiredStringThree) {
                if (diagonal.contains(desiredString)) {
                    diagonalSlashThrees++;
                    break;
                }
            }
            for (String desiredString : desiredStringTwo) {
                if (diagonal.contains(desiredString)) {
                    diagonalSlashTwos++;
                    break;
                }
            }
        }

        int diagonalBackslashThrees = 0;
        int diagonalBackslashTwos = 0;
        // diagonal backslash \
        for (int j = 0; j < ROWS + COLS - 2; j++) {
            char[] diagCh = new char[ROWS + COLS - 2];
            int startX = j;
            int startY = 0;
            for (int i = 0; i < ROWS; i++) {
                int x = startX + i;
                int y = startY + i;
                if (x < 0 || x >= COLS || y < 0 || y >= ROWS) {
                    diagCh[i] = '-';
                    continue;
                }
                switch (stage[y][x].getState()) {
                    case EMPTY:
                        diagCh[i] = ' ';
                        break;
                    case PLAYER_X:
                        diagCh[i] = 'X';
                        break;
                    case PLAYER_O:
                        diagCh[i] = 'O';
                        break;
                }
            }
            String diagonal = new String(diagCh);
            for (String desiredString : desiredStringThree) {
                if (diagonal.contains(desiredString)) {
                    diagonalBackslashThrees++;
                    break;
                }
            }
            for (String desiredString : desiredStringTwo) {
                if (diagonal.contains(desiredString)) {
                    diagonalBackslashTwos++;
                    break;
                }
            }
        }

        int threes = horizontalThrees + verticalThrees + diagonalSlashThrees + diagonalBackslashThrees;
        int twos = horizontalTwos + verticalTwos + diagonalSlashTwos + diagonalBackslashTwos;
        int evaluation = threes * 9 + twos * 4;
        return evaluation;

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
}
