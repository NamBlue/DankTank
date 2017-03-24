package comp3717.bcit.ca.danktank.scenes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.R;
import comp3717.bcit.ca.danktank.managers.SceneManager;


/**
 * Created by panda on 2017-01-26.
 */

public class InstructionScene implements Scene {
    private Rect screen = new Rect();
    private Rect backButton;
    private Bitmap back_image;

    public InstructionScene(){
        backButton= new Rect(0, 0, 150, 150);
        BitmapFactory bitmapFactory = new BitmapFactory();
        back_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.back_button);
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        drawTitle(canvas, paint, "How to play");
        canvas.drawBitmap(back_image, null, backButton, paint);
        drawInstructions(canvas, paint,
                "1.Use the arrow keys to move\n" + "2.Use the fire button to shoot bullets\n" +
                        "asdf\n" +
                        "asdf\n");
    }

    private void drawTitle(Canvas canvas, Paint paint, String text)
    {
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLUE);
        canvas.drawText(text, Constants.SCREEN_WIDTH/4, Constants.SCREEN_HEIGHT/8 ,paint);
    }

    private void drawInstructions(Canvas canvas, Paint paint, String text)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(screen);
        paint.getTextBounds(text, 0, text.length(), screen);
        canvas.drawText(text, Constants.SCREEN_WIDTH/10, Constants.SCREEN_HEIGHT/4 ,paint);
    }

    public void receiveTouch(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(backButton.contains((int)event.getX(), (int)event.getY())){
                    SceneManager.ACTIVE_SCENE = 0;
                }
                break;
        }
    }
    @Override
    public void reset(){

    }

    @Override
    public void onExit()
    {

    }
}
