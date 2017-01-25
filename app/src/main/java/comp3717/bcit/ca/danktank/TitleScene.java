package comp3717.bcit.ca.danktank;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.PictureDrawable;
import android.view.MotionEvent;
import android.graphics.Bitmap;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class TitleScene implements Scene
{
    //Used to set the bounds for the gameover text box
    private Rect screen = new Rect();

    private BitmapFactory bitmapFactory;
    private Bitmap title;

    public TitleScene()
    {
        bitmapFactory = new BitmapFactory();
        title = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.titlebackground);
    }

    public void reset()
    {

    }

    private void drawCentreText(Canvas canvas, Paint paint, String text)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(screen);
        int cHeight = screen.height();
        int cWidth = screen.width();
        paint.getTextBounds(text, 0, text.length(), screen);
        float x = cWidth / 2f - screen.width() / 2f - screen.left;
        float y = cHeight / 2f - screen.height() / 2f - screen.bottom;
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
        canvas.getClipBounds(screen);
        canvas.drawBitmap(title, null, screen, null);


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
