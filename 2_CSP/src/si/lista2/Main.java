package si.lista2;

import si.lista2.csp.sudoku.BacktrackingSudokuSolver;
import si.lista2.model.sudoku.Sudoku;
import si.lista2.utils.JolkaParser;
import si.lista2.utils.SudokuParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SudokuParser sudokuParser = new SudokuParser("data//Sudoku.csv");
        List<Sudoku> sudokus = sudokuParser.parseFileContent();
//
        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
        backtrackingSudokuSolver.solve(sudokus.get(0));
//        sudokus.stream()
//                .map(backtrackingSudokuSolver::solve)
//                .forEach(list -> {
//                    list.forEach(System.out::println);
//                });
//        JolkaParser jolkaParser = new JolkaParser(new String[] {"data//Jolka//puzzle0", "data//Jolka//words0"});
//        System.out.println(jolkaParser.parseFileContent());
    }
}
