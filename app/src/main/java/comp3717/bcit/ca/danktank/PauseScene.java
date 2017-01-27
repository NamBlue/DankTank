package comp3717.bcit.ca.danktank;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;


/**
 * Created by steve on 2017-01-26.
 */

public class PauseScene implements Scene {

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                SceneManager.ACTIVE_SCENE = 0;
                break;
        }
    }

    @Override
    public void reset() {

    }
}
