package Network;

import Game.GameModel;
import Game.GameState;
import Game.Player;
import Game.Tile;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UDPThreadServer extends Thread{
    private InetAddress IPAddress;
    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    //private int port;
    private int playerNum;
    //private int intTile;

    private ArrayList<InetAddress> IPAddresses;
    private ArrayList<Player> Players;
    private HashMap<InetAddress, Integer> port;
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
        port = new HashMap<>();
        //intTile = 0;
    }

    public void receiveStateConnection() throws Exception {
        byte[] receiveData = new byte[1024];

        receivePacket =
                new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        String player = new String(receivePacket.getData()).trim();

        IPAddress = receivePacket.getAddress();
        port.put(IPAddress, receivePacket.getPort());


        if (!IPAddresses.contains(IPAddress)) {
            IPAddresses.add(IPAddress);
            Players.add(new Player(player));
        }
        System.out.println("FROM CLIENT: received");
    }

    public void sendPacketConnection() throws Exception {
        byte[] sendData = new byte[1024];

        String capitalizedSentence = new String(receivePacket.getData());
        sendData = capitalizedSentence.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, port.get(IPAddress));

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
        while (!serverController.getGameModel().isOver()) {
            byte[] receiveData = new byte[1024];
            System.out.println("Tile Received");
            receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String stringTile = new String(receivePacket.getData()).trim();
            int tile = Integer.parseInt(stringTile);
            //intTile = (Integer) Blob.toObject(receivePacket.getData());
            System.out.println("Tile index: " + tile);
            //System.out.println("Tile Clicked");

            GameState GS = serverController.getNextState(tile);

            new Thread(new Responder(serverSocket, serverController, getIPAddresses(), GS, port)).start();
            System.out.println("Sent Tile");
            System.out.println ("Is Game over: " + serverController.getGameModel().isOver());

        }
    }

    public void run2(UDPThreadServer server) {
        serverController = new ServerController(server.getPlayers());
        Blob blob = new Blob();
        byte[] objectToData = blob.toStream(serverController.getGameModel());
        GameModel gm = (GameModel) blob.toObject(objectToData);

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
            System.out.println("Num of players: " + server.getPlayers().size());
        }

        server.run2(server);
        server.stop();

        //while (!server.getServerController().getGameModel().isOver())
            server.run();

        /*while (!serverController.getGameModel().isOver()) {
            for (InetAddress IPAddress: server.getIPAddresses()) {
                objectToData = blob.toStream(serverController.getNextState(server.receiveState()));
                server.sendPacket(objectToData, IPAddress);
            }
        }*/
    }
}
