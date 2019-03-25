package Network;

import java.io.IOException;
import java.io.Serializable;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Responder2 implements Runnable, Serializable {

    private DatagramSocket socket;
    private byte[] data;
    private ArrayList<InetAddress> IPAddresses;
    private HashMap<InetAddress, Integer> port;

    public Responder2(DatagramSocket socket, byte[] data, ArrayList<InetAddress> IPAddresses, HashMap<InetAddress, Integer> port) {
        this.socket = socket;
        this.data = data;
        this.IPAddresses = IPAddresses;
        this.port = port;
    }

    public void run() {
        for (InetAddress IPAddress : IPAddresses) {
            byte[] sendData = new byte[1024];
            sendData = data;

            System.out.println("IPADDRESS: " + IPAddress);

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