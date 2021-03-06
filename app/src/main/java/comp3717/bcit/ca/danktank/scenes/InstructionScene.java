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
    private Rect backButton, backZone;
    private Bitmap back_image;
    private Bitmap up_image;
    private Bitmap down_image;
    private Bitmap right_image;
    private Bitmap left_image;
    private Bitmap pause_image;
    private Bitmap fire_image;
    private Bitmap player_image;
    private Bitmap tank1_image;
    private Bitmap art_image;
    private Rect pauseButton, moveLeftButton, moveRightButton,
            moveUpButton, moveDownButton, fireButton, player, tank1, art;

    public InstructionScene(){
        backButton= new Rect(0, 0, (int)(Constants.SCREEN_WIDTH * .15), (int)(Constants.SCREEN_WIDTH * .15));
        backZone = new Rect(0, 0, (int)(Constants.SCREEN_WIDTH * .20), (int)(Constants.SCREEN_WIDTH * .20));
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
        art_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.flag);

        moveLeftButton = new Rect(Constants.SCREEN_WIDTH*6/20, Constants.SCREEN_HEIGHT*8/20, Constants.SCREEN_WIDTH*7/20, Constants.SCREEN_HEIGHT*9/20);
        moveRightButton = new Rect(Constants.SCREEN_WIDTH*8/20, Constants.SCREEN_HEIGHT*8/20, Constants.SCREEN_WIDTH*9/20, Constants.SCREEN_HEIGHT*9/20);
        moveUpButton = new Rect(Constants.SCREEN_WIDTH*7/20, Constants.SCREEN_HEIGHT*7/20, Constants.SCREEN_WIDTH*8/20, Constants.SCREEN_HEIGHT*8/20);
        moveDownButton = new Rect(Constants.SCREEN_WIDTH*7/20, Constants.SCREEN_HEIGHT*8/20, Constants.SCREEN_WIDTH*8/20, Constants.SCREEN_HEIGHT*9/20);
        fireButton = new Rect(Constants.SCREEN_WIDTH*5/20, Constants.SCREEN_HEIGHT*10/20, Constants.SCREEN_WIDTH*7/20, Constants.SCREEN_HEIGHT*12/20);
        pauseButton = new Rect(Constants.SCREEN_WIDTH*4/20, Constants.SCREEN_HEIGHT*13/20, Constants.SCREEN_WIDTH*6/20, Constants.SCREEN_HEIGHT*15/20);
        player = new Rect(Constants.SCREEN_WIDTH * 5 / 20, Constants.SCREEN_HEIGHT *
                16 / 20, Constants.SCREEN_WIDTH * (5 + 2) / 20, Constants.SCREEN_HEIGHT *
                (16 + 1) / 20);
        tank1 = new Rect(Constants.SCREEN_WIDTH * 4 / 10, Constants.SCREEN_HEIGHT *
                18 / 20, Constants.SCREEN_WIDTH * (4 + 1) / 10, Constants.SCREEN_HEIGHT *
                (18 + 1) / 20);
        art = new Rect(Constants.SCREEN_WIDTH*12/20, Constants.SCREEN_HEIGHT*11/40, Constants.SCREEN_WIDTH*15/20, Constants.SCREEN_HEIGHT*14/40);
    }

    public void update(){

    }

    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));
        drawTitle(canvas, paint, "How to play");
        canvas.drawBitmap(back_image, null, backButton, paint);
        drawInstructions(canvas, paint);
    }

    private void drawTitle(Canvas canvas, Paint paint, String text)
    {
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLUE);
        canvas.drawText(text, Constants.SCREEN_WIDTH/4, Constants.SCREEN_HEIGHT/10 ,paint);
    }

    private void drawInstructions(Canvas canvas, Paint paint)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.RED);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .075));
        canvas.drawText("Objective", Constants.SCREEN_WIDTH/3, Constants.SCREEN_HEIGHT*3/20 ,paint);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .045));
        paint.setColor(Color.BLACK);
        canvas.drawText("- Shoot down all enemy tanks", Constants.SCREEN_WIDTH/30, Constants.SCREEN_HEIGHT*4/20 ,paint);
        canvas.drawText("- Recover the New Westminster flags", Constants.SCREEN_WIDTH/30, Constants.SCREEN_HEIGHT*5/20 ,paint);
        canvas.drawText("to unlock art descriptions:", Constants.SCREEN_WIDTH*2/30, Constants.SCREEN_HEIGHT*6/20 ,paint);
        canvas.drawBitmap(art_image, null, art, paint);
        canvas.drawText("1. Press the", Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT*8/20 ,paint);
        canvas.drawText("  buttons to move your tank", Constants.SCREEN_WIDTH*9/20, Constants.SCREEN_HEIGHT*8/20 ,paint);
        canvas.drawBitmap(up_image, null, moveUpButton, paint);
        canvas.drawBitmap(down_image, null, moveDownButton, paint);
        canvas.drawBitmap(left_image, null, moveLeftButton, paint);
        canvas.drawBitmap(right_image, null, moveRightButton, paint);

        canvas.drawText("2. Press", Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT*11/20 ,paint);
        canvas.drawText("  button to shoot", Constants.SCREEN_WIDTH*7/20, Constants.SCREEN_HEIGHT*11/20 ,paint);
        canvas.drawBitmap(fire_image, null, fireButton, paint);

        canvas.drawText("3. Press", Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT*7/10 ,paint);
        canvas.drawText("button to pause the game", Constants.SCREEN_WIDTH*6/20, Constants.SCREEN_HEIGHT*7/10 ,paint);
        canvas.drawBitmap(pause_image, null, pauseButton, paint);

        canvas.drawText("Your tank: ", Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT*8/10 ,paint);
        canvas.drawBitmap(player_image, null, player, paint);
        canvas.drawText("The enemy tanks: ", Constants.SCREEN_WIDTH/20, Constants.SCREEN_HEIGHT*9/10 ,paint);
        canvas.drawBitmap(tank1_image, null, tank1, paint);
    }

    public void receiveTouch(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(backZone.contains((int)event.getX(), (int)event.getY())){
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
