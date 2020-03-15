package si.lista1.ea.strategy;

import si.lista1.ea.RandomGenotypeGenerator;
import si.lista1.model.*;
import si.lista1.utils.DistanceCalculator;
import si.lista1.utils.ResultLogger;

import java.io.IOException;
import java.util.*;

public class EvolutionaryAlgorithmStrategy extends Strategy {

    private RandomGenotypeGenerator genotypeGenerator;
    private SelectionType selectionType;
    private int populationSize;
    private int tournamentSize;
    private int generations;
    private double crossoverLikelihood;
    private double mutationLikelihood;

    private ResultLogger logger;

    public EvolutionaryAlgorithmStrategy(DistanceMatrix distanceMatrix, SelectionType selectionType,
                                         int populationSize, int tournamentSize, int generations,
                                         double crossoverLikelihood, double mutationLikelihood,
                                         int repetitions) {
        super("EA strategy(selection type: " + selectionType.name() + ")", repetitions, distanceMatrix);
        this.genotypeGenerator = new RandomGenotypeGenerator();
        this.selectionType = selectionType;
        this.populationSize = populationSize;
        this.tournamentSize = tournamentSize;
        this.generations = generations;
        this.crossoverLikelihood = crossoverLikelihood;
        this.mutationLikelihood = mutationLikelihood;
    }

    @Override
    public Solution findOptimalSolution(Map<Integer, Place> places) {
        Genotype bestGenotype = null;
        double minimalDistance = Double.MAX_VALUE;
        for (int j = 0; j < repetitions; j++) {
            getNewLogFile();
            List<Genotype> population = initializePopulation(places);
            for (int i = 0; i < generations; i++) {
                double bestInPop = Double.MAX_VALUE;
                double worstInPop = Double.MIN_VALUE;
                double sumInPop = 0;
                Genotype bestGenotypeInPop = null;
                switch (selectionType) {
                    case TOURNAMENT:
                        population = conductTournamentSelection(population, tournamentSize);
                        break;
                    case ROULETTE:
                        population = conductRouletteSelection(population);
                }
                population = conductOrderedCrossover(population, crossoverLikelihood);
                conductSwapMutation(population, mutationLikelihood);
                DistanceCalculator distanceCalculator = new DistanceCalculator();
                for (Genotype genotype : population) {
                    double distance = distanceCalculator.sumDistance(genotype, distanceMatrix);
                    if (distance < bestInPop) {
                        bestInPop = distance;
                        bestGenotypeInPop = genotype;
                    }
                    if (distance > worstInPop) {
                        worstInPop = distance;
                    }
                    sumInPop += distance;
                }
                double avgOfPop = sumInPop / populationSize;
                logResult(i, bestInPop, worstInPop, avgOfPop);
                if (bestInPop < minimalDistance) {
                    minimalDistance = bestInPop;
                    bestGenotype = bestGenotypeInPop;
                }
                if (i+1 == generations) {
                    System.out.println(bestInPop);
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

    private List<Genotype> conductOrderedCrossover(List<Genotype> population, double crossoverLikelihood) {
        Random random = new Random();
        List<Genotype> populationAfterCrossover = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            if (random.nextDouble() < crossoverLikelihood) {
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
            } else {
                populationAfterCrossover.add(population.get(i));
            }
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

    private void getNewLogFile() {
        try {
            this.logger = ResultLogger.getResultLogger();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logResult(int i, double best, double worst, double avg) {
        if (logger != null) {
            try {
                logger.saveToLog(i, best, worst, avg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
