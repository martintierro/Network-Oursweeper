package Game;

import Network.ClientController;
import Network.UDPClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;


public class LoginView extends View implements Serializable {
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
        this.players = new ArrayList<>();
    }

    @Override
    public void update()
    {

    }

    public boolean validIP (String ip) {
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public void clickPlay(ActionEvent actionEvent) throws Exception{
        if (validIP(addressTextField.getText()) || addressTextField.getText().equals("localhost")) {
            client = new UDPClient(InetAddress.getByName(addressTextField.getText()));
            LoginController loginController = new LoginController(client);
            loginController.setupGame(usernameTextField.getText());
            String name = usernameTextField.getText();
            GM = loginController.getModel();
            Player yourPlayer = null;
            for(Player player:GM.getPlayers()) {
                //System.out.println("Name1: " + player.getName() + "tt");
                if (player.getName().compareTo(name)== 0) {
                    System.out.println("Name2: " + player.getName());
                    yourPlayer = player;
                }
            }
            ClientController clientController = new ClientController(GM,client);
            new GameView(actionEvent,GM, yourPlayer,clientController);
        }
        else {
            System.out.println("Invalid IPAddress");
            addressTextField.setText("");
            addressTextField.setPromptText("Invalid IPAddress");
        }
    }
}
