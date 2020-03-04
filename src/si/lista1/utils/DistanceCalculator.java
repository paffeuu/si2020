package si.lista1.utils;

import si.lista1.model.DistanceMatrix;
import si.lista1.model.Genotype;
import si.lista1.model.Place;

import java.util.Map;

public class DistanceCalculator {
    public double calculateDistance(Place object1, Place object2) {
        double distanceX = Math.abs(object1.x - object2.x);
        double distanceY = Math.abs(object1.y - object2.y);
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    public double sumDistance(Genotype genotype, Map<Integer, Place> places, DistanceMatrix distanceMatrix) {
        double sum = 0;
        for (int i = 0; i < genotype.size() - 1; i++) {
            sum += distanceMatrix.getDistance(genotype.get(i),genotype.get(i+1));
        }
        if (genotype.size() != 0) {
            sum += distanceMatrix.getDistance(genotype.get(genotype.size() - 1), genotype.get(0));
        }
        return sum;
    }
}
