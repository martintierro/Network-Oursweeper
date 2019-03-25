package Game;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Model implements Serializable {

    private ArrayList<View> views;

    public Model(){
        views = new ArrayList<>();
    }

    public void attach(View view){
        views.add(view);
    }

    public void detach(View view){
        views.remove(view);
    }

    public void notifyViews(){
        for(View view: views)
            view.update();
    }

}
