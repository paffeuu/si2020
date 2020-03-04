package si.lista1.model;

import java.util.List;

public class Genotype {
    private final List<Integer> vector;

    public Genotype(List<Integer> vector) {
        this.vector = vector;
    }

    public Integer get(int index) {
        return vector.get(index);
    }

    public Integer size() {
        return vector.size();
    }

    public List<Integer> getVector() {
        return vector;
    }
}
