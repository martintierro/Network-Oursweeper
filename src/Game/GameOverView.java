package Game;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverView extends View {

    public GameOverView(Stage primaryStage){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Game/GameOver.fxml"));
        fxmlLoader.setController(this);
        Parent parent = null;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        Stage window = primaryStage;
        window.setScene(scene);
        window.show();

    }

    @Override
    public void update() {

    }
}
