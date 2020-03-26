package si.lista2.model;

import java.util.List;

public interface Puzzle {
    List<Object> getDomain();
    boolean checkConstraints(Object gap, Object value);
    void fillGap(Object gap, Object value);
    int releaseGap(Object gap);
    Puzzle createSolution();
    String getId();
    List<Object> getGaps();
}
