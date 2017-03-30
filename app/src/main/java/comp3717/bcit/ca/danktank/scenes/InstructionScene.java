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
    private Bitmap up_image;
    private Bitmap down_image;
    private Bitmap right_image;
    private Bitmap left_image;
    private Bitmap pause_image;
    private Bitmap fire_image;
    private Bitmap player_image;
    private Bitmap tank1_image;
    private Rect pauseButton, moveLeftButton, moveRightButton,
            moveUpButton, moveDownButton, fireButton, player, tank1;

    public InstructionScene(){
        backButton= new Rect(0, 0, 150, 150);
        BitmapFactory bitmapFactory = new BitmapFactory();
        back_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.back_button);
        pause_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pause_button);
        up_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.up_button);
        down_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.down_button);
        right_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.right_button);
        left_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.left_button);
        fire_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fire_button);
        player_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p_idle);
        tank1_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.e_idle);

        moveLeftButton = new Rect(Constants.SCREEN_WIDTH*6/20, Constants.SCREEN_HEIGHT*8/20, Constants.SCREEN_WIDTH*7/20, Constants.SCREEN_HEIGHT*9/20);
        moveRightButton = new Rect(Constants.SCREEN_WIDTH*8/20, Constants.SCREEN_HEIGHT*8/20, Constants.SCREEN_WIDTH*7/20 + 100, Constants.SCREEN_HEIGHT*9/20);
        moveUpButton = new Rect(Constants.SCREEN_WIDTH*7/20, Constants.SCREEN_HEIGHT*7/20, Constants.SCREEN_WIDTH*7/20 + 50, Constants.SCREEN_HEIGHT*8/20);
        moveDownButton = new Rect(Constants.SCREEN_WIDTH*7/20, Constants.SCREEN_HEIGHT*8/20, Constants.SCREEN_WIDTH*7/20 + 50, Constants.SCREEN_HEIGHT*9/20);
        fireButton = new Rect(Constants.SCREEN_WIDTH*5/20, Constants.SCREEN_HEIGHT*10/20, Constants.SCREEN_WIDTH*7/20 + 50, Constants.SCREEN_HEIGHT*12/20);
        pauseButton = new Rect(Constants.SCREEN_WIDTH*4/20, Constants.SCREEN_HEIGHT*13/20, Constants.SCREEN_WIDTH*6/20 + 50, Constants.SCREEN_HEIGHT*15/20);
        player = new Rect(Constants.SCREEN_WIDTH*6/20, Constants.SCREEN_HEIGHT*15/20, Constants.SCREEN_WIDTH*8/20 + 50, Constants.SCREEN_HEIGHT*17/20);
        tank1 = new Rect(Constants.SCREEN_WIDTH*10/20, Constants.SCREEN_HEIGHT*9/10, Constants.SCREEN_WIDTH*12/20 + 50, Constants.SCREEN_HEIGHT);
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        drawTitle(canvas, paint, "How to play");
        canvas.drawBitmap(back_image, null, backButton, paint);
        drawInstructions(canvas, paint);
    }

    private void drawTitle(Canvas canvas, Paint paint, String text)
    {
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLUE);
        canvas.drawText(text, Constants.SCREEN_WIDTH/4, Constants.SCREEN_HEIGHT/10 ,paint);
    }

    private void drawInstructions(Canvas canvas, Paint paint)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.RED);
        paint.setTextSize(75);
        canvas.drawText("Objective", Constants.SCREEN_WIDTH/3, Constants.SCREEN_HEIGHT*3/20 ,paint);
        paint.setTextSize(50);
        paint.setColor(Color.BLACK);
        canvas.drawText("-Shoot down as many enemy tanks as you can", Constants.SCREEN_WIDTH/30, Constants.SCREEN_HEIGHT*4/20 ,paint);
        canvas.drawText("-Recover the paintings:", Constants.SCREEN_WIDTH/30, Constants.SCREEN_HEIGHT*5/20 ,paint);
        canvas.drawText("1. Press the              buttons to move your tank", Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT*8/20 ,paint);
        canvas.drawBitmap(up_image, null, moveUpButton, new Paint());
        canvas.drawBitmap(down_image, null, moveDownButton, new Paint());
        canvas.drawBitmap(left_image, null, moveLeftButton, new Paint());
        canvas.drawBitmap(right_image, null, moveRightButton, new Paint());

        canvas.drawText("2. Press                  button to shoot", Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT*11/20 ,paint);
        canvas.drawBitmap(fire_image, null, fireButton, new Paint());

        canvas.drawText("3. Press           button to pause the game", Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT*7/10 ,paint);
        canvas.drawBitmap(pause_image, null, pauseButton, new Paint());

        canvas.drawText("Your tank: ", Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT*8/10 ,paint);
        canvas.drawBitmap(player_image, null, player, new Paint());
        canvas.drawText("The enemy tanks: ", Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT*9/10 ,paint);
        canvas.drawBitmap(tank1_image, null, tank1, new Paint());
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
