package Network;

import Game.GameController;
import Game.GameModel;

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

public class ServerController {

    ByteArrayOutputStream BAOS;
    ObjectOutputStream OOS;
    //byte[] data;

    GameController GC;
    GameModel GM;

    ArrayList<InetAddress> IPAddresses;

    private InetAddress IPAddress;
    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    private int port;

    public ServerController() {
        //this.GC = gameController;
        //this.GM = gameModel;
        try {
            serverSocket = new DatagramSocket(4321);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.IPAddresses = new ArrayList<>();
    }

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

    public void setPlayerNum() {
        System.out.println ("Input no. of players: ");
        Scanner sc = new Scanner(System.in);
        int playerNum = sc.nextInt();
        System.out.println ("Waiting for " + playerNum + " player(s) to join...");
    }

    public void receiveState() throws Exception {
        byte[] receiveData = new byte[1024];
        receivePacket =
                new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        String sentence = new String(receivePacket.getData());

        IPAddress = receivePacket.getAddress();
        port = receivePacket.getPort();

        System.out.println("FROM CLIENT: received");
        System.out.println ("Port: " + port);
    }

    public void sendPacketString() throws Exception {
        byte[] sendData = new byte[1024];

        String capitalizedSentence = new String(receivePacket.getData());

        sendData = capitalizedSentence.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, port);

        System.out.println("FROM CLIENT: received");
        serverSocket.send(sendPacket);
    }

    public void sendPacket(Object object) throws Exception {

        byte[] sendData = new byte[1024];

        while(true) {
            sendData = convertObjectToByte(object);

            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, port);

            //System.out.println("FROM CLIENT: " + sentence);
            System.out.println("FROM CLIENT: received");

            serverSocket.send(sendPacket);
        }
    }

    public ArrayList<InetAddress> getIPAddresses() {
        return IPAddresses;
    }

    public DatagramSocket getServerSocket() {
        return serverSocket;
    }
}
