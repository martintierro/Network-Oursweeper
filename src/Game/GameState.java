package Game;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {

    private Player currentPlayer;
    private ArrayList<Integer> sweepedTileIndeces;
    private boolean over;
    private ArrayList<Integer> deadPlayers;

    public GameState(GameModel game){
        sweepedTileIndeces = new ArrayList<>();
        deadPlayers = new ArrayList<>();
        currentPlayer = game.getCurrentPlayer();
        for(Tile tile:game.getField().getTiles())
            if(tile.isSweep())
                sweepedTileIndeces.add(game.getField().getTiles().indexOf(tile));
        over = game.isOver();
        for(Player player:game.getPlayers())
            if(!player.isAlive())
                deadPlayers.add(game.getPlayers().indexOf(player));
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Integer> getSweepedTileIndeces() {
        return sweepedTileIndeces;
    }

    public boolean isOver() {
        return over;
    }

    public ArrayList<Integer> getDeadPlayers() {
        return deadPlayers;
    }
}
