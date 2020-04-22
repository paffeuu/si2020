package si.lista3.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import si.lista3.algorithm.GameAlgorithm;
import si.lista3.algorithm.MinMaxAlgorithm;
import si.lista3.engine.Connect4Engine;
import si.lista3.gui.control.FieldCircle;

import java.util.Arrays;

public class GameStageController {

    private final static int RECTANGLE_SIZE = 25;
    private final static int ROWS = Connect4Engine.ROWS;
    private final static int COLS = Connect4Engine.COLS;


    private GameAlgorithm gameAlgorithm;
    // game modes; 0 - AI vs AI, 1 - Human vs AI
    private int gameMode = 0;

    private int[] columnPointers;

    private FieldCircle[][] fields;

    @FXML
    BorderPane mainBorderPane;

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

    @FXML
    VBox algorithmVBox;

    @FXML
    RadioButton minMaxRadioButton;
    @FXML
    RadioButton alfaBetaRadioButton;

    @FXML
    VBox minMaxDepthVBox;
    @FXML
    Slider minMaxDepthSlider;

    private Connect4Engine engine;

    public void initialize() {
        registerEventListeners();
        initializeChooseAlgorithmRadioButtonGroup();
        nextMoveButton.setDisable(true);
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
                    int nextMove = this.gameAlgorithm.getBestMove(engine, engine.getNextMovePlayer());
                    System.out.println("Depth: " + ((MinMaxAlgorithm) gameAlgorithm).getDepth());
                    if (engine.nextMove(nextMove, engine.getNextMovePlayer())) {
                        finishGame();
                        //TODO: does not work
                    }
                    onColumnClicked(nextMove);
                    engine.printStage();
                    if (gameMode == 1) {
                        nextMoveButton.setDisable(true);
                        setAllFieldsDisable(false);
                    }
                }
        );
        mainBorderPane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.N && !nextMoveButton.isDisable()) {
                nextMoveButton.fire();
            }
        });
        minMaxRadioButton.setOnAction(event -> {
            gameAlgorithm = new MinMaxAlgorithm();
            minMaxDepthVBox.setVisible(true);
        });
//        alfaBetaRadioButton.setOnAction(event -> {
//            gameAlgorithm = new AlfaBetaAlgorithm();
//            minMaxDepthVBox.setVisible(false);
//        });
        minMaxDepthSlider.valueProperty().addListener((observable, oldValue, newValue)
                -> ((MinMaxAlgorithm) gameAlgorithm).setDepth(newValue.intValue()));
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
                        if (columnPointers[currX] >= 0) {
                            if (engine.nextMove(currX, 1)) {
                                finishGame();
                            }
                            onColumnClicked(currX);
                            setAllFieldsDisable(true);
                            nextMoveButton.setDisable(false);
                        }
                    });
                }
            }
        }
    }

    private void initializeChooseAlgorithmRadioButtonGroup() {
        ToggleGroup group = new ToggleGroup();
        minMaxRadioButton.setToggleGroup(group);
        alfaBetaRadioButton.setToggleGroup(group);
        minMaxRadioButton.fire();
        alfaBetaRadioButton.setDisable(true);
    }

    private void startExampleBotGame() {
        initializeGameStage();

        engine = new Connect4Engine();
        columnPointers = engine.getColumnPointers();

//        engine.nextMove(4,1);
//        onColumnClicked(4);
//        engine.nextMove(3,2);
//        onColumnClicked(3);
//        engine.nextMove(5,1);
//        onColumnClicked(5);
//        engine.nextMove(5,2);
//        onColumnClicked(5);
        engine.printStage();
        nextMoveButton.setDisable(false);
    }

    private void startHumanWithBotGame() {
        initializeGameStage();

        engine = new Connect4Engine();
        columnPointers = engine.getColumnPointers();



    }

    private void finishGame() {
        nextMoveButton.setDisable(true);
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
                nextMoveButton.setDisable(true);
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
