package Game;

public class GameController {
    private GameModel game;

    public GameController(GameModel game) {
        this.game = game;
    }

    public void sweepNextTile(int tileIndex){
        Tile tile = game.getField().getTile(tileIndex);
        tile.setSweep(true);
        if(tile.isBomb())
            game.getCurrentPlayer().setAlive(false);
        int nextPlayerIndex = (game.getPlayers().indexOf(game.getCurrentPlayer())+1);
        if(nextPlayerIndex>=game.getPlayers().size())
            nextPlayerIndex = 0;
        while(!game.getPlayers().get(nextPlayerIndex).isAlive())
           nextPlayerIndex++;
        Player nextPlayer = game.getPlayers().get(nextPlayerIndex);
        game.setCurrentPlayer(nextPlayer);
        game.notifyViews();
    }

}
