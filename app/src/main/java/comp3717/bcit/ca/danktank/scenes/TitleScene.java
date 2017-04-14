package comp3717.bcit.ca.danktank.scenes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
    private Rect screen;
    private Rect instructions_button, credits_button;
    private BitmapFactory bitmapFactory;
    private Bitmap title;
    private Paint titlePaint;
    private int alpha;
    private boolean decreasing;
    private AlertDialog credits;

    public TitleScene()
    {
        bitmapFactory = new BitmapFactory();
        screen = new Rect(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGHT);
        title = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.titlebackground);
        instructions_button = new Rect(0,Constants.SCREEN_HEIGHT*15/20,Constants.SCREEN_WIDTH, (int)(Constants.SCREEN_HEIGHT*17.5/20));
        credits_button = new Rect(0,(int)(Constants.SCREEN_HEIGHT*17.5/20),Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        titlePaint = new Paint();
        titlePaint.setTextSize((int)(Constants.SCREEN_WIDTH * .08));
        titlePaint.setColor(Color.YELLOW);
        titlePaint.setTextAlign(Paint.Align.CENTER);
        alpha = 255;
        decreasing = true;
        credits = new AlertDialog.Builder(Constants.CURRENT_CONTEXT)
                .setTitle("Credits")
                .setMessage("Lead Developer:\nGeorge Lee\n\nLevel Designer:\nSteven Ma\n\nDatasets:\nHarman Mahal")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_menu_myplaces)
                .create();
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
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .10));
        paint.setColor(Color.YELLOW);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("The Dank Tank", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*2/20 ,paint);
        canvas.drawText("Tap to Start!", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/2 ,titlePaint);
        paint.setColor(Color.WHITE);
        canvas.drawText("Instructions", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*17/20 ,paint);
        canvas.drawText("Credits", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*19/20 ,paint);
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
                else if(credits_button.contains((int) event.getX(), (int) event.getY())){
                    credits.show();
                }
                else{
                    SceneManager.ACTIVE_SCENE = 2;
                }
                break;
        }
    }
}
