package si.lista3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    private static Stage gameStage;

    @Override
    public void start(Stage gameStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/game_stage.fxml"));
        gameStage = new Stage();
        gameStage.setScene(new Scene(root, 1000, 600));
        gameStage.setTitle("Connect4");
        gameStage.show();
    }

}
