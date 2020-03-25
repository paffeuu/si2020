package si.lista2.utils;

import si.lista2.model.sudoku.Sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SudokuParser {
    private File file;
    private List<String> fileContent;

    public SudokuParser(String fileName) {
        this.file = new File(fileName);
        this.fileContent = loadFileContent();
    }

    private List<String> loadFileContent() {
        List<String> textContent = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                textContent.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return textContent;
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
