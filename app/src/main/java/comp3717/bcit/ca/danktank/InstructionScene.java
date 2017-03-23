package comp3717.bcit.ca.danktank;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.Button;


/**
 * Created by panda on 2017-01-26.
 */

public class InstructionScene implements Scene {
    private Rect screen = new Rect();
    private Rect backButton;

    public InstructionScene(){
        backButton= new Rect(0, 0, 150, 150);
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawColor(Color.BLACK);
        Paint paint = new Paint();
        drawTitle(canvas, paint, "How to play");
        drawBackButton(canvas, paint);
        drawInstructions(canvas, paint, "1.\n");
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
