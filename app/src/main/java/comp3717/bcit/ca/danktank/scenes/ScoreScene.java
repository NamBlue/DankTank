package comp3717.bcit.ca.danktank.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.managers.SceneManager;

/**
 * Created by steve on 2017-03-30.
 */

public class ScoreScene implements Scene {


    public void update(){

    }
    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.RED);
        canvas.drawText("Score", Constants.SCREEN_WIDTH/4, Constants.SCREEN_HEIGHT/10 ,paint);
    }
    public void receiveTouch(MotionEvent event){

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (true)
                {
                    SceneManager.ACTIVE_SCENE = 2;
                    break;
                }

        }
    }
    /**
     * Invoked on re-entering of a scene, used to restart the scene to initial state
     */
    public void reset(){

    }
    /**
     * Invoked on exit of a scene, used to clean up resources
     */
    public void onExit(){

    }
}
