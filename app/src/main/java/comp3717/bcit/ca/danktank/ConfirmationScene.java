package comp3717.bcit.ca.danktank;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;


/**
 * Created by steve on 2017-01-26.
 * Might delete.  Will work on it if we have time. For now, don't include in main game.
 */

public class ConfirmationScene implements Scene {


    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();

        drawCentreText(canvas, paint, "Are you sure you want to quit?");
        drawOptions(canvas, paint);
    }

    private void drawCentreText(Canvas canvas, Paint paint, String text)
    {
        paint.setTextSize(75);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLUE);
        canvas.drawText(text, Constants.SCREEN_WIDTH/14, Constants.SCREEN_HEIGHT/8 ,paint);
    }

    private void drawOptions(Canvas canvas, Paint paint)
    {
        paint.setTextSize(75);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLUE);
        canvas.drawText("Yes", Constants.SCREEN_WIDTH/5, Constants.SCREEN_HEIGHT*4/8 ,paint);
        canvas.drawText("No", Constants.SCREEN_WIDTH/5, Constants.SCREEN_HEIGHT*5/8 ,paint);
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
                SceneManager.ACTIVE_SCENE = 2;
                break;
        }
    }

    @Override
    public void reset() {

    }
}
