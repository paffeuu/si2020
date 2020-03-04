package si.lista1.utils;

import si.lista1.model.DataFile;
import si.lista1.model.EuclidesCoordinates;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataLoader {
    private File[] files;

    public DataLoader(String[] fileNames) {
        files = new File[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            files[i] = new File(fileNames[i]);
        }
    }

    public List<DataFile> load() {
        List<DataFile> dataFiles = new ArrayList<>();
        for (File file : files) {
            List<String> fileContent = loadFile(file);
            DataFile dataFile = parseFile(fileContent);
            dataFiles.add(dataFile);
        }
        return dataFiles;
    }

    private List<String> loadFile(File file) {
        List<String> textContent = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                textContent.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return textContent;
    }

    private DataFile parseFile(List<String> fileContent) {
        String name = fileContent.get(0).split("NAME: ")[1].trim();
        String type = fileContent.get(1).split("TYPE: ")[1].trim();
        String comment = fileContent.get(2).split("COMMENT: ")[1].trim();
        String dimension = fileContent.get(3).split("DIMENSION: ")[1].trim();
        String edgeWeightType = fileContent.get(4).split("EDGE_WEIGHT_TYPE: ")[1].trim();
        String displayDataType = null;
        int firstDataRow = 6;
        if (edgeWeightType.equals("GEO")) {
            displayDataType = fileContent.get(5).split("DISPLAY_DATA_TYPE: ")[1].trim();
            firstDataRow++;
        }
        List<EuclidesCoordinates> data = new ArrayList<>();
        for (int i = firstDataRow; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals("EOF")) {
                break;
            }
            String[] splitValues = fileContent.get(i).trim().split(" ");
            Integer index = null;
            Double x = null;
            Double y = null;
            for (String splitValue : splitValues) {
                if (!splitValue.isEmpty() && index == null) {
                    index = Integer.parseInt(splitValue);
                    continue;
                }
                if (!splitValue.isEmpty() && x == null) {
                    x = Double.parseDouble(splitValue);
                    continue;
                }
                if (!splitValue.isEmpty() && y == null) {
                    y = Double.parseDouble(splitValue);
                }
            }
            EuclidesCoordinates coordinates = new EuclidesCoordinates(x, y);
            data.add(coordinates);
        }
        return new DataFile(name, type, comment, dimension, edgeWeightType, displayDataType, data);
    }

}
