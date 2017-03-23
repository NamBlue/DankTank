package comp3717.bcit.ca.danktank.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by NamBlue on 1/20/2017.
 */

public interface Scene
{
    public void update();
    public void draw(Canvas canvas);
    public void receiveTouch(MotionEvent event);
    /**
     * Invoked on re-entering of a scene, used to restart the scene to initial state
     */
    public void reset();
    /**
     * Invoked on exit of a scene, used to clean up resources
     */
    public void onExit();
}
