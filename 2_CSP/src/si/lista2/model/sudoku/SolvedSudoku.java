package si.lista2.model.sudoku;

import java.util.ArrayList;
import java.util.List;

public class SolvedSudoku extends Sudoku {
    public SolvedSudoku(Sudoku sudoku, List<Integer> solutionVector) {
        super(sudoku.id, sudoku.difficulty);
        this.stage = new Integer[81];
        this.gaps = new ArrayList<>(sudoku.gaps);
        int pointer = 0;
        for (int i = 0; i < this.stage.length; i++) {
            if (sudoku.stage[i] != null) {
                this.stage[i] = sudoku.stage[i];
            } else {
                this.stage[i] = solutionVector.get(pointer++);
            }
        }
    }
}
