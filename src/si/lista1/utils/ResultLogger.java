package si.lista1.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultLogger {
    private FileWriter fw;

    private ResultLogger() throws IOException {}

    public static ResultLogger getResultLogger() throws IOException {
        return new ResultLogger();
    }

    public void saveToLog(int i, double best, double worst, double avg) throws IOException {
        String bestStr = String.format("%.0f", best);
        try (FileWriter fw = new FileWriter(new File(getFilename(bestStr)))) {
            StringBuilder sb = new StringBuilder();
            sb.append(i+1);
            sb.append(",");
            sb.append(bestStr);
            sb.append(",");
            sb.append(String.format("%.0f", worst));
            sb.append(",");
            sb.append(String.format("%.0f", avg));
            sb.append("\n");
            String logStr = sb.toString();
            fw.write(logStr);
        }
    }

    private String getFilename(String bestStr) {
        return "log_" + bestStr + "___" + System.currentTimeMillis() + ".csv";
    }

}
