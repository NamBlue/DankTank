package comp3717.bcit.ca.danktank;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.PictureDrawable;
import android.util.Log;
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
    int x;
    int y;

    public TitleScene()
    {
        bitmapFactory = new BitmapFactory();
        title = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.titlebackground);
    }

    public void reset()
    {

    }

    private void drawCentreStart(Canvas canvas, Paint paint, String text)
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
        canvas.getClipBounds(screen);
        canvas.drawBitmap(title, null, screen, null);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.RED);
        drawCentreStart(canvas, paint, "Click to Start!");
        drawCentreInstruct(canvas, paint, "Instructions");
    }

    private void drawCentreInstruct(Canvas canvas, Paint paint, String instructions) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(screen);
        int cHeight = screen.height();
        int cWidth = screen.width();
        paint.getTextBounds(instructions, 0, instructions.length(), screen);
        float x = cWidth / 2f - screen.width() / 2f - screen.left;
        float y = cHeight / 2f - screen.height() / 2f - screen.bottom;
        y = y *2;
        canvas.drawText(instructions, x ,y ,paint);
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
                x = (int)event.getRawX();
                y = (int)event.getRawY();

                //code issue here
                if(y > screen.height()){
                    SceneManager.ACTIVE_SCENE = 3;
                }
                else{SceneManager.ACTIVE_SCENE = 2;}
                break;
        }
    }
}
