package Game;

import Network.UDPClient;

public class LoginController implements Runnable {
    private UDPClient udpClient;
    private GameModel model;

    public LoginController(UDPClient udpClient) {
        this.udpClient = udpClient;
    }

    public void setupGame(String username){
        try {
            udpClient.sendPacket(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameModel getModel() {
        return model;
    }

    @Override
    public void run() {
        try {
            model = (GameModel)udpClient.receivePacket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
