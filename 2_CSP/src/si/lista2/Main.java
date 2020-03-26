package si.lista2;

import si.lista2.csp.sudoku.BacktrackingSolver;
import si.lista2.model.sudoku.Sudoku;
import si.lista2.utils.SudokuParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SudokuParser sudokuParser = new SudokuParser("data//Sudoku.csv");
        List<Sudoku> sudokus = sudokuParser.parseFileContent();
        BacktrackingSolver backtrackingSolver = new BacktrackingSolver();
//        System.out.println(backtrackingSolver.solve(sudokus.get(0)));
        sudokus.stream()
                .map(backtrackingSolver::solve)
                .forEach(list -> {
                    list.forEach(System.out::println);
                });

//        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
//        backtrackingSudokuSolver.solve(sudokus.get(0));
//        sudokus.stream()
//                .map(backtrackingSudokuSolver::solve)
//                .forEach(list -> {
//                    list.forEach(System.out::println);
//                });
//        JolkaParser jolkaParser = new JolkaParser(new String[] {"data//Jolka//puzzle1", "data//Jolka//words1"});
//        Jolka jolka = jolkaParser.parseFileContent();
    }
}
