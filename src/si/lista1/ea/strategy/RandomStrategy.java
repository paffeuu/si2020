package si.lista1.ea.strategy;

import si.lista1.ea.RandomGenotypeGenerator;
import si.lista1.model.DistanceMatrix;
import si.lista1.model.Genotype;
import si.lista1.model.Place;
import si.lista1.model.Solution;
import si.lista1.utils.DistanceCalculator;

import java.util.Map;

public class RandomStrategy extends Strategy {

    public RandomStrategy(int repetitions, DistanceMatrix distanceMatrix) {
        super("Random strategy", repetitions, distanceMatrix);
    }

    public Solution findOptimalSolution(Map<Integer, Place> places) {
        RandomGenotypeGenerator randomGenotypeGenerator = new RandomGenotypeGenerator();
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        for (int i = 0; i < getRepetitions(); i++) {
            Genotype genotype = randomGenotypeGenerator.generate(places);
            double distance = distanceCalculator.sumDistance(genotype, places, distanceMatrix);
            if (distance < minimalDistance) {
                minimalDistance = distance;
                bestGenotype = genotype;
            }
        }
        return new Solution(bestGenotype, minimalDistance);
    }
}
