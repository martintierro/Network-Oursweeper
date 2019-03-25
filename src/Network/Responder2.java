package Network;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.util.ArrayList;

public class Responder2 implements Runnable, Serializable {

    private DatagramSocket socket;
    private byte[] data;
    private ArrayList<InetAddress> IPAddresses;
    private int port;

    public Responder2(DatagramSocket socket, byte[] data, ArrayList<InetAddress> IPAddresses, int port) {
        this.socket = socket;
        this.data = data;
        this.IPAddresses = IPAddresses;
        this.port = port;
    }

    public void run() {
        for (InetAddress IPAddress : IPAddresses) {
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