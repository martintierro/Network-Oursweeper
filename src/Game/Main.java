package Game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Bomb Sweeper");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Game/field.fxml"));

        fxmlLoader.setController(new GameView());
        Parent root = fxmlLoader.load();
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.sizeToScene();
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
