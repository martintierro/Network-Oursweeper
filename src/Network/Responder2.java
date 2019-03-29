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
    private boolean timedOut;

    public Responder2(DatagramSocket socket, byte[] data, ArrayList<InetAddress> IPAddresses, HashMap<InetAddress, Integer> port) {
        this.socket = socket;
        try {
            socket.setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
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

            timedOut = true;

            while (timedOut) {
                try {
                    socket.send(sendPacket);
                    checkReceived();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void checkReceived() {
        byte[] receiveData = new byte[1024];
        try{
            DatagramPacket checkReceivePacket =
                    new DatagramPacket(receiveData, receiveData.length);
            socket.receive(checkReceivePacket);
            String returnMessage = new String(checkReceivePacket.getData()).trim();
            int returnNum = Integer.parseInt(returnMessage);

            if (returnNum == 1)
                timedOut = false;

        } catch (SocketTimeoutException e) {
            System.out.println ("Packet not received");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}