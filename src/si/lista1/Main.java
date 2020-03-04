package si.lista1;

import si.lista1.ea.SolutionFinder;
import si.lista1.ea.strategy.RandomStrategy;
import si.lista1.model.DataFile;
import si.lista1.model.DistanceMatrix;
import si.lista1.model.Place;
import si.lista1.utils.DataLoader;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String[] fileNames = new String[]{"data\\berlin11_modified.tsp"};
        DataLoader dataLoader = new DataLoader(fileNames);
        List<DataFile> dataFileList = dataLoader.load();

        Map<Integer, Place> places = dataFileList.get(0).getPlaces();

        DistanceMatrix distanceMatrix = new DistanceMatrix(places);
        SolutionFinder solutionFinder = new SolutionFinder(new RandomStrategy(100000, distanceMatrix));
        solutionFinder.findOptimalSolution(places);
        System.out.println(solutionFinder.getLastResultDescription());


    }
}
