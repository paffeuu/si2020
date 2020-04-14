package si.lista3.engine;

import si.lista3.engine.model.Field;

public class Connect4Engine {
    private final static int COLS = 6;
    private final static int ROWS = 7;

    private Field[][] stage;

    public Connect4Engine() {
        stage = new Field[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                stage[i][j] = new Field(i, j);
            }
        }
    }

    public void printStage() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                switch (stage[i][j].getState()) {
                    case EMPTY:
                        sb.append("_");
                        break;
                    case PLAYER_1:
                        sb.append("X");
                        break;
                    case PLAYER_2:
                        sb.append("O");
                        break;
                }
                sb.append("  ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

}
