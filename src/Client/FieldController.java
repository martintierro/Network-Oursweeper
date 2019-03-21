package Client;

public class FieldController {

    private Field field;

    public FieldController(Field field) {
        this.field = field;
    }

    public boolean sweepTile(int tileIndex){
        if(field.tileIsSweeped(tileIndex))
            return false;
        else{
            field.getTile(tileIndex).setSweep(true);
            field.notifyViews();
            return true;
        }
    }


}
