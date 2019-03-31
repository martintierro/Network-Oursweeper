package Network;

import Game.GameModel;
import Game.GameState;
import Game.Player;
import Game.Tile;

import java.io.IOException;
import java.net.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UDPServer {

    private InetAddress IPAddress;
    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;
    private int playerNum;

    private ArrayList<InetAddress> IPAddresses;
    private ArrayList<Player> Players;
    private HashMap<InetAddress, Integer> port;
    private boolean timedOut;

    private ServerController serverController;

    public UDPServer() {
        serverController = null;
        playerNum = 0;
        IPAddresses = new ArrayList<>();
        Players = new ArrayList<>();
        port = new HashMap<>();

        try {
            serverSocket = new DatagramSocket(1234);
            //serverSocket.setSoTimeout(100000);
        } catch (SocketException e) {
            System.out.println ("Connection Timed Out");
        }

    }

    public void receiveStateConnection() throws Exception, SocketException {
        byte[] receiveData = new byte[1024];

        receivePacket =
                new DatagramPacket(receiveData, receiveData.length);

        serverSocket.receive(receivePacket);
        String player = new String(receivePacket.getData());

        IPAddress = receivePacket.getAddress();
        port.put(IPAddress, receivePacket.getPort());

        if (!IPAddresses.contains(IPAddress)) {
            IPAddresses.add(IPAddress);
            Players.add(new Player(player));
        }

        System.out.println ("Port: " + port);
    }

    public void sendPacketConnection() throws Exception {
        byte[] sendData = new byte[1024];

        String capitalizedSentence = new String(receivePacket.getData());
        sendData = capitalizedSentence.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, port.get(IPAddress));

        System.out.println("FROM CLIENT: received");
        serverSocket.send(sendPacket);
    }

    public void sendGameModel(UDPServer server) throws Exception {
        System.out.println ("Sending Game Model");

        serverController = new ServerController(server.getPlayers());
        byte[] objectToData = Blob.toStream(serverController.getGameModel());
        //GameModel GM = (GameModel) blob.toObject(objectToData);

        for(InetAddress IPAddress: IPAddresses) {
            byte[] sendData = new byte[1024];
            sendData = objectToData;

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port.get(IPAddress));

            timedOut = true;
            while(timedOut) {
                try{
                    serverSocket.send(sendPacket);
                    checkAcknowledgement();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Sent Game Model");
        }
    }

    public void receiveGameState() throws Exception{

        while (!serverController.getGameModel().isOver()) {
            System.out.println("Waiting for move");
            byte[] receiveData = new byte[1024];
            receivePacket = new DatagramPacket(receiveData, receiveData.length);


                serverSocket.receive(receivePacket);
                sendAcknowledgement(receivePacket.getAddress());
            System.out.println("Generating state");

            String sTile = new String(receivePacket.getData()).trim();
            int tile = Integer.parseInt(sTile);
            //Blob.toObject(receivePacket.getData());

            GameState GS = serverController.getNextState(tile);

            sendGameState(GS);
        }
    }

    public void sendGameState(GameState GS) throws Exception {
        System.out.println("Sending Game State");
        byte[] objectToData = Blob.toStream(GS);

        for (InetAddress IPAddress: IPAddresses) {
            byte[] sendData = new byte[1024];
            sendData = objectToData;

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port.get(IPAddress));

            timedOut = true;
            while(timedOut) {
                try{
                    serverSocket.send(sendPacket);
                    checkAcknowledgement();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Send Game State");
        }
    }

    /*public int receiveState() throws Exception {
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
    }*/

    /*public void sendPacket(byte[] object, InetAddress IPAddress) throws Exception {

        byte[] sendData = new byte[1024];

       // while(true) {
            sendData = object;

            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, port);

            //System.out.println("FROM CLIENT: " + sentence);
            //System.out.println("FROM CLIENT: received");

            serverSocket.send(sendPacket);
        //}
    }*/

    public void checkAcknowledgement() {
        System.out.println("Waiting for Acknowledgement");
        try {
            serverSocket.setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        byte[] receiveData = new byte[1024];
        try{
            DatagramPacket checkReceivePacket =
                    new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(checkReceivePacket);
            String returnMessage = new String(checkReceivePacket.getData()).trim();
            int returnNum = Integer.parseInt(returnMessage);

            if (returnNum == 1) {
                serverSocket.setSoTimeout(0);
                timedOut = false;
            }

        } catch (SocketTimeoutException e) {
            System.out.println ("Packet not received");
            try{
                //serverSocket = new DatagramSocket(1234);
                serverSocket.setSoTimeout(0);
            } catch (SocketException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Got Acknowledgement");
    }

    public void sendAcknowledgement(InetAddress IPAddress){
        byte[] sendData = new byte[1024];

        String capitalizedSentence = new String("1");
        sendData = capitalizedSentence.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, port.get(IPAddress));

        //System.out.println ("IPAddress: " + IPAddress);

        try {
            serverSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sent Acknowledgement");
    }

    public ServerController getServerController() {
        return serverController;
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

    public InetAddress getIPAddress() {
        return IPAddress;
    }

    public boolean getTimedOut() {
        return timedOut;
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

        while (counter > server.getIPAddresses().size()) {
            server.receiveStateConnection();
            server.sendAcknowledgement(server.getIPAddress());

            /*while(server.getTimedOut()) {
                server.sendPacketConnection();
                server.checkAcknowledgement();
            }*/

            System.out.println("Num of players: " + server.getPlayers().size());
        }

        server.sendGameModel(server);
        server.receiveGameState();

        /*ServerController serverController = new ServerController(server.getPlayers());
        objectToData = blob.toStream(serverController.getGameModel());

        for (InetAddress IPAddress: server.getIPAddresses()) {
            server.sendPacket(objectToData, IPAddress);
        }

        while (!serverController.getGameModel().isOver()) {
            for (InetAddress IPAddress: server.getIPAddresses()) {
                objectToData = blob.toStream(serverController.getNextState(server.receiveState()));
                server.sendPacket(objectToData, IPAddress);
            }
        }*/

    }
}