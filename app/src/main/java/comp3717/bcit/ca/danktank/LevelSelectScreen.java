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
 * Created by steve on 2017-01-26.
 */

public class LevelSelectScreen implements Scene {
    private Rect screen = new Rect();
    private Rect backButton = new Rect(0, 0, 150, 150);

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.BLUE);
        canvas.drawRect(backButton, paint);

        paint.setColor(Color.RED);
        canvas.drawText("<-",50,100,paint);

        paint.setColor(Color.BLUE);
        drawCentreText(canvas, paint, "Level 1");
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
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(backButton.contains((int)event.getX(), (int)event.getY())){
                    terminate();
                }
                else {
                    SceneManager.ACTIVE_SCENE = 1;
                }
                break;
        }
    }

    @Override
    public void reset() {

    }
}
