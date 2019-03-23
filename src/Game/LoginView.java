package Game;

import Network.UDPClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.util.ArrayList;


public class LoginView extends View {
    @FXML
    Label usernameLabel, passwordLabel, gameLabel;
    @FXML
    TextField usernameTextField, addressTextField;
    @FXML
    Button playButton;

    private UDPClient client;
    private GameModel GM;
    private ArrayList<Player> players;

    public LoginView() {
        players = new ArrayList<>();
    }

    @Override
    public void update()
    {

    }

    public void clickPlay(ActionEvent actionEvent) throws Exception{
        //client = new UDPClient(InetAddress.getByName(addressTextField.getText()));
        //GM = new GameModel(players);
        //players.add(new Player(usernameTextField.getText()));
        //GM.setPlayers(players);

        //System.out.println ("Player ADDED: " + players.get(0).getName());
        System.out.println("Play Button Clicked!");
        new GameView(actionEvent, new GameModel(new ArrayList<Player>()));
    }
}
