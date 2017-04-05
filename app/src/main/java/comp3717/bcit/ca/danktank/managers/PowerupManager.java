package comp3717.bcit.ca.danktank.managers;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.objects.Powerup;

/**
 * Created by namblue on 4/4/2017.
 */

public class PowerupManager
{
    private ArrayList<Powerup> powerups, collectedPowerups, allPowerUps;

    public PowerupManager()
    {
        powerups = new ArrayList<>();
        allPowerUps = new ArrayList<>();
        collectedPowerups = new ArrayList<>();
        this.readJsonFromFile();
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

    public ArrayList<Powerup> getCollectedPowerups(){
        return this.collectedPowerups;
    }

    public void spawnPowerup(Rect rect)
    {
        int p_index = (int)Math.floor(Math.random() * allPowerUps.size());
        Powerup p = allPowerUps.get(p_index);
        Log.d("Power:", p.toString());
        p.setRectangle(rect);
        powerups.add(p);
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

    private void readJsonFromFile()
    {
        try
        {
            Type collectionType = new TypeToken<ArrayList<Powerup>>(){}.getType();
            String filename = Constants.CURRENT_CONTEXT.getFilesDir() + "/" + "a0.json";
            JsonReader js = new JsonReader(new FileReader(filename));
            Gson gson = new Gson();
            allPowerUps  = gson.fromJson(js, collectionType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
