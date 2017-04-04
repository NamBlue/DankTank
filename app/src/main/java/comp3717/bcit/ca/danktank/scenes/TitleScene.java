package comp3717.bcit.ca.danktank.scenes;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.graphics.Bitmap;

import java.io.IOException;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.R;
import comp3717.bcit.ca.danktank.managers.SceneManager;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class TitleScene implements Scene
{
    //Used to set the bounds for the gameover text box
    private Rect screen = new Rect(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
    private Rect instructions_button = new Rect(0,Constants.SCREEN_HEIGHT*18/20,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
    private BitmapFactory bitmapFactory;
    private Bitmap title;
    private Paint titlePaint;
    private int alpha;
    private boolean decreasing;

    public TitleScene()
    {
        bitmapFactory = new BitmapFactory();
        title = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.titlebackground);
        titlePaint = new Paint();
        titlePaint.setTextSize(100);
        titlePaint.setColor(Color.YELLOW);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        alpha = 255;
        decreasing = true;
    }


    @Override
    public void reset()
    {

    }

    @Override
    public void onExit()
    {

    }

    @Override
    public void update()
    {
        if(decreasing)
        {
            alpha -= Constants.TITLE_BLINK_SPEED;
            if(alpha < 0)
            {
                decreasing = false;
                alpha = 0;
            }
            titlePaint.setAlpha(alpha);
        }
        else
        {
            alpha += Constants.TITLE_BLINK_SPEED;
            if(alpha > 255)
            {
                decreasing  = true;
                alpha = 255;
            }
            titlePaint.setAlpha(alpha);
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(title, null, screen, null);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.YELLOW);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("The Dank Tank", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*2/20 ,paint);
        canvas.drawText("Click to Start!", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2 ,titlePaint);
        paint.setColor(Color.WHITE);
        canvas.drawText("Instructions", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*19/20 ,paint);
    }

    @Override
    public void receiveTouch(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:

                //code issue here with the screen.height() the following hack allows the bottom 25%
                //to point to the instructions scene
                if(instructions_button.contains((int) event.getX(), (int) event.getY())){
                    SceneManager.ACTIVE_SCENE = 4;
                }
                else{
                    SceneManager.ACTIVE_SCENE = 2;

                }
                break;
        }
    }
}
