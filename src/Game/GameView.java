package Game;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class GameView extends View{

    @Override
    public void update() {

    }

    /*
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

    Scene scene = new Scene(tilePane, 450,450);

    */
}
