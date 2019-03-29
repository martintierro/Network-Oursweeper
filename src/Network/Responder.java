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
    private boolean timedOut;

    public Responder(DatagramSocket socket, ServerController serverController, ArrayList<InetAddress> IPAddresses, GameState GS, HashMap<InetAddress, Integer> port) {
        this.socket = socket;
        try {
            socket.setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
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

            timedOut = true;

            while (timedOut) {
                try {
                    socket.send(sendPacket);
                    checkAcknowledgement();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void checkAcknowledgement() {
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
