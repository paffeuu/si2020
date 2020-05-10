package si.lista4.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataProcessor {
    private String inFilePathStr;
    private String outFilePathStr;
    private long id = System.currentTimeMillis();
    private Set<String> categorySet;

    public DataProcessor(String inFilePathStr) throws IOException {
        this.inFilePathStr = inFilePathStr;
        this.outFilePathStr = "data//weka//out" + id + ".arff";
    }

    public void processData() {
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
                if (!file.getName().contains(".txt")) {
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
                    try (FileWriter fw = new FileWriter(new File(outFilePathStr), true)) {
                        fw.append(fileContent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void prepareHeaders() {
        try (FileWriter fw = new FileWriter(new File(outFilePathStr), true)) {
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
