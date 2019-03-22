package Game;

public class Tile {

    private boolean sweep;
    private boolean bomb;

    public Tile(){
        sweep = false;
        bomb = false;
    }

    public boolean isSweep() {
        return sweep;
    }

    public void setSweep(boolean sweep) {
        this.sweep = sweep;
    }

    public boolean isBomb() {
        return bomb;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "sweep=" + sweep +
                ", bomb=" + bomb +
                '}';
    }
}
