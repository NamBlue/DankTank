package comp3717.bcit.ca.danktank.managers;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

import comp3717.bcit.ca.danktank.objects.Powerup;

/**
 * Created by namblue on 4/4/2017.
 */

public class PowerupManager
{
    private ArrayList<Powerup> powerups, collectedPowerups;

    public PowerupManager()
    {
        powerups = new ArrayList<>();
        collectedPowerups = new ArrayList<>();
    }

    public boolean collided(Rect object)
    {
        for(int i = 0; i < powerups.size(); i++)
        {
            if(powerups.get(i).collided(object))
            {
                collectedPowerups.add(powerups.get(i));
                powerups.remove(i);
                return true;
            }
        }
        return false;
    }

    public void spawnPowerup(Rect rect)
    {
        powerups.add(new Powerup("test", "This is a test object", "www.google.com", rect));
    }

    public void draw(Canvas canvas)
    {
        for(Powerup powerup: powerups)
        {
            powerup.draw(canvas);
        }
    }

    public void update()
    {
        for(Powerup powerup: powerups)
        {
            powerup.update();
        }
    }
}
