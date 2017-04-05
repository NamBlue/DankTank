package comp3717.bcit.ca.danktank.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.managers.PowerupManager;
import comp3717.bcit.ca.danktank.managers.SceneManager;
import comp3717.bcit.ca.danktank.objects.Powerup;

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
        paint.setColor(Color.BLUE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .07));
        canvas.drawText("Score", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/20 ,paint);
        paint.setColor(Color.BLUE);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .045));
        //TODO: Format output
        for(int i = 0; i < PowerupManager.getCollectedPowerups().size(); i++){
            Powerup p = PowerupManager.getCollectedPowerups().get(i);
            canvas.drawText(p.getName(), Constants.SCREEN_WIDTH/6, Constants.SCREEN_HEIGHT*4/20 ,paint);
            canvas.drawText(p.getDescriptn(), Constants.SCREEN_WIDTH/6, Constants.SCREEN_HEIGHT*5/20 ,paint);
            canvas.drawText(p.getAddress(), Constants.SCREEN_WIDTH/6, Constants.SCREEN_HEIGHT*6/20 ,paint);
        }
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .07));
        paint.setColor(Color.BLACK);
        canvas.drawRect(levelselect_button, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawText("Back to Level Select", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*19/20 ,paint);

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
