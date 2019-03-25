package Game;

import Network.UDPClient;

import java.net.SocketTimeoutException;

public class LoginController {
    private UDPClient udpClient;
    private GameModel model;

    public LoginController(UDPClient udpClient) {
        this.udpClient = udpClient;
    }

    public void setupGame(String username)  {
        try {
            udpClient.sendPacket(username);
            System.out.println("Connected: " + udpClient.receiveString());
            model = (GameModel)udpClient.receivePacket();
        /*}catch (SocketTimeoutException s){
            System.out.println("Socket has timed out");
            s.printStackTrace();*/
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameModel getModel() {
        return model;
    }
}
