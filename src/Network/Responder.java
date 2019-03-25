package Network;

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
    private int sweptTile;
    private byte[] data;
    private Blob blob;
    private HashMap<InetAddress, Integer> port;

    public Responder(DatagramSocket socket, ServerController serverController, ArrayList<InetAddress> IPAddresses, int sweptTile, HashMap<InetAddress, Integer> port) {
        this.socket = socket;
        this.serverController = serverController;
        this.IPAddresses = IPAddresses;
        this.sweptTile = sweptTile;
        this.data = null;
        this.blob = new Blob();
        this.port = port;
    }

    public void run() {
        data = blob.toStream(serverController.getNextState(sweptTile));

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
