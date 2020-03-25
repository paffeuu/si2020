package si.lista2;

import si.lista2.model.Sudoku;
import si.lista2.utils.SudokuParser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SudokuParser sudokuParser = new SudokuParser("data//Sudoku.csv");
        List<Sudoku> sudokus = sudokuParser.parseFileContent();
        sudokus.forEach(System.out::println);
    }
}
