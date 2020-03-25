package si.lista1.ea.strategy;

import si.lista1.model.DistanceMatrix;
import si.lista1.model.Place;
import si.lista1.model.Solution;

import java.util.Map;

public abstract class Strategy {
    private final String name;
    protected final int repetitions;
    protected final DistanceMatrix distanceMatrix;

    protected Strategy(String name, int repetitions, DistanceMatrix distanceMatrix) {
        this.name = name;
        this.repetitions = repetitions;
        this.distanceMatrix = distanceMatrix;
    }

    public abstract Solution findOptimalSolution(Map<Integer, Place> places);

    public String getName() {
        return name;
    }

    public int getRepetitions() {
        return repetitions;
    }
}
