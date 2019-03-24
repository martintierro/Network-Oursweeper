package Network;

import Game.GameController;
import Game.GameModel;
import Game.GameState;
import Game.Player;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ServerController implements Serializable{


    private GameController GController;
    private GameModel GModel;
    private GameState GState;

    public ServerController(ArrayList<Player> players) {
        this.GModel = new GameModel(players);
        this.GState = new GameState(this.GModel);
        this.GController = new GameController(this.GModel);
    }


    public GameModel getGameModel() {
        return GModel;
    }

    public GameState getNextState(int tile) {
        GController.sweepNextTile(tile);
        return GState;
    }
}
