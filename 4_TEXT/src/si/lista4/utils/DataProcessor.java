package si.lista4.utils;

import si.lista4.model.Record;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataProcessor {
    private String trainInFilePathStr;
    private String testInFilePathStr;
    private String trainOutFilePathStr;
    private String testOutFilePathStr;

    private Map<String, List<Record>> recordMap;
    private List<Record> outTrainList;
    private List<Record> outTestList;

    private long timestamp = System.currentTimeMillis();
    private final double trainRatio;

    public DataProcessor(String trainInFilePathStr, String testInFilePathStr, double trainRatio) throws IOException {
        this.trainInFilePathStr = trainInFilePathStr;
        this.testInFilePathStr = testInFilePathStr;
        this.trainOutFilePathStr = "data//weka//out_train" + timestamp + ".arff";
        this.testOutFilePathStr = "data//weka//out_test" + timestamp + ".arff";
        this.trainRatio = trainRatio;

        recordMap = new HashMap<>();
        outTrainList = new ArrayList<>();
        outTestList = new ArrayList<>();
    }

    public void processData() {
        File trainInFilePath = new File(trainInFilePathStr);
        File testInFilePath = new File(testInFilePathStr);

        loadCategoriesFromFileNames(trainInFilePath);
        loadCategoriesFromFileNames(testInFilePath);

        loadRecords(trainInFilePath);
        loadRecords(testInFilePath);

        divideRecordsBetweenSets();

        File trainOutFilePath = new File(trainOutFilePathStr);
        File testOutFilePath = new File(testOutFilePathStr);

        prepareHeaders(trainOutFilePath);
        prepareDataContent(trainOutFilePath, outTrainList);

        prepareHeaders(testOutFilePath);
        prepareDataContent(testOutFilePath, outTestList);
    }

    private void loadCategoriesFromFileNames(File inFilePath) {
        if (inFilePath.exists() && inFilePath.isDirectory()) {
            File[] files = inFilePath.listFiles();
            Arrays.stream(files)
                    .map(File::getName)
                    .filter(fileName -> fileName.contains(".txt") && !fileName.equals("license.txt"))
                    .map(fileName -> fileName.split("_")[0])
                    .forEach(category -> recordMap.put(category, new ArrayList<>()));
        }
    }

    private void loadRecords(File inFilePath) {
        File[] files = inFilePath.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.getName().contains(".txt") || file.getName().equals("license.txt")) {
                    continue;
                }
                System.out.println("Processing " + file.getName());
                String fileContent = null;
                String fileCategory = file.getName().split("_")[0];
                try (Scanner scanner = new Scanner(file)) {
                    StringBuilder sb = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        line = line.trim();
                        line = line.replace("\"", "");
                        sb.append(line);
                        sb.append(' ');
                    }
                    fileContent = sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (fileContent != null) {
                    Record record = new Record(fileCategory, fileContent);
                    recordMap.get(fileCategory).add(record);
                }
            }
        } else {
            System.err.println("There was a problem with file: " + inFilePath.getName());
        }
    }

    public void divideRecordsBetweenSets() {
        for (List<Record> recordList : recordMap.values()) {
            int trainSetRecords = (int) (trainRatio * recordList.size());
            int testSetRecords = recordList.size() - trainSetRecords;

            for (int i = 0; i < trainSetRecords; i++) {
                outTrainList.add(recordList.remove(0));
            }
            for (int i = 0; i < testSetRecords; i++) {
                outTestList.add(recordList.remove(0));
            }
        }
    }


    private void prepareHeaders(File outFilePath) {
        try (FileWriter fw = new FileWriter(outFilePath, true)) {
            StringBuilder sb = new StringBuilder();
            String classes = String.join(", ", new ArrayList<>(recordMap.keySet()));
            sb.append("@relation set")
                    .append(timestamp)
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

    private void prepareDataContent(File outFilePath, List<Record> outList) {
        try (FileWriter fw = new FileWriter(outFilePath, true)) {
            for (Record record : outList) {
                StringBuilder sb = new StringBuilder();
                sb.append('"')
                        .append(record.getArticleContent())
                        .append('"')
                        .append(',')
                        .append(' ')
                        .append(record.getClassName())
                        .append('\n');
                String line = sb.toString();
                fw.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
