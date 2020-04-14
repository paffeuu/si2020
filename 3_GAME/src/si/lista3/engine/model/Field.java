package si.lista3.engine.model;

public class Field {
    private final int col;
    private final int row;
    private State state;

    public Field (int row, int col) {
        this.row = row;
        this.col = col;
        this.state = State.EMPTY;
    }

    public Field clone() {
        Field clonedField = new Field(row, col);
        clonedField.setState(state);
        return clonedField;
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

    public boolean isOccupied() {
        return state != State.EMPTY;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Field{" +
                "col=" + col +
                ", row=" + row +
                ", state=" + state +
                '}';
    }

    public enum State {
        EMPTY, PLAYER_X, PLAYER_O
    }

}
