package Network;

import Game.GameModel;
import Game.GameState;

import java.io.Serializable;

public class ClientController implements Runnable, Serializable {
    private GameModel clientModel;
    private UDPClient udpClient;

    public ClientController(GameModel clientModel, UDPClient udpClient) {
        this.clientModel = clientModel;
        this.udpClient = udpClient;
    }

    public void sweepNextTile(Integer tileIndex){
        try {
            udpClient.sendPacket(tileIndex.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNextState(GameState nextState){
        clientModel.setOver(nextState.isOver());
        for(int tileIndex:nextState.getSweepedTileIndeces())
            clientModel.getField().getTiles().get(tileIndex).setSweep(true);
        for(int playerIndex:nextState.getDeadPlayers())
            clientModel.getPlayers().get(playerIndex).setAlive(false);
        clientModel.setCurrentPlayer(nextState.getCurrentPlayer());
        clientModel.notifyViews();
    }

    public void run() {
        while(!clientModel.isOver()){
            try {
                setNextState((GameState) udpClient.receivePacket());
                System.out.println("RUN");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
