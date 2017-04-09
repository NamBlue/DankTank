package comp3717.bcit.ca.danktank.scenes;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.R;
import comp3717.bcit.ca.danktank.managers.SceneManager;


/**
 * Created by steve on 2017-01-26.
 */

public class PauseScene implements Scene {
    public static boolean resume = false;
    private Rect screen = new Rect();
    private Rect resumeButton = new Rect(Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*6/20, Constants.SCREEN_WIDTH*7/8, Constants.SCREEN_HEIGHT*8/20);
    private Rect restartButton = new Rect(Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*9/20, Constants.SCREEN_WIDTH*7/8, Constants.SCREEN_HEIGHT*11/20);
    private Rect levelSelectButton = new Rect(Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*12/20, Constants.SCREEN_WIDTH*7/8, Constants.SCREEN_HEIGHT*14/20);
    private Rect QuitButton = new Rect(Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*15/20, Constants.SCREEN_WIDTH*7/8, Constants.SCREEN_HEIGHT*17/20);


    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        drawTitle(canvas, paint, "Paused");
        drawOptions(canvas, paint);

    }

    private void drawTitle(Canvas canvas, Paint paint, String text)
    {
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.RED);
        canvas.drawText(text, Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/8 ,paint);

    }

    private void drawOptions(Canvas canvas, Paint paint)
    {
        paint.setColor(Color.BLACK);
        canvas.drawRect(resumeButton, paint);
        canvas.drawRect(restartButton, paint);
        canvas.drawRect(levelSelectButton, paint);
        canvas.drawRect(QuitButton, paint);

        paint.setColor(Color.YELLOW);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .075));
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Resume", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*7/20,paint);
        canvas.drawText("Restart", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*10/20 ,paint);
        canvas.drawText("Return to level select", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*13/20 ,paint);
        canvas.drawText("Quit", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*16/20 ,paint);
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (resumeButton.contains((int) event.getX(), (int) event.getY()))
                {
                    resume = true;
                    SceneManager.ACTIVE_SCENE = 1;
                    break;
                }
                if (restartButton.contains((int) event.getX(), (int) event.getY()))
                {
                    GameplayScene.pause = false;
                    SceneManager.ACTIVE_SCENE = 1;
                    break;
                }
                if (levelSelectButton.contains((int) event.getX(), (int) event.getY()))
                {
                    GameplayScene.pause = false;
                    SceneManager.ACTIVE_SCENE = 2;
                    break;
                }
                if (QuitButton.contains((int) event.getX(), (int) event.getY()))
                {
                    GameplayScene.pause = false;
                    SceneManager.ACTIVE_SCENE = 0;
                    break;
                }
        }
    }

    @Override
    public void reset() {

    }

    @Override
    public void onExit()
    {

    }
}
