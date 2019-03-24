package Game;

import Network.UDPClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
        this.players = new ArrayList<>();
    }

    @Override
    public void update()
    {

    }

    public void clickPlay(ActionEvent actionEvent) throws Exception{
        client = new UDPClient(InetAddress.getByName(addressTextField.getText()));
        LoginController loginController = new LoginController(client);
        loginController.setupGame(usernameTextField.getText());
        Thread t1 = new Thread(loginController);
        t1.start();
        while (loginController.getModel()==null){
            System.out.println("Connecting");
        }
        t1.stop();
        GM = loginController.getModel();

        //GM = new GameModel(players);
        //players.add(new Player(usernameTextField.getText()));
        //GM.setPlayers(players);

        //System.out.println ("Player ADDED: " + players.get(0).getName());
        /*System.out.println("Play Button Clicked!");
        client.sendPacket(usernameTextField.getText());
        System.out.println(client.receiveString());
        GameModel gameModel = null;
        gameModel = (GameModel) client.receivePacket();
        Player yourPlayer = null;
        for(Player player:gameModel.getPlayers())
            if(player.getName().equals(usernameTextField.getText()))
                yourPlayer = player;
        new GameView(actionEvent,gameModel,yourPlayer);

        /*BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));

        DatagramSocket clientSocket = new DatagramSocket();

        //InetAddress IPAddress = InetAddress.getByName("localhost");
        InetAddress IPAddress = InetAddress.getByName(addressTextField.getText());
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        //String sentence = inFromUser.readLine();
        String username = usernameTextField.getText();
        sendData = username.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, 1234);

        clientSocket.send(sendPacket);

        DatagramPacket receivePacket =
                new DatagramPacket (receiveData, receiveData.length);

        clientSocket.receive(receivePacket);

        String modifiedSentence = new String (receivePacket.getData());

        System.out.println(modifiedSentence + "is connected!");
        //System.out.println(" is connected!");
        clientSocket.close();*/
        //new GameView(actionEvent, new GameModel(new ArrayList<Player>()));
    }
}
