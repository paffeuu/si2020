package si.lista1.ea.strategy;

import si.lista1.ea.RandomGenotypeGenerator;
import si.lista1.model.*;
import si.lista1.utils.DistanceCalculator;

import java.util.*;

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
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        for (int i = 0; i < 100; i++) {
            switch (selectionType) {
                case TOURNAMENT:
                    population = conductTournamentSelection(population, tournamentSize);
                    break;
                case ROULETTE:
                    population = conductRouletteSelection(population);
            }
            population = conductOrderedCrossover(population);
            conductSwapMutation(population, 0.05);
            DistanceCalculator distanceCalculator = new DistanceCalculator();
            for (Genotype genotype : population) {
                double distance = distanceCalculator.sumDistance(genotype, distanceMatrix);
                if (distance < minimalDistance) {
                    minimalDistance = distance;
                    bestGenotype = genotype;
                }
            }
        }
        return new Solution(bestGenotype, minimalDistance);
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
        List<Genotype> populationAfterCrossover = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            Random random = new Random();
            int firstIntersection = (random.nextInt(population.get(0).size()));
            int secondIntersection;
            do {
                secondIntersection = (random.nextInt(population.get(0).size()));
            } while (firstIntersection == secondIntersection);
            if (firstIntersection > secondIntersection) {
                int temp = secondIntersection;
                secondIntersection = firstIntersection;
                firstIntersection = temp;
            }
            Genotype firstParent = population.get(i);
            Genotype secondParent = population.get((i + 1) % population.size());
            Genotype child = new Genotype(secondParent);
            List<Integer> placesToRelocate = new ArrayList<>();
            for (int j = firstIntersection; j <= secondIntersection; j++) {
                placesToRelocate.add(firstParent.get(j));
            }
            for (Integer placeToRelocate : placesToRelocate) {
                child.getVector().removeIf(number -> number.equals(placeToRelocate));
            }
            for (int k = 0; k < placesToRelocate.size(); k++ ) {
                child.getVector().add(firstIntersection + k, placesToRelocate.get(k));
            }
            populationAfterCrossover.add(child);
        }
        return populationAfterCrossover;
    }

    private void conductSwapMutation(List<Genotype> population, double mutationLikelihood) {
        Random random = new Random();
        for (Genotype genotype : population) {
            if (random.nextDouble() < mutationLikelihood) {
                int firstPlace = (random.nextInt(genotype.size()));
                int secondPlace;
                do {
                    secondPlace = (random.nextInt(genotype.size()));
                } while (firstPlace == secondPlace);
                int firstValue = genotype.getVector().get(firstPlace);
                int secondValue = genotype.getVector().get(secondPlace);
                genotype.getVector().remove(firstPlace);
                genotype.getVector().add(firstPlace, secondValue);
                genotype.getVector().remove(secondPlace);
                genotype.getVector().add(secondPlace, firstValue);
            }
        }
    }

}
