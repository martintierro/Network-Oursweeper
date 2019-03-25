package Network;

import Game.GameState;
import Game.Tile;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Responder implements Runnable, Serializable {
    private DatagramSocket socket;
    private ServerController serverController;
    private ArrayList<InetAddress> IPAddresses;
    private GameState GS;
    private byte[] data;
    private Blob blob;
    private HashMap<InetAddress, Integer> port;

    public Responder(DatagramSocket socket, ServerController serverController, ArrayList<InetAddress> IPAddresses, GameState GS, HashMap<InetAddress, Integer> port) {
        this.socket = socket;
        this.serverController = serverController;
        this.IPAddresses = IPAddresses;
        this.GS = GS;
        this.data = null;
        this.blob = new Blob();
        this.port = port;
    }

    public void run() {
        data = blob.toStream(GS);

        for (InetAddress IPAddress: IPAddresses) {
            byte[] sendData = new byte[1024];
            sendData = data;

            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, port.get(IPAddress));
            try {
                socket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
