package comp3717.bcit.ca.danktank;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import comp3717.bcit.ca.danktank.managers.MusicManager;
import comp3717.bcit.ca.danktank.managers.SceneManager;

/**
 * Created by namblue on 1/19/2017.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    private MainThread thread;

    private SceneManager sceneManager;

    private MusicManager musicManager;

    public GamePanel(Context context)
    {
        super(context);

        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;
        thread = new MainThread(getHolder(), this);
        sceneManager = new SceneManager();
        musicManager = new MusicManager();
        setFocusable(true);
    }

    public void pause()
    {
        musicManager.pause();
    }

    public void resume()
    {
        musicManager.resume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        thread = new MainThread(getHolder(), this);
        Constants.INIT_TIME = System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while (retry)
        {
            try
            {
                if (thread != null)
                {
                    thread.setRunning(false);
                    thread.join();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        sceneManager.receiveTouch(event);
        return true;
        //return super.onTouchEvent(event);
    }

    public void update()
    {
        sceneManager.update();
        musicManager.update();
    }

    @Override
    public void draw(Canvas canvas)
    {
        if (canvas != null)
        {
            super.draw(canvas);
            sceneManager.draw(canvas);
        }
    }

}
