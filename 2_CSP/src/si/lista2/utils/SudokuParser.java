package si.lista2.utils;

import si.lista2.model.sudoku.Sudoku;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SudokuParser extends Parser {
    private List<String> fileContent;

    public SudokuParser(String fileName) {
        File file = new File(fileName);
        this.fileContent = loadFileContent(file);
    }

    public List<Sudoku> parseFileContent() {
        List<Sudoku> sudokuList = new ArrayList<>();
        for (int i = 1; i < fileContent.size(); i++) {
            String line = fileContent.get(i);
            String[] parts = line.split(";");
            int id = Integer.parseInt(parts[0]);
            double difficulty = Double.parseDouble(parts[1]);
            Sudoku sudoku = new Sudoku(id, difficulty);
            String puzzle = parts[2];
            for (int j = 0; j < puzzle.length(); j++) {
                char ch = puzzle.charAt(j);
                if (ch == '.') {
                    sudoku.addNewField(null);
                } else {
                    sudoku.addNewField(Integer.parseInt(String.valueOf(ch)));
                }
            }
            sudokuList.add(sudoku);
        }
        return sudokuList;
    }
}
