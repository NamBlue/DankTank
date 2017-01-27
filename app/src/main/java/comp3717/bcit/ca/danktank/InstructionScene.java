package comp3717.bcit.ca.danktank;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;


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
        paint.setTextSize(100);
        paint.setColor(Color.BLUE);
        canvas.drawRect(backButton, paint);
        drawCentreText(canvas, paint, "Instructions");
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

    public void receiveTouch(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(backButton.contains((int)event.getX(), (int)event.getY())){
                    terminate();
                }
                break;
        }
    }

    public void terminate(){
        SceneManager.ACTIVE_SCENE = 0;
    }

    public void reset(){

    }
}
