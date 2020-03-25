package si.lista1.ea;

import si.lista1.ea.strategy.Strategy;
import si.lista1.model.DistanceMatrix;
import si.lista1.model.Place;
import si.lista1.model.Solution;

import java.util.Map;
import java.util.Set;

public class SolutionFinder {
    private Strategy strategy;
    private String lastResultDescription;

    public SolutionFinder(Strategy strategy) {
        this.strategy = strategy;
        this.lastResultDescription = "No solution found yet.";
    }

    public Solution findOptimalSolution(Map<Integer, Place> places) {
        Solution optimalSolution = strategy.findOptimalSolution(places);
        setLastResultDescription(optimalSolution);
        return optimalSolution;
    }

    public String getLastResultDescription() {
        return lastResultDescription;
    }

    private void setLastResultDescription(Solution optimalSolution) {
        StringBuilder sb = new StringBuilder();
        sb.append("Using strategy: ");
        sb.append(strategy.getName());
        sb.append(" (");
        sb.append(strategy.getRepetitions());
        sb.append(" repetitions)\n");
        sb.append("Best found solution:\n");
        sb.append(optimalSolution);
        lastResultDescription = sb.toString();
    }
}
