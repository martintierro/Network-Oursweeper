package Game;

import java.io.Serializable;
import java.util.ArrayList;

public class GameModel extends Model implements Serializable {
    private Field field;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private boolean over;

    public GameModel(ArrayList<Player> players){
        field = new Field();
        this.players = players;
        setCurrentPlayer(players.get(0));
    }

    public Field getField() {
        return field;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}
