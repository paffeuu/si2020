package si.lista1.ea;

import si.lista1.model.Genotype;
import si.lista1.model.Place;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class RandomGenotypeGenerator {

    public Genotype generate(Map<Integer, Place> places) {
        ArrayList<Integer> genotypeVector = new ArrayList<>(places.size());
        for (int i = 1; i < places.size() + 1; i++) {
            genotypeVector.add(i);
        }
        Collections.shuffle(genotypeVector);
        return new Genotype(genotypeVector);
    }
}
