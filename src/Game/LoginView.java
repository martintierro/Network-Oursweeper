package Game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;

public class LoginView extends View {

    @Override
    public void update() {

    }

    @FXML
    Label usernameLabel, passwordLabel, gameLabel;
    TextField usernameTextField, addressTextField;
    Button playButton;


    public void clickPlay(javafx.event.ActionEvent actionEvent) throws Exception{
        System.out.println("Play Button Clicked!");
    }
}
