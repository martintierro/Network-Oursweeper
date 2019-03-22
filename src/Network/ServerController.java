package Network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerController {

    ByteArrayOutputStream BAOS;
    ObjectOutputStream OOS;
    //byte[] data;

    public byte[] convertObjectToByte (Object object) {
        try {
            BAOS = new ByteArrayOutputStream(6400);
            OOS = new ObjectOutputStream(BAOS);
            OOS.writeObject(object);
            //data = BAOS.toByteArray();
            return BAOS.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void sendPacket(Object object) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9876);

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        while(true) {

            DatagramPacket receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            String sentence = new String(receivePacket.getData());

            InetAddress IPAddress = receivePacket.getAddress();

            int port = receivePacket.getPort();

            //String capitalizedSentence = sentence.toUpperCase();

            //sendData = capitalizedSentence.getBytes();
            sendData = convertObjectToByte(object);

            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, port);

            //System.out.println("FROM CLIENT: " + sentence);
            System.out.println("FROM CLIENT: received");

            serverSocket.send(sendPacket);
        }
    }
}
