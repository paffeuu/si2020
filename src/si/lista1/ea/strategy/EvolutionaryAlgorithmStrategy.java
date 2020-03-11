package si.lista1.ea.strategy;

import si.lista1.ea.RandomGenotypeGenerator;
import si.lista1.model.*;
import si.lista1.utils.DistanceCalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvolutionaryAlgorithmStrategy extends Strategy {

    private RandomGenotypeGenerator genotypeGenerator;
    private SelectionType selectionType;
    private int populationSize = 100;
    private int tournamentSize = 50;

    public EvolutionaryAlgorithmStrategy(DistanceMatrix distanceMatrix, SelectionType selectionType) {
        super("EA strategy(selection type: " + selectionType.name() + ")", 1, distanceMatrix);
        this.genotypeGenerator = new RandomGenotypeGenerator();
        this.selectionType = selectionType;
    }

    @Override
    public Solution findOptimalSolution(Map<Integer, Place> places) {
        List<Genotype> population = initializePopulation(places);
        List<Genotype> selectedPopulation = null;
        switch (selectionType) {
            case TOURNAMENT:
                selectedPopulation = conductTournamentSelection(population, tournamentSize);
                break;
            case ROULETTE:
                selectedPopulation = conductRouletteSelection(population);
        }
        List<Genotype> populationAfterCrossover = conductOrderedCrossover(selectedPopulation);
        return null;
    }

    private List<Genotype> initializePopulation(Map<Integer, Place> places) {
        List<Genotype> population = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            population.add(genotypeGenerator.generate(places));
        }
        return population;
    }

    private List<Genotype> conductTournamentSelection(List<Genotype> population, int tournamentSize) {
        List<Genotype> selectedPopulation = new ArrayList<>(population.size());
        while (selectedPopulation.size() != population.size()) {
            List<Genotype> tournament = new ArrayList<>(tournamentSize);
            for (int i = 0; i < tournamentSize; i++) {
                int randomIndex = (int) (Math.random() * population.size());
                Genotype randomGenotype = population.remove(randomIndex);
                tournament.add(randomGenotype);
            }
            DistanceCalculator distanceCalculator = new DistanceCalculator();
            Genotype bestGenotype = null;
            double minimalDistance = Double.MAX_VALUE;
            for (Genotype genotype : tournament) {
                double distance = distanceCalculator.sumDistance(genotype, distanceMatrix);
                if (distance < minimalDistance) {
                    minimalDistance = distance;
                    bestGenotype = genotype;
                }
            }
            selectedPopulation.add(bestGenotype);
            population.addAll(tournament);
            System.out.println(new Solution(bestGenotype, minimalDistance));
        }
        return selectedPopulation;
    }

    private List<Genotype> conductRouletteSelection(List<Genotype> population) {
        Map<Genotype, Double> distanceMap = new HashMap<>();
        DistanceCalculator distanceCalculator = new DistanceCalculator();
        double sum = 0;
        for (Genotype genotype : population) {
            double distance = distanceCalculator.sumDistance(genotype, distanceMatrix);
            distanceMap.put(genotype, distance);
            sum += distance;
        }
        Map<Genotype, Double> likelihoodMap = new HashMap<>();
        for (Genotype genotype : population) {
            double distance = distanceMap.get(genotype);
            double likelihood = distance / sum;
            likelihoodMap.put(genotype, likelihood);
        }
        List<Double> limitList = new ArrayList<>();
        double limit = 0;
        for (Genotype genotype : population) {
            double likelihood = likelihoodMap.get(genotype);
            limit += likelihood;
            limitList.add(limit);
        }
        List<Genotype> selectionPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            double randomNr = Math.random();
            for (int j = 0; j < limitList.size(); j++) {
                if (randomNr < limitList.get(j)) {
                    selectionPopulation.add(population.get(j));
                    break;
                }
            }
        }
        return selectionPopulation;
    }

    private List<Genotype> conductOrderedCrossover(List<Genotype> population) {
//        for ()
//        int firstIntersection = (int)(Math.random() * population.size());
//        int secondIntersection = (int)(Math.random() * population.size());
        return null;
    }


}
