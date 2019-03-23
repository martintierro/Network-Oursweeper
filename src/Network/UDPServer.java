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
    private byte[] objectToData;
    //private ArrayList<Player> players;

    //private ServerController serverController;

    public UDPServer() {
        //serverController = new ServerController();
        playerNum = 0;
        IPAddresses = new ArrayList<>();
        try {
            serverSocket = new DatagramSocket(1234);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void receiveState() throws Exception {
        System.out.println("IN");
        byte[] receiveData = new byte[1024];

        receivePacket =
                new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        String sentence = new String(receivePacket.getData());

        IPAddress = receivePacket.getAddress();
        port = receivePacket.getPort();

        IPAddresses.add(IPAddress);
        //System.out.println("FROM CLIENT: received");
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

    public void sendPacket() throws Exception {

        byte[] sendData = new byte[1024];

        while(true) {
            sendData = objectToData;

            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, port);

            //System.out.println("FROM CLIENT: " + sentence);
            System.out.println("FROM CLIENT: received");

            serverSocket.send(sendPacket);
        }
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

        server.setPlayerNum();
        int counter = server.getPlayerNum();

        while (counter > 0) {
            server.receiveState();
            server.sendPacketString();
            counter--;
        }
    }
}