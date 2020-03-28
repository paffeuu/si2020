package si.lista2.model.jolka;

import si.lista2.exception.UndefinedWordException;
import si.lista2.model.Puzzle;

import java.util.ArrayList;
import java.util.List;

public class Jolka implements Puzzle {
    private int id;
    private List<String> words;
    private List<String> wordsImmutable;
    private List<Integer[]> gaps;
    private char[][] stage;

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
                boolean remove = true;
                int vertChUpper = gapp[2] - 1;
                if (vertChUpper >= 0) {
                    if (stage[gapp[2] - 1][gapp[0] + i] != '_' && stage[gapp[2] - 1][gapp[0] + i] != '#') {
                        remove = false;
                    }
                }
                int vertChLower = gapp[2] + 1;
                if (vertChLower >= 0) {
                    if (stage[gapp[2] + 1][gapp[0] + i] != '_' && stage[gapp[2] + 1][gapp[0] + i] != '#') {
                        remove = false;
                    }
                }
                if (remove) {
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
                boolean remove = true;
                int horChLeft = gapp[0] - 1;
                if (horChLeft >= 0) {
                    if (stage[gapp[2] + j][gapp[0] - 1] != '_' && stage[gapp[2] + j][gapp[0] - 1] != '#') {
                        remove = false;
                    }
                }
                int horChRight = gapp[0] + 1;
                if (horChRight >= 0) {
                    if (stage[gapp[2] + j][gapp[0] + 1] != '_' && stage[gapp[2] + j][gapp[0] + 1] != '#') {
                        remove = false;
                    }
                }
                if (remove) {
                    stage[gapp[2] + j][gapp[0]] = '_';
                }
            }
        }
        String word = sb.toString();
        for (int k = 0; k < wordsImmutable.size(); k++) {
            if (word.equals(wordsImmutable.get(k))) {
                words.add(0, word);
                return k;
            }
        }
        throw new UndefinedWordException("Word '" + word + "' cannot be found in words set.");
    }

    @Override
    public Puzzle createSolution() {
        return this;
    }

    @Override
    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public List getGaps() {
        return gaps;
    }

    @Override
    public boolean shouldStopSearching() {
        return true;
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
