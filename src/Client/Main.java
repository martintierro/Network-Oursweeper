package Client;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.setTitle("Bomb Sweeper");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Client/field.fxml"));

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
