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
 * Created by steve on 2017-01-26.
 */

public class LevelSelectScene implements Scene {
    private Rect backButton, backZone;
    private Rect level1Button;
    private Rect level2Button;
    private Rect level3Button;
    private Rect level4Button;
    private Bitmap back_image;
    private Bitmap Level1_image;
    private Bitmap Level2_image;
    private Bitmap Level3_image;
    private Bitmap Level4_image;


    public LevelSelectScene(){
        backButton = new Rect(0, 0, (int)(Constants.SCREEN_WIDTH * .15), (int)(Constants.SCREEN_WIDTH * .15));
        backZone = new Rect(0, 0, (int)(Constants.SCREEN_WIDTH * .20), (int)(Constants.SCREEN_WIDTH * .20));
        level1Button = new Rect(Constants.SCREEN_WIDTH/10, Constants.SCREEN_HEIGHT*3/20, Constants.SCREEN_WIDTH*4/10, Constants.SCREEN_HEIGHT *9/20);
        level2Button = new Rect(Constants.SCREEN_WIDTH*6/10, Constants.SCREEN_HEIGHT*3/20, Constants.SCREEN_WIDTH*9/10, Constants.SCREEN_HEIGHT*9/20);
        level3Button = new Rect(Constants.SCREEN_WIDTH/10, Constants.SCREEN_HEIGHT*12/20, Constants.SCREEN_WIDTH*4/10, Constants.SCREEN_HEIGHT*18/20);
        level4Button = new Rect(Constants.SCREEN_WIDTH*6/10, Constants.SCREEN_HEIGHT*12/20, Constants.SCREEN_WIDTH*9/10, Constants.SCREEN_HEIGHT*18/20);
        BitmapFactory bitmapFactory = new BitmapFactory();
        back_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.back_button);
        Level1_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.level1);
        Level2_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.level2);
        Level3_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.level3);
        Level4_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.level4);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.LTGRAY);
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
        canvas.drawBitmap(back_image, null, backButton, paint);
    }

    private void drawLevelButtons(Canvas canvas, Paint paint){
        canvas.drawBitmap(Level1_image,null, level1Button, new Paint());
        canvas.drawBitmap(Level2_image, null, level2Button, new Paint());
        canvas.drawBitmap(Level3_image, null, level3Button, new Paint());
        canvas.drawBitmap(Level4_image, null, level4Button, new Paint());
    }

    private void drawLevel1Text(Canvas canvas, Paint paint, String text)
    {
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));
        paint.setColor(Color.BLACK);
        canvas.drawText(text, Constants.SCREEN_WIDTH*5/20 ,Constants.SCREEN_HEIGHT*3/20 ,paint);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .075));
        paint.setColor(Color.YELLOW);
        canvas.drawText("Desert", Constants.SCREEN_WIDTH*5/20 ,Constants.SCREEN_HEIGHT*10/20 ,paint);
    }

    private void drawLevel2Text(Canvas canvas, Paint paint, String text)
    {
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.BLACK);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));
        canvas.drawText(text, Constants.SCREEN_WIDTH*15/20 ,Constants.SCREEN_HEIGHT*3/20 ,paint);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .075));
        paint.setColor(Color.BLUE);
        canvas.drawText("Snow", Constants.SCREEN_WIDTH*15/20 ,Constants.SCREEN_HEIGHT*10/20 ,paint);
    }

    private void drawLevel3Text(Canvas canvas, Paint paint, String text)
    {
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));

        canvas.drawText(text, Constants.SCREEN_WIDTH*5/20 ,Constants.SCREEN_HEIGHT*12/20 ,paint);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .075));
        paint.setColor(Color.GREEN);
        canvas.drawText("Forest", Constants.SCREEN_WIDTH*5/20 ,Constants.SCREEN_HEIGHT*19/20 ,paint);
    }

    private void drawLevel4Text(Canvas canvas, Paint paint, String text)
    {
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));

        canvas.drawText(text, Constants.SCREEN_WIDTH*15/20 ,Constants.SCREEN_HEIGHT*12/20 ,paint);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .075));
        paint.setColor(Color.RED);
        canvas.drawText("Volcano", Constants.SCREEN_WIDTH*15/20 ,Constants.SCREEN_HEIGHT*19/20 ,paint);
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(backZone.contains((int)event.getX(), (int)event.getY())){
                    SceneManager.ACTIVE_SCENE = 0;
                }
                else if(level1Button.contains((int)event.getX(), (int)event.getY())){
                    GameplayScene.level = 1;
                    SceneManager.ACTIVE_SCENE = 1;
                }
                else if(level2Button.contains((int)event.getX(), (int)event.getY())){
                    GameplayScene.level = 2;
                    SceneManager.ACTIVE_SCENE = 1;
                }
                else if(level3Button.contains((int)event.getX(), (int)event.getY())){
                    GameplayScene.level = 3;
                    SceneManager.ACTIVE_SCENE = 1;
                }
                else if(level4Button.contains((int)event.getX(), (int)event.getY())){
                    GameplayScene.level = 4;
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
