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
        String[] fileNames = new String[]{"data\\fl417.tsp"};
        DataLoader dataLoader = new DataLoader(fileNames);
        List<DataFile> dataFileList = dataLoader.load();

        Map<Integer, Place> places = dataFileList.get(0).getPlaces();
        DistanceMatrix distanceMatrix = new DistanceMatrix(places);

        SolutionFinder solutionFinder = new SolutionFinder(new RandomStrategy(1000000, 10, distanceMatrix));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
//
//        solutionFinder = new SolutionFinder(new GreedyStrategy(distanceMatrix));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());




        System.out.println("Pm = 0.05");

        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
                distanceMatrix, SelectionType.TOURNAMENT,
                1000, 15, 1000,
                0.9, 0.05, 10));
        solutionFinder.findOptimalSolution(places);
        System.out.println(solutionFinder.getLastResultDescription());

        System.out.println("Pm = 0.2");

        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
                distanceMatrix, SelectionType.TOURNAMENT,
                1000, 15, 1000,
                0.9, 0.2, 10));
        solutionFinder.findOptimalSolution(places);
        System.out.println(solutionFinder.getLastResultDescription());
        System.out.println("Pm = 0.3");

        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
                distanceMatrix, SelectionType.TOURNAMENT,
                1000, 15, 1000,
                0.9, 0.3, 10));
        solutionFinder.findOptimalSolution(places);
        System.out.println(solutionFinder.getLastResultDescription());
    }


}
