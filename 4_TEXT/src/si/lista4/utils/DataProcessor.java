package si.lista4.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataProcessor {
    private String trainInFilePathStr;
    private String testInFilePathStr;
    private String trainOutFilePathStr;
    private String testOutFilePathStr;
    private long id = System.currentTimeMillis();
    private Set<String> categorySet;
    private int counter;
    private int threshold;
    private final double trainSize = 0.8;

    public DataProcessor(String trainInFilePathStr, String testInFilePathStr) throws IOException {
        this.trainInFilePathStr = trainInFilePathStr;
        this.testInFilePathStr = testInFilePathStr;
        this.trainOutFilePathStr = "data//weka//out_train" + id + ".arff";
        this.testOutFilePathStr = "data//weka//out_test" + id + ".arff";
    }

    public void processData() {
        int filesNumber = 0;
        File fileTrainPath = new File(trainInFilePathStr);
        if (fileTrainPath.exists() && fileTrainPath.isDirectory()) {
            File[] files = fileTrainPath.listFiles();
            filesNumber += files.length;
        }
        File fileTestPath = new File(testInFilePathStr);
        if (fileTestPath.exists() && fileTestPath.isDirectory()) {
            File[] files = fileTestPath.listFiles();
            filesNumber += files.length;
        }
        threshold = (int)(trainSize * (double)filesNumber);

        processData(trainInFilePathStr);
        processData(testInFilePathStr);
    }

    private void processData(String inFilePathStr) {
        File filePath = new File(inFilePathStr);
        categorySet = new HashSet<>();
        if (filePath.exists() && filePath.isDirectory()) {
            File[] files = filePath.listFiles();
            Arrays.stream(files)
                    .map(File::getName)
                    .filter(fileName -> fileName.contains(".txt") && !fileName.equals("license.txt"))
                    .map(fileName -> fileName.split("_")[0])
                    .forEach(categorySet::add);
            prepareHeaders();
            for (File file : files) {
                if (!file.getName().contains(".txt") || file.getName().equals("license.txt")) {
                    continue;
                }
                String fileContent = null;
                String fileCategory = file.getName().split("_")[0];
                System.out.println("Processing " + file.getName());
                try (Scanner scanner = new Scanner(file)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("\"");
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        line = line.trim();
                        line = line.replace("\"", "");
                        sb.append(line);
                        sb.append(' ');
                    }
                    sb.append("\", ");
                    sb.append(fileCategory);
                    sb.append("\n");
                    fileContent = sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (fileContent != null) {
                    if (counter < threshold) {
                        try (FileWriter fw = new FileWriter(new File(trainOutFilePathStr), true)) {
                            fw.append(fileContent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try (FileWriter fw = new FileWriter(new File(testOutFilePathStr), true)) {
                            fw.append(fileContent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
                counter++;
            }
        }
    }

    private void prepareHeaders() {
        try (FileWriter fw = new FileWriter(new File(trainOutFilePathStr), true)) {
            StringBuilder sb = new StringBuilder();
            String classes = String.join(", ", new ArrayList<>(categorySet));
            sb.append("@relation set")
                    .append(id)
                    .append("\n\n")
                    .append("@attribute text string\n")
                    .append("@attribute class {")
                    .append(classes)
                    .append("}\n\n")
                    .append("@data\n\n");
            fw.append(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
