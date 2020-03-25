package si.lista2.utils;

import si.lista2.model.jolka.Jolka;
import si.lista2.model.sudoku.Sudoku;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JolkaParser extends Parser {
    private List<String> puzzleFileContent;
    private List<String> wordsFileContent;

    public JolkaParser(String[] fileNames) {
        File[] files = new File[fileNames.length];
        for (int i = 0; i < files.length; i++) {
            files[i] = new File(fileNames[i]);
        }
        puzzleFileContent = loadFileContent(files[0]);
        wordsFileContent = loadFileContent(files[1]);
    }

    public Jolka parseFileContent() {
        List<String> words = new ArrayList<>();
        for (int i = 0; i < wordsFileContent.size(); i++) {
            String line = wordsFileContent.get(i);
            words.add(line);
        }

        List<String> lines = new ArrayList<>();
        for (int i = 0; i < puzzleFileContent.size(); i++) {
            String line = puzzleFileContent.get(i);
            lines.add(line);
        }
        char[][] stage = new char[lines.size()][];
        for (int i = 0; i < stage.length; i++) {
            stage[i] = lines.get(i).toCharArray();
        }

        List<Integer[]> gaps = new ArrayList<>();
        //horizontal parsing
        for (int i = 0; i < stage.length; i++) {
            int wordStart = -1;
            for (int j = 0; j < stage[0].length; j++) {
                if (stage[i][j] == '#') {
                    if (wordStart != -1) {
                        Integer[] gap = new Integer[4];
                        gap[0] = wordStart;
                        gap[1] = j;
                        gap[2] = i;
                        gap[3] = i;
                        gaps.add(gap);
                        wordStart = -1;
                    }
                } else if (stage[i][j] == '_') {
                    if (wordStart == -1) {
                        wordStart = j;
                    }
                }
            }
        }
        //vertical parsing
        for (int j = 0; j < stage[0].length; j++) {
            int wordStart = -1;
            for (int i = 0; i < stage.length; i++) {
                if (stage[i][j] == '#') {
                    if (wordStart != -1) {
                        Integer[] gap = new Integer[4];
                        gap[0] = j;
                        gap[1] = j;
                        gap[2] = wordStart;
                        gap[3] = i;
                        gaps.add(gap);
                        wordStart = -1;
                    }
                } else if (stage[i][j] == '_') {
                    if (wordStart == -1) {
                        wordStart = i;
                    }
                }
            }
        }
        return new Jolka(words, gaps);
    }



}
