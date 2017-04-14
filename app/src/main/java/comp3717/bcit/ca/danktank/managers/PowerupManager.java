package comp3717.bcit.ca.danktank.managers;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.objects.Art;
import comp3717.bcit.ca.danktank.objects.Powerup;

/**
 * Created by namblue on 4/4/2017.
 */

public class PowerupManager
{
    private ArrayList<Powerup> powerups;
    private ArrayList<Art> artsList;
    private static ArrayList<Powerup> collectedPowerups = new ArrayList<>();

    public PowerupManager()
    {
        powerups = new ArrayList<>();
        artsList = new ArrayList<>();
        this.readJsonFromFile();
    }

    public boolean collided(Rect object)
    {
        for(int i = 0; i < powerups.size(); ++i)
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

    public void collectDroppedPowerups()
    {
        for(Powerup pu: powerups)
        {
            collectedPowerups.add(pu);
        }
        int p_index = (int) Math.floor(Math.random() * artsList.size());
        Art temp = artsList.get(p_index);
        collectedPowerups.add(new Powerup(temp.Name, temp.summary, temp.Address, temp.city, new Rect()));
        powerups.clear();
    }

    public static ArrayList<Powerup> getCollectedPowerups(){
        return collectedPowerups;
    }

    public void spawnPowerup(Rect rect)
    {
        boolean exists = false, spawned = false;
        double spawnPercentage = Math.random();
        if ((spawnPercentage >= 0))
        {
            while(!spawned && (collectedPowerups.size() + powerups.size()) < artsList.size())
            {
                exists = false;
                int p_index = (int) Math.floor(Math.random() * artsList.size());
                Art temp = artsList.get(p_index);
                for (Powerup pwr : powerups)
                {
                    if (pwr.getName().equals(temp.Name))
                    {
                        exists = true;
                        break;
                    }
                }
                for (Powerup pwr : collectedPowerups)
                {
                    if (pwr.getName().equals(temp.Name))
                    {
                        exists = true;
                        break;
                    }
                }
                if (!exists)
                {
                    Powerup p = new Powerup(temp.Name, temp.summary, temp.Address, temp.city, rect);
                    powerups.add(p);
                    spawned = true;
                }
            }
        }
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

    public void reset()
    {
        powerups.clear();
        collectedPowerups = new ArrayList<>();
    }

    private void readJsonFromFile()
    {
        try
        {
            Type collectionType = new TypeToken<ArrayList<Art>>(){}.getType();
            String filename = Constants.CURRENT_CONTEXT.getFilesDir() + "/" + "a0.json";
            JsonReader js = new JsonReader(new FileReader(filename));
            Gson gson = new Gson();
            artsList  = gson.fromJson(js, collectionType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
