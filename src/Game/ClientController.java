package Game;

import Network.UDPClient;

public class ClientController {
    private GameModel clientModel;

    public ClientController(GameModel clientModel) {
        this.clientModel = clientModel;
    }

    public void sweepNextTile(int tileIndex){

    }

    public void setNextState(GameState nextState){
        clientModel.setOver(nextState.isOver());
        for(int tileIndex:nextState.getSweepedTileIndeces())
            clientModel.getField().getTiles().get(tileIndex).setSweep(true);
        for(int playerIndex:nextState.getAlivePlayers())
            clientModel.getPlayers().get(playerIndex).setAlive(true);
        clientModel.setCurrentPlayer(nextState.getCurrentPlayer());
    }
}
