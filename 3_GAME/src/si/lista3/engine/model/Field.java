package si.lista3.engine.model;

public class Field {
    private int col;
    private int row;
    private State state;

    public Field (int row, int col) {
        this.row = row;
        this.col = col;
        this.state = State.EMPTY;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public State getState() {
        return state;
    }

    public enum State {
        EMPTY, PLAYER_1, PLAYER_2
    }

}
