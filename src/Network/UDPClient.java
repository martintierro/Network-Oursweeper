package Network;

import Game.GameState;

import java.io.*;
import java.net.*;

public class UDPClient {
    private DatagramSocket clientSocket;
    private InetAddress serverIPAddress;

    public UDPClient(InetAddress serverIPAddress) {
        this.serverIPAddress = serverIPAddress;
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(Integer i) throws Exception{
        byte[] sendData = new byte[1024];
        sendData[0] = i.byteValue();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, 9876);
        clientSocket.send(sendPacket);
    }

    public GameState receiveState() throws Exception{
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket (receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        //TODO return game state
        return null;
    }


    public static void main(String args[]) throws Exception {
        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));

        DatagramSocket clientSocket = new DatagramSocket();

        //InetAddress IPAddress = InetAddress.getByName("localhost");
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        String sentence = inFromUser.readLine();
        sendData = sentence.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, 4321);

        clientSocket.send(sendPacket);

        DatagramPacket receivePacket =
                new DatagramPacket (receiveData, receiveData.length);

        clientSocket.receive(receivePacket);

        String modifiedSentence = new String (receivePacket.getData());

        System.out.println("FROM SERVER: " + modifiedSentence);
        clientSocket.close();
    }
}
