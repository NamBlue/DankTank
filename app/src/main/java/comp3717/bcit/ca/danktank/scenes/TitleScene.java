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
    private Rect screen = new Rect();
    private Rect instructions_button = new Rect(0,Constants.SCREEN_HEIGHT*18/20,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
    private BitmapFactory bitmapFactory;
    private Bitmap title;
    int x;
    int y;

    public TitleScene()
    {
        bitmapFactory = new BitmapFactory();
        title = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.titlebackground);

    }


    @Override
    public void reset()
    {
        //mySound.start();
    }

    @Override
    public void onExit()
    {
        //mySound.pause();
        //mySound.seekTo(0);
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
