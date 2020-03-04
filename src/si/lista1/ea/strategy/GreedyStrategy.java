package si.lista1.ea.strategy;

import si.lista1.model.DistanceMatrix;
import si.lista1.model.Genotype;
import si.lista1.model.Place;
import si.lista1.model.Solution;
import si.lista1.utils.DistanceCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GreedyStrategy extends Strategy {
    public GreedyStrategy(DistanceMatrix distanceMatrix) {
        super("Greedy strategy", 1, distanceMatrix);
    }

    @Override
    public Solution findOptimalSolution(Map<Integer, Place> places) {
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        for (int i = 1; i < places.size() + 1; i++) {
            Genotype genotype = findGreedySolutionStartingFrom(i, places);
            double distance = distanceCalculator.sumDistance(genotype, places, distanceMatrix);
            if (distance < minimalDistance) {
                minimalDistance = distance;
                bestGenotype = genotype;
            }
        }
        return new Solution(bestGenotype, minimalDistance);
    }

    private Genotype findGreedySolutionStartingFrom(int startId, Map<Integer, Place> places) {
        List<Integer> vector = new ArrayList<>();
        int fromId = startId;
        vector.add(fromId);
        while(vector.size() != places.size()) {
            double minimalDistance = Double.MAX_VALUE;
            int nextId = -1;
            for (int i = 1; i < places.size() + 1; i++) {
                if (i == fromId || vector.contains(i)) {
                    continue;
                }
                double distance = distanceMatrix.getDistance(fromId, i);
                if (distance < minimalDistance) {
                    minimalDistance = distance;
                    nextId = i;
                }
            }
            vector.add(nextId);
            fromId = nextId;
        }
        return new Genotype(vector);
    }
}
