package Game;

import java.util.ArrayList;
import java.util.Random;

public class Field extends Model{
    private ArrayList<Tile> tiles;
    Random random = new Random();

    public Field(){
        tiles = new ArrayList<>();
        for(int i=0 ; i<100 ; i++){
            tiles.add(new Tile());
            if(random.nextInt(10)==0)
                this.tiles.get(i).setBomb(true);
        }
    }

    public ArrayList<Tile> getField(){
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

    public void display(){
        int count = 0;
        for(Tile tile: tiles){
            if(tile.isBomb())
                count++;
        }
        System.out.println(count);
    }

    public static void main (String[] args){
        Field field = new Field();
        field.display();
    }

}
