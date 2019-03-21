package Game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.naming.ldap.PagedResultsControl;
import java.io.IOException;

public class GameView extends View
{
    @Override
    public void update() {

    }

    @FXML
    AnchorPane anchorPane;

    @FXML
    BorderPane borderPane;

    private Image dirt = new Image("/Pictures/dirt.png", 50, 50, false, false);
    private Image bomb = new Image("/Pictures/bomb.JPG", 50, 50, false, false);
    private Image dirtHole = new Image("/Pictures/dirt hole.png", 50, 50, false, false);

    public GameView(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Game/field.fxml"));
        fxmlLoader.setController(this);
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        TilePane tilePane = new TilePane();
        tilePane.setVgap(1.0); tilePane.setHgap(1.0);
        for(int i = 0; i < 100; i++){

            BackgroundImage backgroundImage = new BackgroundImage(dirt, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);

            Button button = new Button();
            button.setBackground(background);

            button.setPrefSize(50.0, 50.0);

            DropShadow shadow = new DropShadow();

            button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            button.setEffect(shadow);
                        }
                    });

            button.addEventHandler(MouseEvent.MOUSE_EXITED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            button.setEffect(null);
                        }
                    });

            button.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            
                        }
                    });

            tilePane.getChildren().add(button);
        }

        borderPane.setCenter(tilePane);

    }

}
