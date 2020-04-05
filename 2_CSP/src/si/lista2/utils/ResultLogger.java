package si.lista2.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultLogger {
    private FileWriter fw;

    private ResultLogger(String params) throws IOException {
        this.fw = new FileWriter(new File("log\\" + params + getFilename()), true);
    }

    public static ResultLogger getResultLogger(String params) throws IOException {
        return new ResultLogger(params);
    }

    public void saveToLog(String puzzleName, String puzzleId, long firstSolutionTime, int firstSolutionNodes,
                          int firstSolutionReturns, long totalTime, int totalNodes, int totalReturns,
                          int solutionsFound) throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append(puzzleName);
        sb.append("\n");
        sb.append("Id: ");
        sb.append(puzzleId);
        sb.append("\n");
        sb.append("Czas do znalezienia pierwszego rozwiązania: ");
        sb.append(firstSolutionTime);
        sb.append(" ms\n");
        sb.append("Liczba odwiedzonych węzłów drzewa do znalezienia pierwszego rozwiązania: ");
        sb.append(firstSolutionNodes);
        sb.append("\n");
        sb.append("Liczba nawrotów wykonanych do znalezienia pierwszego rozwiązania: ");
        sb.append(firstSolutionReturns);
        sb.append("\n");
        sb.append("Całkowity czas działania metody: ");
        sb.append(totalTime);
        sb.append(" ms\n");
        sb.append("Całkowita liczba odwiedzonych węzłów drzewa: ");
        sb.append(totalNodes);
        sb.append("\n");
        sb.append("Całkowita liczba nawrotów: ");
        sb.append(totalReturns);
        sb.append("\n");
        sb.append("Liczba rozwiązań: ");
        sb.append(solutionsFound);
        String logStr = sb.toString();
        fw.write(logStr);
        fw.flush();
        System.out.println(logStr);
    }

    private String getFilename() {
        return "_log_" + System.currentTimeMillis() + ".log";
    }
}
