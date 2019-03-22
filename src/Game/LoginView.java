package Game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;


public class LoginView extends View {

    @Override
    public void update()
    {

    }

    @FXML
    Label usernameLabel, passwordLabel, gameLabel;
    TextField usernameTextField, addressTextField;
    Button playButton;


    public void clickPlay(ActionEvent actionEvent) throws Exception{
        System.out.println("Play Button Clicked!");
        new GameView(actionEvent, new GameModel(new ArrayList<Player>()));
    }
}
