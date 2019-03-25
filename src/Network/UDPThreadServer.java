package Network;

import Game.Player;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class UDPThreadServer extends Thread{
    private InetAddress IPAddress;
    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    private int port;
    private int playerNum;
    private int intTile;

    private ArrayList<InetAddress> IPAddresses;
    private ArrayList<Player> Players;
    //private byte[] objectToData;

    public ServerController serverController;

    public UDPThreadServer() {
        serverController = null;
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

    public void sendPacketConnection() throws Exception {
        byte[] sendData = new byte[1024];

        String capitalizedSentence = new String(receivePacket.getData());
        sendData = capitalizedSentence.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, port);

        System.out.println("FROM CLIENT: received");
        serverSocket.send(sendPacket);
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

    public void run() {
        //ServerController serverController = new ServerController(getPlayers());
        byte[] receiveData = new byte[1024];

        System.out.println (getServerController().getGameModel().getCurrentPlayer().getName());

        while (!getServerController().getGameModel().isOver()) {
            System.out.println("IN");
            receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String stringTile = new String(receivePacket.getData());
            intTile = Integer.parseInt(stringTile);
            port = receivePacket.getPort();

            new Thread(new Responder(serverSocket, serverController, getIPAddresses(), intTile, port)).start();
        }
    }

    public void run2(UDPThreadServer server) {
        serverController = new ServerController(server.getPlayers());
        Blob blob = new Blob();
        byte[] objectToData = blob.toStream(serverController.getGameModel());

        new Thread(new Responder2(serverSocket, objectToData, getIPAddresses(), port)).start();
        setServerController(serverController);

        System.out.println (Thread.currentThread().getState());
        /*if (Thread.currentThread().getState() == State.RUNNABLE)
            Thread.currentThread().stop();*/

        /*while (!serverController.getGameModel().isOver())
            start();*/
    }

    public ServerController getServerController() {
        return serverController;
    }

    public void setServerController (ServerController serverController) {
        this.serverController = serverController;
    }


    public static void main(String args[]) throws Exception
    {
        //Blob blob = new Blob();
        UDPThreadServer server = new UDPThreadServer();
        //byte[] objectToData;

        server.setPlayerNum();
        int counter = server.getPlayerNum();

        while (counter > server.getIPAddresses().size()) {
            server.receiveStateConnection();
            server.sendPacketConnection();
        }

        server.run2(server);
        server.stop();

        while (!server.getServerController().getGameModel().isOver())
            server.run();

        /*while (!serverController.getGameModel().isOver()) {
            for (InetAddress IPAddress: server.getIPAddresses()) {
                objectToData = blob.toStream(serverController.getNextState(server.receiveState()));
                server.sendPacket(objectToData, IPAddress);
            }
        }*/

    }


}
