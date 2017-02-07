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
    private Rect screen = new Rect();

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.BLUE);
        drawCentreText(canvas, paint, "Are you sure you want to quit?");
    }

    private void drawCentreText(Canvas canvas, Paint paint, String text)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(screen);
        int cHeight = screen.height();
        int cWidth = screen.width();
        paint.getTextBounds(text, 0, text.length(), screen);
        float x = cWidth / 2f - screen.width() / 2f - screen.left;
        float y = Constants.SCREEN_HEIGHT/7;
        canvas.drawText(text, x , y,paint);
        canvas.drawText("Resume", x, y*3, paint);

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
                SceneManager.ACTIVE_SCENE = 1;
                break;
        }
    }

    @Override
    public void reset() {

    }
}
