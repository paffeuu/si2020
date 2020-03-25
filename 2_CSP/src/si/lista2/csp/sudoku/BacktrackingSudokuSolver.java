package si.lista2.csp.sudoku;

import si.lista2.model.sudoku.SolvedSudoku;
import si.lista2.model.sudoku.Sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class BacktrackingSudokuSolver {
    public List<SolvedSudoku> solve(Sudoku sudoku) {
        List<SolvedSudoku> solvedSudokus = new ArrayList<>();
        ListIterator<Integer> gapsIterator = sudoku.getGaps().listIterator();
        int[] domain = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        int lastCheckedValue = 0;
        Integer gap = gapsIterator.next();
        do {
            for (int nextValue : domain) {
                if (nextValue <= lastCheckedValue) {
                    continue;
                }
                lastCheckedValue = nextValue;
                if (sudoku.checkConstraints(gap, nextValue)) {
                    sudoku.setField(gap, nextValue);
                    if (gapsIterator.hasNext()) {
                        gap = gapsIterator.next();
                        lastCheckedValue = Integer.MIN_VALUE;
                        break;
                    } else {
                        List<Integer> solutionVector = new ArrayList<>();
                        sudoku.getGaps().forEach(gapp -> {
                            solutionVector.add(sudoku.getField(gapp));
                        });
                        SolvedSudoku solvedSudoku = new SolvedSudoku(sudoku, solutionVector);
                        solvedSudokus.add(solvedSudoku);
                        sudoku.unsetField(gap);
                    }
                }
            }
            if (lastCheckedValue == domain[domain.length - 1]) {
                gapsIterator.previous();
                while (lastCheckedValue == domain[domain.length - 1] && gapsIterator.hasPrevious()) {
                    gap = gapsIterator.previous();
                    lastCheckedValue = sudoku.getField(gap);
                    sudoku.unsetField(gap);
                }
                if (gapsIterator.hasPrevious()) {
                    gapsIterator.next();
                }
            } else {
                lastCheckedValue = Integer.MIN_VALUE;
            }
        } while (gapsIterator.hasPrevious());
        return solvedSudokus;
    }
}