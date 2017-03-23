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

public class LevelSelectScene implements Scene {
    private Rect backButton = new Rect(0, 0, 150, 150);
    private Rect level1Button = new Rect(Constants.SCREEN_WIDTH/10, Constants.SCREEN_HEIGHT*2/10, Constants.SCREEN_WIDTH*4/10, Constants.SCREEN_HEIGHT *5/10);
    private Rect level2Button = new Rect(Constants.SCREEN_WIDTH*6/10, Constants.SCREEN_HEIGHT*2/10, Constants.SCREEN_WIDTH*9/10, Constants.SCREEN_HEIGHT*5/10);
    private Rect level3Button = new Rect(Constants.SCREEN_WIDTH/10, Constants.SCREEN_HEIGHT*6/10, Constants.SCREEN_WIDTH*4/10, Constants.SCREEN_HEIGHT*9/10);
    private Rect level4Button = new Rect(Constants.SCREEN_WIDTH*6/10, Constants.SCREEN_HEIGHT*6/10, Constants.SCREEN_WIDTH*9/10, Constants.SCREEN_HEIGHT*9/10);

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();

        drawBackButton(canvas, paint);
        drawLevelButtons(canvas, paint);
        drawLevel1Text(canvas, paint, "Level 1");
        drawLevel2Text(canvas, paint, "Level 2");
        drawLevel3Text(canvas, paint, "Level 3");
        drawLevel4Text(canvas, paint, "Level 4");
    }

    private void drawBackButton(Canvas canvas, Paint paint)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(100);
        paint.setColor(Color.BLUE);
        canvas.drawRect(backButton, paint);
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("<-",25,100,paint);
    }

    private void drawLevelButtons(Canvas canvas, Paint paint){
        paint.setColor(Color.RED);
        canvas.drawRect(level1Button, paint);
        paint.setColor(Color.GREEN);
        canvas.drawRect(level2Button, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(level3Button, paint);
        paint.setColor(Color.GRAY);
        canvas.drawRect(level4Button, paint);
    }

    private void drawLevel1Text(Canvas canvas, Paint paint, String text)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
        canvas.drawText(text, Constants.SCREEN_WIDTH/10 ,Constants.SCREEN_HEIGHT*2/10 ,paint);
    }

    private void drawLevel2Text(Canvas canvas, Paint paint, String text)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
        canvas.drawText(text, Constants.SCREEN_WIDTH*6/10 ,Constants.SCREEN_HEIGHT*2/10 ,paint);
    }

    private void drawLevel3Text(Canvas canvas, Paint paint, String text)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
        canvas.drawText(text, Constants.SCREEN_WIDTH/10 ,Constants.SCREEN_HEIGHT*6/10 ,paint);
    }

    private void drawLevel4Text(Canvas canvas, Paint paint, String text)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
        canvas.drawText(text, Constants.SCREEN_WIDTH*6/10 ,Constants.SCREEN_HEIGHT*6/10 ,paint);
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(backButton.contains((int)event.getX(), (int)event.getY())){
                    SceneManager.ACTIVE_SCENE = 0;
                }
                if(level1Button.contains((int)event.getX(), (int)event.getY())){
                    SceneManager.ACTIVE_SCENE = 1;
                }

                break;
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
