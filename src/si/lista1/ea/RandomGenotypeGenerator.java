package si.lista1.ea;

import si.lista1.model.EuclidesCoordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomGenotypeGenerator {

    public List<Integer> generate(List<EuclidesCoordinates> objects) {
        ArrayList<Integer> genotype = new ArrayList<>(objects.size());
        for (int i = 0; i < objects.size(); i++) {
            genotype.add(i);
        }
        Collections.shuffle(genotype);
        return genotype;
    }
}
