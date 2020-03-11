package si.lista1.model;

public class Solution {
    private Genotype bestGenotype;
    private Double minimalDistance;

    public Solution(Genotype bestGenotype, Double minimalDistance) {
        this.bestGenotype = bestGenotype;
        this.minimalDistance = minimalDistance;
    }

    public Genotype getBestGenotype() {
        return bestGenotype;
    }

    public void setBestGenotype(Genotype bestGenotype) {
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
        sb.append("Vector = ");
        sb.append(bestGenotype);
        sb.append("\n");
        sb.append("Distance = ");
        sb.append(String.format("%.2f", minimalDistance));
        return sb.toString();
    }
}
