package si.lista2;

import si.lista2.csp.BacktrackingSolver;
import si.lista2.csp.ForwardCheckingSolver;
import si.lista2.model.jolka.Jolka;
import si.lista2.model.sudoku.Sudoku;
import si.lista2.utils.JolkaParser;
import si.lista2.utils.SudokuParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SudokuParser sudokuParser = new SudokuParser("data//Sudoku.csv");
        List<Sudoku> sudokus = sudokuParser.parseFileContent();
        Sudoku easySudoku = sudokus.get(0);

        BacktrackingSolver backtrackingSolver = new BacktrackingSolver();
        System.out.println(backtrackingSolver.solve(sudokus.get(0)));

        ForwardCheckingSolver forwardCheckingSolver = new ForwardCheckingSolver();
        System.out.println(forwardCheckingSolver.solve(easySudoku));

        easySudoku.useSortFromTheMostFullRowHeuristics();
        System.out.println(forwardCheckingSolver.solve(easySudoku));
//        sudokus.stream()
//                .map(forwardCheckingSolver::solve)
//                .forEach(list -> {
//                    list.forEach(System.out::println);
//                });


//        System.out.println(backtrackingSolver.solve(sudokus.get(0)));


//        BacktrackingSudokuSolver backtrackingSudokuSolver = new BacktrackingSudokuSolver();
//        backtrackingSudokuSolver.solve(sudokus.get(0));
//        sudokus.stream()
//                .map(backtrackingSudokuSolver::solve)
//                .forEach(list -> {
//                    list.forEach(System.out::println);
//                });
//        JolkaParser jolkaParser = new JolkaParser(new String[] {"data//Jolka//puzzle", "data//Jolka//words"},
//                4);
//        Jolka jolka = jolkaParser.parseFileContent();
//        jolka.useSortFromTheLongestHeuristic();
//        System.out.println(backtrackingSolver.solve(jolka));
    }
}
