package si.lista2.model.sudoku;

import si.lista2.exception.GapJustFilledException;
import si.lista2.exception.SudokuParseException;
import si.lista2.model.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sudoku implements Puzzle {
    protected int id;
    protected int difficulty;
    protected Integer[] stage;
    protected List<Integer> gaps;

    private int pointer = 0;

    public Sudoku(int id, double difficulty) {
        this.stage = new Integer[81];
        this.gaps = new ArrayList<>();
        this.id = id;
        this.difficulty = (int) difficulty;
    }

    public boolean checkConstraints(int gap, int value) {
        int x = gap % 9;
        int y = gap / 9;
        for (int i = 0; i < 9; i++) {
            // horizontal
            Integer valueHToCompare = stage[y * 9 + i];
            if (valueHToCompare != null) {
                if (valueHToCompare == value && i != x) {
                    return false;
                }
            }
            // vertical
            Integer valueVToCompare = stage[i * 9 + x];
            if (valueVToCompare != null) {
                if (valueVToCompare == value && i != y) {
                    return false;
                }
            }
        }
        // square
        int sqX = x % 3;
        int sqY = y % 3;
        int edgeX = x / 3;
        int edgeY = y / 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Integer valueSToCompare = stage[(edgeY * 3 + i) * 9 + (edgeX * 3 + j)];
                if (valueSToCompare != null) {
                    if (valueSToCompare == value && i != sqY && j != sqX) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void addNewField(Integer value) {
        if (pointer >= 81) {
            throw new SudokuParseException("Counter >= 81.");
        }
        if (value == null) {
            gaps.add(pointer++);
        } else {
            stage[pointer++] = value;
        }
    }

    public Integer getField(int x, int y) {
        return stage[x + y * 9];
    }

    public Integer getField(int gap) {
        return stage[gap];
    }


    public void setField(int gap, int value) {
        if (stage[gap] == null) {
            stage[gap] = value;
        } else {
            throw new GapJustFilledException("Field from gap [" + gap + "] set.");
        }
    }

    public int unsetField(int gap) {
        int value = stage[gap];
        stage[gap] = null;
        return value - 1;
    }

    public SolvedSudoku createSolution() {
        List<Integer> solutionVector = new ArrayList<>();
        getGaps().forEach(gap -> solutionVector.add(getField((int)gap)));
        return new SolvedSudoku(this, solutionVector);
    }

    public List getGaps() {
        return gaps;
    }

    @Override
    public List<Object> getDomain() {
        return IntStream.range(1, 10).boxed().collect(Collectors.toList());
    }

    @Override
    public boolean checkConstraints(Object gap, Object value) {
        return checkConstraints((int)gap, (int)value);
    }

    @Override
    public void fillGap(Object gap, Object value) {
        setField((int)gap, (int)value);
    }

    @Override
    public int releaseGap(Object gap) {
        return unsetField((int)gap);
    }

    public String getId() {
        return String.valueOf(id);
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
        return "Sudoku\nId: " + id + "\n" +
                "Difficulty: " + difficulty +"\n" +
                getPrintableVisualization();
    }
}
