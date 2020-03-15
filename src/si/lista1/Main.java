package si.lista1;

import si.lista1.ea.SolutionFinder;
import si.lista1.ea.strategy.EvolutionaryAlgorithmStrategy;
import si.lista1.ea.strategy.GreedyStrategy;
import si.lista1.ea.strategy.RandomStrategy;
import si.lista1.model.DataFile;
import si.lista1.model.DistanceMatrix;
import si.lista1.model.Place;
import si.lista1.model.SelectionType;
import si.lista1.utils.DataLoader;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String[] fileNames = new String[]{"data\\berlin52.tsp"};
        DataLoader dataLoader = new DataLoader(fileNames);
        List<DataFile> dataFileList = dataLoader.load();

        Map<Integer, Place> places = dataFileList.get(0).getPlaces();
        DistanceMatrix distanceMatrix = new DistanceMatrix(places);

        SolutionFinder solutionFinder = new SolutionFinder(new RandomStrategy(10000, distanceMatrix));
        solutionFinder.findOptimalSolution(places);
        System.out.println(solutionFinder.getLastResultDescription());

        solutionFinder = new SolutionFinder(new GreedyStrategy(distanceMatrix));
        solutionFinder.findOptimalSolution(places);
        System.out.println(solutionFinder.getLastResultDescription());

        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
                distanceMatrix, SelectionType.TOURNAMENT,
                400, 10, 2000,
                0.7, 0.2, 100));
        solutionFinder.findOptimalSolution(places);
        System.out.println(solutionFinder.getLastResultDescription());


    }
}
