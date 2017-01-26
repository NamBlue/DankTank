package comp3717.bcit.ca.danktank;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

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
        scenes.add(new LevelSelectScreen());
        scenes.add(new PauseScene());
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
