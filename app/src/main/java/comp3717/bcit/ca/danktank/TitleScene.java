package comp3717.bcit.ca.danktank;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class TitleScene implements Scene
{
    //Used to set the bounds for the gameover text box
    private Rect rectBound = new Rect();

    public TitleScene()
    {
    }

    public void reset()
    {
    }


    private void drawCentreText(Canvas canvas, Paint paint, String text)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(rectBound);
        int cHeight = rectBound.height();
        int cWidth = rectBound.width();
        paint.getTextBounds(text, 0, text.length(), rectBound);
        float x = cWidth / 2f - rectBound.width() / 2f - rectBound.left;
        float y = cHeight / 2f - rectBound.height() / 2f - rectBound.bottom;
        canvas.drawText(text, x ,y ,paint);
    }

    @Override
    public void update()
    {

    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawColor(Color.BLACK);

        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.BLUE);
        drawCentreText(canvas, paint, "Click to Start!");
    }

    @Override
    public void terminate()
    {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                SceneManager.ACTIVE_SCENE = 1;
                break;
        }
    }
}
