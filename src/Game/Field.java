package Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Field implements Serializable{
    private ArrayList<Tile> tiles;
    Random random = new Random();

    public Field(){
        tiles = new ArrayList<>();
        for(int i=0 ; i<100 ; i++){
            tiles.add(new Tile());
            if(random.nextInt(60)==0)
                this.tiles.get(i).setBomb(true);
        }
    }

    public ArrayList<Tile> getTiles(){
        return tiles;
    }

    public Tile getTile(int i){
        return tiles.get(i);
    }

    public boolean tileIsSweeped(int i){
        return tiles.get(i).isSweep();
    }

    public boolean tileIsBomb(int i){
        return tiles.get(i).isBomb();
    }

}
