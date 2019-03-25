package Game;

import Network.ClientController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.util.concurrent.TimeUnit;

public class GameView extends View
{
    private @FXML AnchorPane anchorPane;
    private @FXML BorderPane borderPane;
    private @FXML Label playerLabel;
    private @FXML Label winnerLabel;
    private boolean hasShownGameOver;


    private Image dirt = new Image("/Pictures/dirt.png", 50, 50, false, false);
    private Image bomb = new Image("/Pictures/bomb.JPG", 50, 50, false, false);
    private Image dirtHole = new Image("/Pictures/dirt hole.png", 50, 50, false, false);

    private GameModel gameModel;
    private ClientController clientController;

    private TilePane tilePane;

    private Stage currentStage;
    private Player player;
    private @FXML Label aliveLabel, gameoverLabel, currentPlayerLabel;

    @Override
    public void update()
    {
        Platform.runLater( () -> {
            aliveLabel.setText("");
            System.out.println("Current Player: " + gameModel.getCurrentPlayer().getName());
            currentPlayerLabel.setText("Current Player: " + gameModel.getCurrentPlayer().getName());
            winnerLabel.setVisible(false);
            int numOfPlayers = 0;
            boolean currentPlayerAlive = true;
            hasShownGameOver = false;
            String aliveTemp = "Alive: ";
            for (Player player : gameModel.getPlayers()) {
                if (player.isAlive()) {
                    aliveTemp = aliveTemp + "  " + player.getName();
                    numOfPlayers++;
                }


                if (player.getName().equals(this.player.getName())) {
                    if (!gameModel.getCurrentPlayer().isAlive()) {
                        currentPlayerAlive = false;
                        for (int i = 0; i < gameModel.getField().getTiles().size(); i++) {
                            if (gameModel.getField().getTile(i).isSweep()) {
                                if (gameModel.getField().getTile(i).isBomb()) {
                                    BackgroundImage backgroundImage = new BackgroundImage(bomb, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                                    Background background = new Background(backgroundImage);
                                    Button button = (Button) tilePane.getChildren().get(i);
                                    button.setBackground(background);
                                } else {
                                    BackgroundImage backgroundImage = new BackgroundImage(dirtHole, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                                    Background background = new Background(backgroundImage);
                                    Button button = (Button) tilePane.getChildren().get(i);
                                    button.setBackground(background);
                                }
                            }
                        }



                    }
                    else {
                        for (int i = 0; i < gameModel.getField().getTiles().size(); i++) {
                            if (gameModel.getField().getTile(i).isSweep()) {
                                if (gameModel.getField().getTile(i).isBomb()) {
                                    BackgroundImage backgroundImage = new BackgroundImage(bomb, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                                    Background background = new Background(backgroundImage);
                                    Button button = (Button) tilePane.getChildren().get(i);
                                    button.setBackground(background);
                                } else {
                                    BackgroundImage backgroundImage = new BackgroundImage(dirtHole, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                                    Background background = new Background(backgroundImage);
                                    Button button = (Button) tilePane.getChildren().get(i);
                                    button.setBackground(background);
                                }
                            }
                        }
                    }
                }
            }
            if (!player.isAlive())
            {
                /*if (!hasShownGameOver)
                {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }*/

                hasShownGameOver = true;
                gameoverLabel.setVisible(true);
            }
            if (gameModel.isOver() && player.isAlive()) {
                winnerLabel.setVisible(true);
            }

            aliveLabel.setText(aliveTemp);
        });

    }


    GameView(ActionEvent actionEvent, GameModel gameModel, Player player, ClientController clientController) throws IOException {
        this.player = player;
        this.gameModel = gameModel;
        this.clientController = clientController;
        Thread clientThread = new Thread(clientController);
        clientThread.setDaemon(true);
        clientThread.start();
        gameModel.attach(this);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Game/field.fxml"));
        fxmlLoader.setController(this);
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        this.currentStage = window;
        window.setScene(scene);
        window.show();
        tilePane = new TilePane();
        tilePane.setVgap(1.0); tilePane.setHgap(1.0);
        for(int i = 0; i < 100; i++){
            Button tempButton = createButtonTile(dirt);
            //tempButton.setId(Integer.toString(i));
            tilePane.getChildren().add(tempButton);
            int num = i;
            tempButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (player.getName().equals(gameModel.getCurrentPlayer().getName())) {
                                clientController.sweepNextTile(num);
                            }
                        }
                    });
        }

        borderPane.setCenter(tilePane);
        //playerLabel = new Label();
        playerLabel.setText(player.getName());
        System.out.println(player.getName());
        currentPlayerLabel.setText("Current Player: " + gameModel.getCurrentPlayer().getName());

    }


    private Button createButtonTile (Image imageButton){

        BackgroundImage backgroundImage = new BackgroundImage(imageButton, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
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


        return button;
    }


}
