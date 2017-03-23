package comp3717.bcit.ca.danktank.managers;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import comp3717.bcit.ca.danktank.scenes.ConfirmationScene;
import comp3717.bcit.ca.danktank.scenes.GameplayScene;
import comp3717.bcit.ca.danktank.scenes.InstructionScene;
import comp3717.bcit.ca.danktank.scenes.LevelSelectScene;
import comp3717.bcit.ca.danktank.scenes.PauseScene;
import comp3717.bcit.ca.danktank.scenes.Scene;
import comp3717.bcit.ca.danktank.scenes.TitleScene;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class SceneManager
{
    private ArrayList<Scene> scenes = new ArrayList<>();
    public static int ACTIVE_SCENE;
    private static int lastScene;

    public SceneManager()
    {
        ACTIVE_SCENE = lastScene = 0;
        scenes.add(new TitleScene());
        scenes.add(new GameplayScene());
        scenes.add(new LevelSelectScene());
        scenes.add(new PauseScene());
        scenes.add(new InstructionScene());
        scenes.add(new ConfirmationScene());
    }

    public void receiveTouch(MotionEvent event)
    {
        scenes.get(ACTIVE_SCENE).receiveTouch(event);
    }

    public void update()
    {
        //If Scene change occurred
        if (lastScene != ACTIVE_SCENE)
        {
            scenes.get(lastScene).onExit();
            scenes.get(ACTIVE_SCENE).reset();
            lastScene = ACTIVE_SCENE;
        }
        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas)
    {
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
}
