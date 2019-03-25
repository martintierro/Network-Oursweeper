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
        int currPlayerIndex = (game.getPlayers().indexOf(game.getCurrentPlayer()));
        if(currPlayerIndex+1>=game.getPlayers().size())
            currPlayerIndex = 0;
        while(!game.getPlayers().get(currPlayerIndex+1).isAlive())
           currPlayerIndex++;
        Player nextPlayer = game.getPlayers().get(currPlayerIndex);
        game.setCurrentPlayer(nextPlayer);
        game.notifyViews();
    }

    public void setIfOver(){
        int numAlive = 0;
        for(Player player: game.getPlayers())
            if(player.isAlive())
                numAlive++;
        if(numAlive == 1)
            game.setOver(true);
        else game.setOver(false);
        game.notifyViews();
    }

}
