package comp3717.bcit.ca.danktank.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.managers.MusicManager;
import comp3717.bcit.ca.danktank.managers.PowerupManager;
import comp3717.bcit.ca.danktank.managers.SceneManager;
import comp3717.bcit.ca.danktank.managers.LevelManager;
import comp3717.bcit.ca.danktank.objects.Powerup;

/**
 * Created by steve on 2017-03-30.
 */

public class ScoreScene implements Scene {
    private Rect levelselect_button = new Rect(Constants.SCREEN_WIDTH*2/20,Constants.SCREEN_HEIGHT*18/20,Constants.SCREEN_WIDTH*18/20,Constants.SCREEN_HEIGHT);
    private String name, desc, address, line;
    private int index, oldIndex;
    private boolean start = true;
    private ArrayList<Powerup> powerups = new ArrayList<>();
    private int powerUpsLeft = 0;
    private int maxLineSize = 44;
    private int lineSize, row, score;
    private boolean decreasing = true;
    private int alpha = 255;
    private Paint artUnlockedPaint = new Paint();
    private ArrayList<String> nameLines, summaryLines, addressLines;

    public ScoreScene()
    {
        artUnlockedPaint.setColor(Color.BLUE);
        artUnlockedPaint.setTextAlign(Paint.Align.CENTER);
        artUnlockedPaint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));
        name = "";
        desc = "";
        address = "";
        index = 0;
        oldIndex = -1;
        summaryLines = new ArrayList<>();
        nameLines = new ArrayList<>();
        addressLines = new ArrayList<>();
    }

    public void update(){
        if(start)
        {
            start = false;
            powerups = PowerupManager.getCollectedPowerups();
        }
        if(index != oldIndex)
        {
            oldIndex = index;
            if(index < powerups.size())
            {
                name = "Name: " + powerups.get(index).getName();
                desc = "Summary: " + powerups.get(index).getDescriptn();
                address = "Address: " + powerups.get(index).getAddress();
                wrapStrings(name, nameLines);
                wrapStrings(desc, summaryLines);
                wrapStrings(address, addressLines);
            }
            powerUpsLeft = powerups.size() - (index + 1);
            if(powerups.size() == 0)
            {
                powerUpsLeft = 0;
            }
        }
        if(decreasing)
        {
            alpha -= Constants.TITLE_BLINK_SPEED;
            if(alpha < 0)
            {
                decreasing = false;
                alpha = 0;
            }
            artUnlockedPaint.setAlpha(alpha);
        }
        else
        {
            alpha += Constants.TITLE_BLINK_SPEED;
            if(alpha > 255)
            {
                decreasing  = true;
                alpha = 255;
            }
            artUnlockedPaint.setAlpha(alpha);
        }
    }

    private void wrapStrings(String string, ArrayList<String> list)
    {
        String temp = "";
        list.clear();
        int i = 0, j = 0;
        while (i + 46 < string.length() && (i = string.lastIndexOf(" ", i + 46)) != -1) {
            temp = string.substring(j, i);
            j = i + 1;
            list.add(temp);
            temp = string.substring(j, string.length());
            if(temp.length() < 46)
            {
                list.add(temp);
            }
        }
        if(list.size() == 0)
        {
            list.add(string);
        }
    }


    public void draw(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .08));
        canvas.drawText("Score", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT/20 ,paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .05));
        score = GameplayScene.score;
        String totalScore = "Total Score: " + score;
        canvas.drawText(totalScore, Constants.SCREEN_WIDTH / 20, Constants.SCREEN_HEIGHT * 3 / 20, paint);
        canvas.drawText("Powerups Left: " + powerUpsLeft, Constants.SCREEN_WIDTH *12/20, Constants.SCREEN_HEIGHT*3/20 ,paint);
        if(powerups.size() > 0)
            canvas.drawText("Art Unlocked!!!", Constants.SCREEN_WIDTH *3/6, Constants.SCREEN_HEIGHT*5/20 ,artUnlockedPaint);
        else
            canvas.drawText("No Art, You Failed!", Constants.SCREEN_WIDTH *3/6, Constants.SCREEN_HEIGHT*5/20 ,artUnlockedPaint);

        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .045));
        paint.setTextAlign(Paint.Align.LEFT);
        row = 6;

        //Display Name
        for(int i = 0; i < nameLines.size(); ++i)
        {
            canvas.drawText(nameLines.get(i), Constants.SCREEN_WIDTH / 20, Constants.SCREEN_HEIGHT * row / 20, paint);
            row++;
        }

        //Display summary
        for(int i = 0; i < summaryLines.size(); ++i)
        {
            canvas.drawText(summaryLines.get(i), Constants.SCREEN_WIDTH / 20, Constants.SCREEN_HEIGHT * row / 20, paint);
            row++;
        }

        //Display Address
        for(int i = 0; i < addressLines.size(); ++i)
        {
            canvas.drawText(addressLines.get(i), Constants.SCREEN_WIDTH / 20, Constants.SCREEN_HEIGHT * row / 20, paint);
            row++;
        }

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize((int)(Constants.SCREEN_WIDTH * .07));
        paint.setColor(Color.BLACK);
        canvas.drawRect(levelselect_button, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawText("Back to Level Select", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT*19/20 ,paint);
    }
    public void receiveTouch(MotionEvent event){

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (levelselect_button.contains((int) event.getX(), (int) event.getY()))
                {
                    SceneManager.ACTIVE_SCENE = 2;
                    MusicManager.getInstance().playTitle();
                    GameplayScene.pause = false;
                    break;
                }
                else if(index < powerups.size() -1)
                {
                    ++index;
                }
    }
    }
    /**
     * Invoked on re-entering of a scene, used to restart the scene to initial state
     */
    public void reset(){
        start = true;
        powerUpsLeft = 0;
        powerups = new ArrayList<>();
        summaryLines = new ArrayList<>();
        nameLines = new ArrayList<>();
        addressLines = new ArrayList<>();
        name = "";
        desc = "";
        address = "";
        index = 0;
        oldIndex = -1;
    }
    /**
     * Invoked on exit of a scene, used to clean up resources
     */
    public void onExit(){

    }
}
