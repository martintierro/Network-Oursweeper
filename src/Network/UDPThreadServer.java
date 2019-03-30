package Network;

import Game.GameModel;
import Game.GameState;
import Game.Player;
import Game.Tile;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
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
    private boolean timedOut;
    //private byte[] objectToData;

    public ServerController serverController;

    public UDPThreadServer() {
        serverController = null;
        playerNum = 0;
        IPAddresses = new ArrayList<>();
        Players = new ArrayList<>();
        try {
            serverSocket = new DatagramSocket(1234);
            serverSocket.setSoTimeout(200000000);
        } catch (SocketException e) {
            System.out.println ("Connection Timed Out");
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
        System.out.println("In RUN");
        //ServerController serverController = new ServerController(getPlayers());
        while (!serverController.getGameModel().isOver()) {
            byte[] receiveData = new byte[1024];
            receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);
            try {
                System.out.println("TRYING TO RECEIVE");
                serverSocket.receive(receivePacket);
                System.out.println("Tile Received");
                sendAcknowledgement(receivePacket.getAddress());
            } catch (SocketTimeoutException e) {
                System.out.println ("Packet not received");
                try {
                    serverSocket = new DatagramSocket(1234);
                } catch (SocketException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            String stringTile = new String(receivePacket.getData()).trim();
            int tile = Integer.parseInt(stringTile);
            //intTile = (Integer) Blob.toObject(receivePacket.getData());
            //System.out.println("Tile index: " + tile);
            //System.out.println("Tile Clicked");

            GameState GS = serverController.getNextState(tile);

            //new Thread(new Responder(serverSocket, serverController, getIPAddresses(), GS, port)).start();
            Responder r = new Responder(serverSocket, serverController, getIPAddresses(), GS, port);
            r.run();
            //System.out.println("Sent Tile");
            //System.out.println ("Is Game over: " + serverController.getGameModel().isOver());

        }
    }

    public void run2(UDPThreadServer server) {
        System.out.println("In RUN2");
        serverController = new ServerController(server.getPlayers());
        Blob blob = new Blob();
        byte[] objectToData = blob.toStream(serverController.getGameModel());
        GameModel gm = (GameModel) blob.toObject(objectToData);

        //new Thread(new Responder2(serverSocket, objectToData, getIPAddresses(), port)).start();
        Responder2 r2 = new Responder2(serverSocket, objectToData, getIPAddresses(), port);
        r2.run();
        System.out.println("Made Thread");
        setServerController(serverController);

        //System.out.println (Thread.currentThread().getState());
        /*if (Thread.currentThread().getState() == State.RUNNABLE)
            Thread.currentThread().stop();*/

        /*while (!serverController.getGameModel().isOver())
            start();*/
    }

    public void checkAcknowledgement() {
        byte[] receiveData = new byte[1024];
         try{
             DatagramPacket checkReceivePacket =
                     new DatagramPacket(receiveData, receiveData.length);
             serverSocket.receive(checkReceivePacket);
             String returnMessage = new String(checkReceivePacket.getData()).trim();
             int returnNum = Integer.parseInt(returnMessage);

             if (returnNum == 1)
                 timedOut = false;

         } catch (SocketTimeoutException e) {
            System.out.println ("Packet not received");
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

    public void setServerController (ServerController serverController) {
        this.serverController = serverController;
    }

    public void setTimedOutToTrue() {
        this.timedOut = true;
    }

    public boolean getTimedOut() {
        return timedOut;
    }

    public InetAddress getIPAddress() {
        return IPAddress;
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
            server.sendAcknowledgement(server.getIPAddress());

            /*server.setTimedOutToTrue();
            while (server.getTimedOut()) {
                server.sendPacketConnection();
                server.checkAcknowledgement();
            }*/
            System.out.println("Num of players: " + server.getPlayers().size());
        }

        server.run2(server);


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
