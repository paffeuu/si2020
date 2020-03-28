package si.lista2.csp.sudoku;

import si.lista2.model.Puzzle;
import si.lista2.utils.ResultLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class BacktrackingSolver {
    public List<Puzzle> solve(Puzzle puzzle) {
        long startTime = System.currentTimeMillis();
        List<Puzzle> solvedPuzzles = new ArrayList<>();
        ListIterator<Object> gapsIterator = puzzle.getGaps().listIterator();
        List<Object> domain = puzzle.getDomain();
        int lastCheckedIndex = Integer.MIN_VALUE;
        Object gap = gapsIterator.next();
        int solutionsFound = 0;
        long firstSolutionTime = -1;
        int firstSolutionNodes = -1;
        int firstSolutionReturns = -1;
        int k = 0;
        int l = 0;
        do {
            for (int i = 0; i < domain.size(); i++) {
                if (i <= lastCheckedIndex) {
                    continue;
                }
                Object nextValue = domain.get(i);
                lastCheckedIndex = i;
                if (puzzle.checkConstraints(gap, nextValue)) {
                    puzzle.fillGap(gap, nextValue);
                    k++;
                    if (gapsIterator.hasNext()) {
                        gap = gapsIterator.next();
                        lastCheckedIndex = Integer.MIN_VALUE;
                        break;
                    } else {
                        if (solutionsFound == 0) {
                            firstSolutionTime = System.currentTimeMillis();
                            firstSolutionNodes = k;
                            firstSolutionReturns = l;
                            solutionsFound++;
                        }
                        Puzzle solvedPuzzle = puzzle.createSolution();
                        solvedPuzzles.add(solvedPuzzle);
                        puzzle.releaseGap(gap);
                    }
                }
            }
            if (lastCheckedIndex == domain.size() - 1) {
                gapsIterator.previous();
                while (lastCheckedIndex == domain.size() - 1 && gapsIterator.hasPrevious()) {
                    gap = gapsIterator.previous();
                    lastCheckedIndex = puzzle.releaseGap(gap);
                    k++;
                    l++;
                }
                if (gapsIterator.hasPrevious() || lastCheckedIndex < domain.size() - 1) {
                    gapsIterator.next();
                }
            } else {
                lastCheckedIndex = Integer.MIN_VALUE;
            }
        } while (lastCheckedIndex < domain.size() - 1 || gapsIterator.hasPrevious());
        long endTime = System.currentTimeMillis();
        int endNodes = k;
        int endReturns = l;

        try {
            ResultLogger resultLogger = ResultLogger.getResultLogger(puzzle.getClass().getSimpleName() + "-Id-" + puzzle.getId());
            firstSolutionTime = firstSolutionTime - startTime;
            long totalTime = endTime - startTime;
            String puzzleName = puzzle.getClass().getSimpleName();
            String puzzleId = puzzle.getId();
            resultLogger.saveToLog(puzzleName, puzzleId, firstSolutionTime, firstSolutionNodes,
                    firstSolutionReturns, totalTime, endNodes, endReturns, solutionsFound);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return solvedPuzzles;
    }
}
