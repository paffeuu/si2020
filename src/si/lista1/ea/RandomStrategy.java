package si.lista1.ea;

import si.lista1.model.DataFile;
import si.lista1.model.Solution;
import si.lista1.utils.DistanceCalculator;

import java.util.List;

public class RandomStrategy {
    public Solution findOptimalSolution(DataFile dataFile) {
        RandomGenotypeGenerator randomGenotypeGenerator = new RandomGenotypeGenerator();
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        List<Integer> bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        for (int i = 0; i < 10000; i++) {
            List<Integer> genotype = randomGenotypeGenerator.generate(dataFile.getCoordinates());
            double distance = distanceCalculator.sumDistance(genotype, dataFile.getCoordinates());
            if (distance < minimalDistance) {
                minimalDistance = distance;
                bestGenotype = genotype;
            }
        }
        return new Solution(bestGenotype, minimalDistance);
    }
}
