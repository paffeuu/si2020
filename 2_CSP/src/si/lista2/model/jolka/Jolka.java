package si.lista2.model.jolka;

import si.lista2.exception.UndefinedWordException;
import si.lista2.model.Puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Jolka implements Puzzle {
    protected int id;
    protected List<String> words;
    protected List<String> wordsImmutable;
    protected List<Integer[]> gaps;
    protected char[][] stage;

    public Jolka(int id, List<String> words, List<Integer[]> gaps, char[][] stage) {
        this.id = id;
        this.words = words;
        this.wordsImmutable = new ArrayList<>(words);
        this.gaps = gaps;
        this.stage = stage;
    }



    @Override
    public List getDomain() {
        return words;
    }

    @Override
    public List getInitialDomain() {
        return wordsImmutable;
    }

    @Override
    public boolean checkConstraints(Object gap, Object value) {
        Integer[] gapp = (Integer[]) gap;
        String word = (String) value;
        // horizontal
        if (gapp[2].equals(gapp[3])) {
            int length = gapp[1] - gapp[0] + 1;
            if (length != word.length()) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                char ch = stage[gapp[2]][gapp[0] + i];
                if (ch != '_' && ch != word.charAt(i)) {
                    return false;
                }
            }
        }
        // vertical
        else if (gapp[0].equals(gapp[1])) {
            int length = gapp[3] - gapp[2] + 1;
            if (length != word.length()) {
                return false;
            }
            for (int j = 0; j < length; j++) {
                char ch = stage[gapp[2] + j][gapp[0]];
                if (ch != '_' && ch != word.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void fillGap(Object gap, Object value) {
        Integer[] gapp = (Integer[]) gap;
        String word = (String) value;
        // horizontal
        if (gapp[2].equals(gapp[3])) {
            for (int i = 0; i < word.length(); i++) {
                stage[gapp[2]][gapp[0] + i] = word.charAt(i);
            }
        }
        // vertical
        else if (gapp[0].equals(gapp[1])) {
            for (int j = 0; j < word.length(); j++) {
                stage[gapp[2] + j][gapp[0]] = word.charAt(j);
            }
        }
        words.remove(value);
    }

    @Override
    public int releaseGap(Object gap) {
        Integer[] gapp = (Integer[]) gap;
        StringBuilder sb = new StringBuilder();
        // horizontal
        if (gapp[2].equals(gapp[3])) {
            int length = gapp[1] - gapp[0] + 1;
            for (int i = 0; i < length; i++) {
                char ch = stage[gapp[2]][gapp[0] + i];
                sb.append(ch);
//                boolean remove = true;
//                int vertChUpper = gapp[2] - 1;
//                if (vertChUpper >= 0) {
//                    if (stage[gapp[2] - 1][gapp[0] + i] != '_' && stage[gapp[2] - 1][gapp[0] + i] != '#') {
//                        remove = false;
//                    }
//                }
//                int vertChLower = gapp[2] + 1;
//                if (vertChLower < stage.length) {
//                    if (stage[gapp[2] + 1][gapp[0] + i] != '_' && stage[gapp[2] + 1][gapp[0] + i] != '#') {
//                        remove = false;
//                    }
//                }
                if (canBeRemoved(gapp[0] + i, gapp[2], gapp)) {
                    stage[gapp[2]][gapp[0] + i] = '_';
                }
            }
        }
        // vertical
        else if (gapp[0].equals(gapp[1])) {
            int length = gapp[3] - gapp[2] + 1;
            for (int j = 0; j < length; j++) {
                char ch = stage[gapp[2] + j][gapp[0]];
                sb.append(ch);
//                boolean remove = true;
//                int horChLeft = gapp[0] - 1;
//                if (horChLeft >= 0) {
//                    if (stage[gapp[2] + j][gapp[0] - 1] != '_' && stage[gapp[2] + j][gapp[0] - 1] != '#') {
//                        remove = false;
//                    }
//                }
//                int horChRight = gapp[0] + 1;
//                if (horChRight < stage[0].length) {
//                    if (stage[gapp[2] + j][gapp[0] + 1] != '_' && stage[gapp[2] + j][gapp[0] + 1] != '#') {
//                        remove = false;
//                    }
//                }
                if (canBeRemoved(gapp[0], gapp[2] + j, gapp)) {
                    stage[gapp[2] + j][gapp[0]] = '_';
                }
            }
        }
        String word = sb.toString();
        int index = wordsImmutable.indexOf(word);
        for (int i = 0; i < words.size(); i++) {
            if (wordsImmutable.indexOf(words.get(i)) > index) {
                words.add(i, word);
                return i;
            }
        }
        words.add(word);
        return words.size() - 1;
    }

    private boolean canBeRemoved(int gapX, int gapY, Integer[] gap) {
        for (Integer[] gapp : gaps) {
            if (Arrays.equals(gap, gapp)) {
                break;
            }
            // horizontal
            if (gapp[2].equals(gapp[3])) {
                int length = gapp[1] - gapp[0] + 1;
                for (int i = 0; i < length; i++) {
                    if (gapp[0] + i == gapX && gapp[2] == gapY) {
                        return false;
                    }
                }
            // vertical
            } else if (gapp[0].equals(gapp[1])) {
                int length = gapp[3] - gapp[2] + 1;
                for (int j = 0; j < length; j++) {
                    if (gapp[0] == gapX && gapp[2] + j == gapY) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Puzzle createSolution() {
        return new SolvedJolka(this);
    }

    @Override
    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public List getGaps() {
        return gaps;
    }

    private String getPrintableVisualization() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stage.length; i++) {
            for (int j = 0; j < stage[0].length; j++) {
                sb.append(stage[i][j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Jolka\nId: " + id + "\n" +
                getPrintableVisualization();
    }
}
