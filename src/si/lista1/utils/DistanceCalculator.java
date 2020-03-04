package si.lista1.utils;

import si.lista1.model.EuclidesCoordinates;

import java.util.List;

public class DistanceCalculator {
    public double calculateDistance(EuclidesCoordinates object1, EuclidesCoordinates object2) {
        double distanceX = Math.abs(object1.x - object2.x);
        double distanceY = Math.abs(object1.y - object2.y);
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    public double sumDistance(List<Integer> genotype, List<EuclidesCoordinates> coordinates) {
        double sum = 0;
        for (int i = 0; i < genotype.size() - 1; i++) {
            sum += calculateDistance(coordinates.get(genotype.get(i)), coordinates.get(genotype.get(i+1)));
        }
        if (genotype.size() != 0) {
            sum += calculateDistance(coordinates.get(genotype.get(genotype.size() - 1)), coordinates.get(genotype.get(0)));
        }

        return sum;
    }
}
