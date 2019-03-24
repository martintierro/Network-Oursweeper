package Network;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Responder implements Runnable{
    private DatagramSocket socket;
    private ServerController serverController;
    private ArrayList<InetAddress> IPAddresses;
    private int sweptTile;
    private byte[] data;
    private Blob blob;
    private int port;

    public Responder(DatagramSocket socket, ServerController serverController, ArrayList<InetAddress> IPAddresses, int sweptTile, int port) {
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
                    new DatagramPacket(sendData, sendData.length, IPAddress, port);
            try {
                socket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
