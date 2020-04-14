package si.lista3.algorithm.utils;

import si.lista3.engine.model.Field;

import static si.lista3.engine.Connect4Engine.COLS;
import static si.lista3.engine.Connect4Engine.ROWS;

public class StageEvaluator {

    public int evaluateStage(Field[][] stage, int player) {
        return evaluateStageForPlayer(stage, player) - evaluateStageForPlayer(stage, player == 1 ? 2 : 1);
    }


    public int evaluateStageForPlayer(Field[][] stage, int player) {
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
            boolean threeFoundInThisRow = false;
            for (String desiredString : desiredStringThree) {
                if (row.contains(desiredString)) {
                    horizontalThrees++;
                    threeFoundInThisRow = true;
                    break;
                }
            }
            if (!threeFoundInThisRow) {
                for (String desiredString : desiredStringTwo) {
                    if (row.contains(desiredString)) {
                        horizontalTwos++;
                        break;
                    }
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
            boolean threeFoundInThisRow = false;
            for (String desiredString : desiredStringThree) {
                if (col.contains(desiredString)) {
                    verticalThrees++;
                    threeFoundInThisRow = true;
                    break;
                }
            }
            if (!threeFoundInThisRow) {
                for (String desiredString : desiredStringTwo) {
                    if (col.contains(desiredString)) {
                        verticalTwos++;
                        break;
                    }
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
            boolean threeFoundInThisRow = false;
            for (String desiredString : desiredStringThree) {
                if (diagonal.contains(desiredString)) {
                    diagonalSlashThrees++;
                    threeFoundInThisRow = true;
                    break;
                }
            }
            if (!threeFoundInThisRow) {
                for (String desiredString : desiredStringTwo) {
                    if (diagonal.contains(desiredString)) {
                        diagonalSlashTwos++;
                        break;
                    }
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
            boolean threeFoundInThisRow = false;
            for (String desiredString : desiredStringThree) {
                if (diagonal.contains(desiredString)) {
                    diagonalBackslashThrees++;
                    threeFoundInThisRow = true;
                    break;
                }
            }
            if (!threeFoundInThisRow) {
                for (String desiredString : desiredStringTwo) {
                    if (diagonal.contains(desiredString)) {
                        diagonalBackslashTwos++;
                        break;
                    }
                }
            }
        }

        int threes = horizontalThrees + verticalThrees + diagonalSlashThrees + diagonalBackslashThrees;
        int twos = horizontalTwos + verticalTwos + diagonalSlashTwos + diagonalBackslashTwos;
        int evaluation = threes * 9 + twos * 4;
        return evaluation;

    }

}
