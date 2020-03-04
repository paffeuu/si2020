package si.lista1.model;

import java.util.List;

public class Solution {
    private List<Integer> bestGenotype;
    private Double minimalDistance;

    public Solution(List<Integer> bestGenotype, Double minimalDistance) {
        this.bestGenotype = bestGenotype;
        this.minimalDistance = minimalDistance;
    }

    public List<Integer> getBestGenotype() {
        return bestGenotype;
    }

    public void setBestGenotype(List<Integer> bestGenotype) {
        this.bestGenotype = bestGenotype;
    }

    public Double getMinimalDistance() {
        return minimalDistance;
    }

    public void setMinimalDistance(Double minimalDistance) {
        this.minimalDistance = minimalDistance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("vector = ");

        for (Integer number : bestGenotype) {
            sb.append(number + " ");
        }
        sb.append("\n");
        sb.append("distance = " + minimalDistance);
        return sb.toString();
    }
}
