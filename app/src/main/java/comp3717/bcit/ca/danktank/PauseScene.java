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
    private Rect resumeButton = new Rect(Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*6/20, Constants.SCREEN_WIDTH*5/8, Constants.SCREEN_HEIGHT*8/20);
    private Rect restartButton = new Rect(Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*9/20, Constants.SCREEN_WIDTH*5/8, Constants.SCREEN_HEIGHT*11/20);
    private Rect levelSelectButton = new Rect(Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*12/20, Constants.SCREEN_WIDTH*5/8, Constants.SCREEN_HEIGHT*14/20);
    private Rect QuitButton = new Rect(Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*15/20, Constants.SCREEN_WIDTH*5/8, Constants.SCREEN_HEIGHT*17/20);

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

        canvas.drawRect(resumeButton, paint);
        canvas.drawRect(restartButton, paint);
        canvas.drawRect(levelSelectButton, paint);
        canvas.drawRect(QuitButton, paint);

        paint.setColor(Color.RED);
        paint.setTextSize((int)((Constants.SCREEN_HEIGHT * Constants.SCREEN_WIDTH)/900 *0.05));
        canvas.drawText("Resume", Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*6/20,paint);
        canvas.drawText("Restart", Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*9/20 ,paint);
        canvas.drawText("Return to level select", Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*12/20 ,paint);
        canvas.drawText("Quit", Constants.SCREEN_WIDTH/8, Constants.SCREEN_HEIGHT*15/20 ,paint);
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
                if (resumeButton.contains((int) event.getX(), (int) event.getY()))
                {


                }
                if (restartButton.contains((int) event.getX(), (int) event.getY()))
                {


                }
                if (levelSelectButton.contains((int) event.getX(), (int) event.getY()))
                {


                }
                if (QuitButton.contains((int) event.getX(), (int) event.getY()))
                {


                }
                SceneManager.ACTIVE_SCENE = 5;
                break;
        }
    }

    @Override
    public void reset() {

    }
}
