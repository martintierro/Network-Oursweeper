package Network;

import Game.GameController;
import Game.GameModel;
import Game.GameState;
import Game.Player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerController implements Serializable{

    private byte[] data;
    private ByteArrayOutputStream BAOS;
    private ObjectOutputStream OOS;

    private GameController GController;
    private GameModel GModel;
    private GameState GState;

    public ServerController(ArrayList<Player> players) {
        this.GModel = new GameModel(players);
        this.GState = new GameState(this.GModel);
        this.GController = new GameController(this.GModel);
    }

    /*public void startGame() {

    }*/

    public byte[] convertObjectToByte (Object object) {
        try {
            BAOS = new ByteArrayOutputStream(6400);
            OOS = new ObjectOutputStream(BAOS);
            OOS.writeObject(object);
            //data = BAOS.toByteArray();
            return BAOS.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateAllClients() {

    }

    public GameModel getGameModel() {
        return GModel;
    }

    public GameState getGameState() {
        return GState;
    }

    public byte[] getNextState() {
        //GController.sweepNextTile();
        return data;
    }

    /*public ArrayList<InetAddress> getIPAddresses() {
        return IPAddresses;
    }

    public DatagramSocket getServerSocket() {
        return serverSocket;
    }*/
}
