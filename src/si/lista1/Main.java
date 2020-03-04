package si.lista1;

import si.lista1.ea.RandomStrategy;
import si.lista1.model.DataFile;
import si.lista1.model.EuclidesCoordinates;
import si.lista1.model.Solution;
import si.lista1.utils.DataLoader;
import si.lista1.utils.DistanceCalculator;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String[] fileNames = new String[]{"data\\ali535.tsp"};
        DataLoader dataLoader = new DataLoader(fileNames);
        List<DataFile> dataFileList = dataLoader.load();
        for (DataFile dataFile : dataFileList) {
            System.out.println(dataFile);
        }

        DistanceCalculator distanceCalculator = new DistanceCalculator();
        System.out.println(distanceCalculator.calculateDistance(new EuclidesCoordinates(1.0, 10.0), new EuclidesCoordinates(3.5, 13.0)));


        System.out.println("Random strategy:");
        RandomStrategy randomStrategy = new RandomStrategy();
        Solution optimalSolution = randomStrategy.findOptimalSolution(dataFileList.get(0));
        System.out.println(optimalSolution);
    }
}
