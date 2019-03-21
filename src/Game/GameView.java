package Game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import javax.naming.ldap.PagedResultsControl;
import java.io.IOException;

public class GameView extends View
{
    @FXML
    AnchorPane anchorPane;

        public GameView(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Game/field.fxml"));
        fxmlLoader.setController(this);
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        TilePane tilePane = new TilePane();
        for(int i = 0; i < 100; i++){
            Image dirt = new Image("/Pictures/grass.png");
            ImageView dirtTile = new ImageView(dirt);
            dirtTile.setFitWidth(30.0);
            dirtTile.setFitHeight(30.0);

            Button button = new Button();
            button.setGraphic(dirtTile);
            button.setPrefSize(50.0, 50.0);
            tilePane.getChildren().add(button);
        }

      //  Scene scene = new Scene(tilePane, 450,450);
        anchorPane.getChildren().add(tilePane);

    }

    @Override
    public void update() {

    }

}
