package Network;

import Game.GameState;

import java.io.*;
import java.net.*;

public class UDPClient implements Serializable{
    private DatagramSocket clientSocket;
    private InetAddress serverIPAddress;
    private int serverPort;

    public UDPClient(InetAddress serverIPAddress) {
        this.serverIPAddress = serverIPAddress;
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        serverPort  = 1234;
    }

    public void sendPacket(Object o) throws Exception{
        //clientSocket.setSoTimeout(30000);
        boolean sent = false;
        byte[] sendData = new byte[1024];
        if (o instanceof String)
            sendData = ((String) o).getBytes();
        else
            sendData = Blob.toStream(o);
        DatagramPacket datagramPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, serverPort );
        while(!sent) {
            clientSocket.send(datagramPacket);
            sent = checkAcknowledgement();
        }
        System.out.println("Sent Packet");
    }

    public Object receivePacket() throws Exception{
        System.out.println("in receive packet");

        byte[] receiveData = new byte[1048576];
        DatagramPacket receivePacket = new DatagramPacket (receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        sendAcknowledgement();
        return Blob.toObject(receivePacket.getData());
    }

    public String receiveString() throws Exception{
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket (receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        serverPort =  receivePacket.getPort();
        sendAcknowledgement();
        return new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
    }

    public boolean checkAcknowledgement(){
        byte[] receiveData = new byte[1024];
        try{
            DatagramPacket checkReceivePacket =
                    new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(checkReceivePacket);
            String returnMessage = new String(checkReceivePacket.getData()).trim();
            int returnNum = Integer.parseInt(returnMessage);
            if (returnNum == 1)
                return true;
        } catch (SocketTimeoutException e) {
            System.out.println ("Packet not received");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sendAcknowledgement(){
        byte[] sendData = new byte[1024];

        String capitalizedSentence = new String("1");
        sendData = capitalizedSentence.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, serverIPAddress, serverPort);

        try {
            clientSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
