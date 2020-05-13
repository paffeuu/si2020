package si.lista4;

import si.lista4.utils.DataProcessor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DataProcessor processor = new DataProcessor("data//train", "data//test");
        processor.processData();

    }
}
