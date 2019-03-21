package Client;

import java.util.ArrayList;
import java.util.Random;

public class Player extends Model{
    private String name;
    private boolean alive;

    public Player(String name){
        this.name = name;
        this.alive = true;
    }

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }




}
