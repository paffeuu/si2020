package si.lista3.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import si.lista3.algorithm.MinMaxAlgorithm;
import si.lista3.engine.Connect4Engine;

public class GameStageController {

    private final static int RECTANGLE_SIZE = 25;
    private final static int ROWS = Connect4Engine.ROWS;
    private final static int COLS = Connect4Engine.COLS;


    private MinMaxAlgorithm minMaxAlgorithm;
    private int player = 1;

    private int[] columnPointers;

    private FieldCircle[][] fields;

    @FXML
    GridPane fieldsGridPane;

    @FXML
    Button newGameButton;
    @FXML
    Button exitButton;

    @FXML
    Button nextMoveButton;

    private Connect4Engine engine;

    public void initialize() {
        fields = new FieldCircle[ROWS][COLS];

        for (int y = 0; y < ROWS; y++)
            for (int x = 0; x < COLS; x++)
            {
                fields[y][x] = new FieldCircle(Paint.valueOf("grey"));
                fieldsGridPane.add(fields[y][x], x, y);
                int currX = x;
//                fields[y][x].setOnMouseClicked(event -> onColumnClicked(currX));
            }


        engine = new Connect4Engine();
        minMaxAlgorithm = new MinMaxAlgorithm();
        columnPointers = engine.getColumnPointers();


        // event handling
        newGameButton.setOnAction(event -> {
            botGame();
        });

        exitButton.setOnAction(event -> ((Stage)((Node)(event.getSource())).getScene().getWindow()).close());
    }

    private void onColumnClicked(int col) {
        int pointer = columnPointers[col];
        FieldCircle fieldCircle = fields[pointer][col];
        if (player == 1) {
            fieldCircle.setPlayerX();
            player = 2;
        } else if (player == 2) {
            fieldCircle.setPlayerY();
            player = 1;
        }
        columnPointers[col] = columnPointers[col] - 1;
    }

    private void botGame() {
        engine = new Connect4Engine();
        engine.nextMove(4,1);
        onColumnClicked(4);
        engine.nextMove(3,2);
        onColumnClicked(3);
        engine.nextMove(5,1);
        onColumnClicked(5);
        engine.nextMove(5,2);
        onColumnClicked(5);
        engine.printStage();

        nextMoveButton.setOnAction(
                event -> {
                    int nextMove = this.minMaxAlgorithm.getBestMove(engine, 5, player);
                    engine.nextMove(nextMove, player);
                    System.out.println(nextMove);
                    onColumnClicked(nextMove);
                    engine.printStage();
                }
        );

//        minMaxAlgorithm = new MinMaxAlgorithm();
//        for (int i = 0; i < 100; i++) {
//            player = 1 + i % 2;
//            int nextMove = minMaxAlgorithm.getBestMove(engine, 5, player);
//            engine.nextMove(nextMove, player);
//            onColumnClicked(nextMove);
//            engine.printStage();
//            if (engine.isFinished()) {
//                break;
//            }
//        }
    }

    private class FieldCircle extends Circle
    {

        private FieldCircle(Paint paint)
        {
            setRadius(RECTANGLE_SIZE);
            setFill(paint);
        }

        private void setPlayerX()
        {
            setFill(Paint.valueOf("red"));
            setDisable(true);
            setDisabled(true);
        }

        private void setPlayerY()
        {
            setFill(Paint.valueOf("green"));
            setDisable(true);
            setDisabled(true);
        }
    }
}
