package Network;

import Game.GameModel;
import Game.GameState;

public class ClientController implements Runnable{
    private GameModel clientModel;
    private UDPClient udpClient;

    public ClientController(GameModel clientModel, UDPClient udpClient) {
        this.clientModel = clientModel;
        this.udpClient = udpClient;
    }

    public void sweepNextTile(int tileIndex){
        try {
            udpClient.sendPacket(tileIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNextState(GameState nextState){
        clientModel.setOver(nextState.isOver());
        for(int tileIndex:nextState.getSweepedTileIndeces())
            clientModel.getField().getTiles().get(tileIndex).setSweep(true);
        for(int playerIndex:nextState.getAlivePlayers())
            clientModel.getPlayers().get(playerIndex).setAlive(true);
        clientModel.setCurrentPlayer(nextState.getCurrentPlayer());
        clientModel.notifyViews();
    }

    public void run() {
        while(!clientModel.isOver()){
            try {
                setNextState((GameState)udpClient.receivePacket());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
