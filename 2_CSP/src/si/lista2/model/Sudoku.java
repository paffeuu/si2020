package si.lista2.model;

import si.lista2.exception.FieldSetException;
import si.lista2.exception.SudokuParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sudoku {
    private int id;
    private int difficulty;
    private Integer[] stage;
    private List<Integer> gaps;

    private int counter = 0;

    public Sudoku(int id, double difficulty) {
        this.stage = new Integer[81];
        this.gaps = new ArrayList<>();
        this.id = id;
        this.difficulty = (int) difficulty;
    }

    public void addNewField(Integer value) {
        if (counter >= 81) {
            throw new SudokuParseException("Counter >= 81.");
        }
        if (value == null) {
            gaps.add(counter++);
        } else {
            stage[counter++] = value;
        }
    }

    public Integer getField(int x, int y) {
        return stage[x + y * 9];
    }

    public void setField(int x, int y, int value) {
        if (getField(x, y) == null) {
            stage[x + y * 9] = value;
        } else {
            throw new FieldSetException("Field [" + x + ", " + y + "] set.");
        }
    }

    public List<Integer> getGaps() {
        return gaps;
    }

    private String getPrintableVisualization() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < 9; y++ ) {
            if (y == 3 || y == 6) {
                sb.append("\n");
            }
            for (int x = 0; x < 9; x++) {
                if (x == 3 || x == 6) {
                    sb.append("\t");
                }
                sb.append(getField(x, y) != null ? getField(x, y) : ".");
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Id: " + id + "\n" +
                "Difficulty: " + difficulty +"\n" +
                getPrintableVisualization();
    }
}
