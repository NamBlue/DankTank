package comp3717.bcit.ca.danktank;

import android.content.Context;
import android.widget.Button;

/**
 * Created by steve on 2017-02-10.
 */

public class ButtonManager {

    public ButtonManager(Context context)
    {
        Constants.CURRENT_CONTEXT = context;
        Button myButton = new Button(context);
    }

    
}
