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
        String fileName = "fl417";
        String[] fileNames = new String[]{"data\\" + fileName + ".tsp"};
        DataLoader dataLoader = new DataLoader(fileNames);
        List<DataFile> dataFileList = dataLoader.load();

        Map<Integer, Place> places = dataFileList.get(0).getPlaces();
        DistanceMatrix distanceMatrix = new DistanceMatrix(places);
//
        SolutionFinder solutionFinder;

        solutionFinder = new SolutionFinder(new RandomStrategy(1000000, 10, distanceMatrix));
        solutionFinder.findOptimalSolution(places);
        System.out.println(solutionFinder.getLastResultDescription());

        solutionFinder = new SolutionFinder(new GreedyStrategy(distanceMatrix));
        solutionFinder.findOptimalSolution(places);
        System.out.println(solutionFinder.getLastResultDescription());



                solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
                distanceMatrix, SelectionType.TOURNAMENT,
                1000, 5, 1000,
                0.7, 0.15, 10, fileName));
        solutionFinder.findOptimalSolution(places);
        System.out.println(solutionFinder.getLastResultDescription());

//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.1, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
////
//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.01, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.05, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.15, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.2, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());


        fileName = "kroA150";
        fileNames = new String[]{"data\\" + fileName + ".tsp"};
        dataLoader = new DataLoader(fileNames);
        dataFileList = dataLoader.load();

        places = dataFileList.get(0).getPlaces();
        distanceMatrix = new DistanceMatrix(places);

//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.05, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());

//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.01, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.5, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.15, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.2, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());



        fileName = "kroA200";
        fileNames = new String[]{"data\\" + fileName + ".tsp"};
        dataLoader = new DataLoader(fileNames);
        dataFileList = dataLoader.load();

        places = dataFileList.get(0).getPlaces();
        distanceMatrix = new DistanceMatrix(places);

//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.05, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
//
//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.ROULETTE,
//                1000, 3, 1000,
//                0.7, 0.1, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 8, 1000,
//                0.7, 0.1, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 10, 1000,
//                0.7, 0.1, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());

//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.01, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.5, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.15, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());
//        solutionFinder = new SolutionFinder(new EvolutionaryAlgorithmStrategy(
//                distanceMatrix, SelectionType.TOURNAMENT,
//                1000, 5, 1000,
//                0.7, 0.2, 10, fileName));
//        solutionFinder.findOptimalSolution(places);
//        System.out.println(solutionFinder.getLastResultDescription());

    }


}
