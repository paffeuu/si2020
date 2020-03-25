package si.lista1.utils;

import java.util.Comparator;
import java.util.List;

public class StatisticsPrinter {
    public void printStatistics(List<Double> results, int repetitions) {
        results.sort(Comparator.naturalOrder());
        double best = results.get(0);
        double worst = results.get(repetitions-1);
        double sum = results.stream().mapToDouble(Double::doubleValue).sum();
        double avg = sum / repetitions;
        double variance = 0;
        for (double result : results) {
            variance += Math.pow((result - avg),2);
        }
        double std = Math.sqrt(variance);

        System.out.println("best = " + best);
        System.out.println("worst = " + worst);
        System.out.println("avg = " + avg);
        System.out.println("std = " + std);
    }
}
