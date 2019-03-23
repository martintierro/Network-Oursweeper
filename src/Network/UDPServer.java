package Network;

import java.io.IOException;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Scanner;

public class UDPServer {
    //private ServerController serverController;

    /*public UDPServer() {
        serverController = new ServerController();
    }*/

    public static void main(String args[]) throws Exception
    {
        ServerController serverController = new ServerController();
        serverController.setPlayerNum();

        while (true) {
            System.out.println("IN");
            serverController.receiveState();
            System.out.println("IN2");
            serverController.sendPacketString();
            System.out.println("IN3");
        }

        //DatagramSocket serverSocket = new DatagramSocket(1234);

        /*DatagramSocket serverSocket = serverController.getServerSocket();

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        while(true)
        {
            System.out.println("IN");
            DatagramPacket receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            String sentence = new String(receivePacket.getData());

            InetAddress IPAddress = receivePacket.getAddress();

            int port = receivePacket.getPort();

            String capitalizedSentence = sentence.toUpperCase();

            sendData = capitalizedSentence.getBytes();

            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, port);

            System.out.println("FROM CLIENT: received");

            serverSocket.send(sendPacket);
        }*/
    }
}