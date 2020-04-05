package si.lista2.csp;

import si.lista2.model.Puzzle;

import java.util.List;

public interface Solver {
    List<Puzzle> solve(Puzzle puzzle);
}
