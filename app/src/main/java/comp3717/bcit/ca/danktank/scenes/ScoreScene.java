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
    private Rect levelselect_button = new Rect(Constants.SCREEN_WIDTH*2/20,Constants.SCREEN_HEIGHT*18/20,Constants.SCREEN_WIDTH*18/20,Constants.SCREEN_HEIGHT);

    public void update(){

    }
    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Score", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/20 ,paint);
        paint.setColor(Color.RED);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .045));
        canvas.drawText("Item name", Constants.SCREEN_WIDTH/6, Constants.SCREEN_HEIGHT*4/20 ,paint);
        canvas.drawText("Quantity", Constants.SCREEN_WIDTH*2/6, Constants.SCREEN_HEIGHT*4/20 ,paint);
        canvas.drawText("Description", Constants.SCREEN_WIDTH*3/6, Constants.SCREEN_HEIGHT*4/20 ,paint);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));
        canvas.drawText("Back to Level Select", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*18/20 ,paint);
    }
    public void receiveTouch(MotionEvent event){

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (levelselect_button.contains((int) event.getX(), (int) event.getY()))
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
