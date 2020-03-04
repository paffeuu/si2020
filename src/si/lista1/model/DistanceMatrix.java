package si.lista1.model;

import si.lista1.utils.DistanceCalculator;

import java.util.Map;

public class DistanceMatrix {
    private final double[][] matrix;

    public DistanceMatrix(Map<Integer, Place> places) {
        matrix = new double[places.size()+1][places.size()+1];

        DistanceCalculator distanceCalculator = new DistanceCalculator();

        for (int i = 1; i < places.size() + 1; i++) {
            for (int j = 1; j < places.size() + 1; j++) {
                matrix[j][i] = distanceCalculator.calculateDistance(places.get(j), places.get(i));
            }
        }
    }

    public double getDistance(int id1, int id2) {
        return matrix[id1][id2];
    }
}
