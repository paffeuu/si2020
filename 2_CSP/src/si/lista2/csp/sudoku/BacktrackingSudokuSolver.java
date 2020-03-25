package si.lista2.csp.sudoku;

import si.lista2.model.sudoku.SolvedSudoku;
import si.lista2.model.sudoku.Sudoku;

import java.util.ArrayList;
import java.util.List;

public class BacktrackingSudokuSolver {
    public List<SolvedSudoku> solve(Sudoku sudoku) {
        List<SolvedSudoku> solvedSudokus = new ArrayList<>();
        List<Integer> gaps = sudoku.getGaps();
        int lastCheckedValue = 0;
        int z = 0;
        for (int i = 0; i < gaps.size(); i++) {
            for (int j = lastCheckedValue + 1; j < 10; j++) {
                sudoku.setField(i, j);
                if (sudoku.checkConstraints(i)) {
                    lastCheckedValue = 0;
                    if (i == gaps.size() - 1) {
                        List<Integer> solutionVector = new ArrayList<>();
                        for (int k = 0; k < gaps.size(); k++) {
                            solutionVector.add(sudoku.getField(k));
                        }
                        SolvedSudoku solvedSudoku = new SolvedSudoku(sudoku, solutionVector);
                        solvedSudokus.add(solvedSudoku);
                        sudoku.unsetField(gaps.size() - 1);
                    } else {
                        break;
                    }
                } else {
                    sudoku.unsetField(i);
                    if (j == 9) {
                        lastCheckedValue = j;
                        while (lastCheckedValue == 9 && --i >= 0) {
                            lastCheckedValue = sudoku.getField(i);
                            sudoku.unsetField(i);
                        }
                        i--;
                    }
                }
            }
            if (i < 0) {
                break;
            }
        }
        return solvedSudokus;
    }
}
