package Network;

import Game.Player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class UDPServer {

    private InetAddress IPAddress;
    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    private int port;
    private int playerNum;

    private ArrayList<InetAddress> IPAddresses;
    private ArrayList<Player> Players;
    //private byte[] objectToData;

    //private ServerController serverController;

    public UDPServer() {
        //serverController = new ServerController();
        playerNum = 0;
        IPAddresses = new ArrayList<>();
        Players = new ArrayList<>();
        try {
            serverSocket = new DatagramSocket(1234);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void receiveStateConnection() throws Exception {
        byte[] receiveData = new byte[1024];

        receivePacket =
                new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        String player = new String(receivePacket.getData());

        IPAddress = receivePacket.getAddress();
        port = receivePacket.getPort();


        if (!IPAddresses.contains(IPAddress)) {
            IPAddresses.add(IPAddress);
            Players.add(new Player(player));
        }
        //System.out.println("FROM CLIENT: received");
        System.out.println ("Port: " + port);
    }

    public int receiveState() throws Exception {
        byte[] receiveData = new byte[1024];

        receivePacket =
                new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        String stringTile = new String(receivePacket.getData());
        int intTile = Integer.parseInt(stringTile);

        return intTile;
        //IPAddress = receivePacket.getAddress();
        //port = receivePacket.getPort();

        //System.out.println ("Port: " + port);
    }

    public void sendPacketConnection() throws Exception {
        byte[] sendData = new byte[1024];

        String capitalizedSentence = new String(receivePacket.getData());
        sendData = capitalizedSentence.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, port);

        System.out.println("FROM CLIENT: received");
        serverSocket.send(sendPacket);
    }

    public void sendPacket(byte[] object, InetAddress IPAddress) throws Exception {

        byte[] sendData = new byte[1024];

       // while(true) {
            sendData = object;

            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, port);

            //System.out.println("FROM CLIENT: " + sentence);
            System.out.println("FROM CLIENT: received");

            serverSocket.send(sendPacket);
        //}
    }

    public ArrayList<InetAddress> getIPAddresses() {
        return IPAddresses;
    }

    public ArrayList<Player> getPlayers() {
        return Players;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public void setPlayerNum() {
        System.out.println ("Input no. of players: ");
        Scanner sc = new Scanner(System.in);
        playerNum = sc.nextInt();
        System.out.println ("Waiting for " + playerNum + " player(s) to join...");
    }

    public static void main(String args[]) throws Exception
    {
        UDPServer server = new UDPServer();
        byte[] objectToData;

        server.setPlayerNum();
        int counter = server.getPlayerNum();

        while (counter > server.getIPAddresses().size()) {
            server.receiveStateConnection();
            server.sendPacketConnection();
        }

        ServerController serverController = new ServerController(server.getPlayers());
        objectToData = serverController.convertObjectToByte(serverController.getGameModel());

        for (InetAddress IPAddress: server.getIPAddresses()) {
            server.sendPacket(objectToData, IPAddress);
        }

        while (!serverController.getGameModel().isOver()) {
            for (InetAddress IPAddress: server.getIPAddresses()) {
                objectToData = serverController.convertObjectToByte(serverController.getNextState(server.receiveState()));
                server.sendPacket(objectToData, IPAddress);
            }
        }

    }
}