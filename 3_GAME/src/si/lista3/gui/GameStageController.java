package si.lista3.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import si.lista3.algorithm.MinMaxAlgorithm;
import si.lista3.engine.Connect4Engine;
import si.lista3.gui.control.FieldCircle;

import java.util.Arrays;

public class GameStageController {

    private final static int RECTANGLE_SIZE = 25;
    private final static int ROWS = Connect4Engine.ROWS;
    private final static int COLS = Connect4Engine.COLS;


    private MinMaxAlgorithm minMaxAlgorithm;
    // game modes; 0 - AI vs AI, 1 - Human vs AI
    private int gameMode = 0;

    private int[] columnPointers;

    private FieldCircle[][] fields;

    @FXML
    GridPane fieldsGridPane;

    @FXML
    Label gameModeValue;

    @FXML
    Button changeGameModeButton;
    @FXML
    Button nextMoveButton;

    @FXML
    Button newGameButton;
    @FXML
    Button exitButton;

    private Connect4Engine engine;

    public void initialize() {
        minMaxAlgorithm = new MinMaxAlgorithm();
        registerEventListeners();
    }

    private void registerEventListeners() {
        exitButton.setOnAction(event -> ((Stage)((Node)(event.getSource())).getScene().getWindow()).close());
        newGameButton.setOnAction(event -> {
            switch (gameMode) {
                case 0:
                    startExampleBotGame();
                    break;
                case 1:
                    startHumanWithBotGame();
                    break;
            }
        });
        changeGameModeButton.setOnAction(event -> changeGameMode());
        nextMoveButton.setOnAction(
                event -> {
                    int nextMove = this.minMaxAlgorithm.getBestMove(engine, 5, engine.getNextMovePlayer());
                    if (engine.nextMove(nextMove, engine.getNextMovePlayer())) {
                        finishGame();
                    }
                    onColumnClicked(nextMove);
                    engine.printStage();
                    if (gameMode == 1) {
                        nextMoveButton.setDisable(true);
                        setAllFieldsDisable(false);
                    }
                }
        );
    }

    private void initializeGameStage() {
        if (fields != null) {
            for (int y = 0; y < ROWS; y++) {
                for (int x = 0; x < COLS; x++) {
                    fieldsGridPane.getChildren().remove(fields[y][x]);
                }
            }
        }
        fields = new FieldCircle[ROWS][COLS];
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                fields[y][x] = new FieldCircle(Paint.valueOf("grey"), RECTANGLE_SIZE);
                fieldsGridPane.add(fields[y][x], x, y);
                if (gameMode == 1) {
                    int currX = x;
                    fields[y][x].setOnMouseClicked(event -> {
                        if (engine.nextMove(currX, 1)) {
                            finishGame();
                        }
                        onColumnClicked(currX);
                        setAllFieldsDisable(true);
                        nextMoveButton.setDisable(false);
                    });
                }
            }
        }
    }

    private void startExampleBotGame() {
        initializeGameStage();

        engine = new Connect4Engine();
        columnPointers = engine.getColumnPointers();

        engine.nextMove(4,1);
        onColumnClicked(4);
        engine.nextMove(3,2);
        onColumnClicked(3);
        engine.nextMove(5,1);
        onColumnClicked(5);
        engine.nextMove(5,2);
        onColumnClicked(5);
        engine.printStage();
    }

    private void startHumanWithBotGame() {
        initializeGameStage();

        engine = new Connect4Engine();
        columnPointers = engine.getColumnPointers();



    }

    private void finishGame() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Koniec gry!");
        alert.setContentText("Gra zakończona");
        alert.show();
    }

    private void onColumnClicked(int col) {
        int pointer = columnPointers[col];
        FieldCircle fieldCircle = fields[pointer+1][col];
        int player = engine.getNextMovePlayer();
        if (player == 1) {
            fieldCircle.setPlayerX();
        } else if (player == 2) {
            fieldCircle.setPlayerY();
        }
    }

    private void changeGameMode() {
        gameMode++;
        gameMode %= 2;
        switch (gameMode) {
            case 0:
                gameModeValue.setText("Komputer vs Komputer");
                break;
            case 1:
                gameModeValue.setText("Człowiek vs Komputer");
                break;
        }
    }

    private void setAllFieldsDisable(boolean disable) {
        Arrays.stream(fields)
                .flatMap(Arrays::stream)
                .forEach(field -> field.setDisable(disable));
    }

}
