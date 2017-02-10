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
    private Rect screen = new Rect();
    private Rect resumeButton = new Rect();
    private Rect restartButton = new Rect();
    private Rect levelSelectButton = new Rect();
    private Rect QuitButton = new Rect();

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        drawTitle(canvas, paint, "Paused");
        drawOptions(canvas, paint);
    }

    private void drawTitle(Canvas canvas, Paint paint, String text)
    {
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLUE);
        canvas.drawText(text, Constants.SCREEN_WIDTH/4, Constants.SCREEN_HEIGHT/8 ,paint);

    }

    private void drawOptions(Canvas canvas, Paint paint)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLUE);
        paint.setTextSize(75);
        canvas.drawText("Resume", Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*3/8 ,paint);
        canvas.drawText("Restart", Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*4/8 ,paint);
        canvas.drawText("Return to level select", Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*5/8 ,paint);
        canvas.drawText("Quit", Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*6/8 ,paint);
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
                SceneManager.ACTIVE_SCENE = 5;
                break;
        }
    }

    @Override
    public void reset() {

    }
}
