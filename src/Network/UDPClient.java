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

    public void sendPacket(Object o) throws Exception{
        byte[] sendData = Blob.toStream(o);
        DatagramPacket datagramPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, 1234 );
        clientSocket.send(datagramPacket);
    }

    public Object receivePacket() throws Exception{
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket (receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        return Blob.toObject(receivePacket.getData());
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
                new DatagramPacket(sendData, sendData.length, IPAddress, 1234);

        clientSocket.send(sendPacket);

        DatagramPacket receivePacket =
                new DatagramPacket (receiveData, receiveData.length);

        clientSocket.receive(receivePacket);

        String modifiedSentence = new String (receivePacket.getData());

        System.out.println("FROM SERVER: " + modifiedSentence);
        clientSocket.close();
    }
}
