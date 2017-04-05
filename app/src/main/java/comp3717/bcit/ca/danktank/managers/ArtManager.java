package comp3717.bcit.ca.danktank.managers;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.objects.Art;

/**
 * Created by panda on 2017-04-02.
 */

public class ArtManager {
    private ArrayList<Art> arts;

    public ArtManager(){
        arts = new ArrayList<Art>();
    }

    public  ArrayList<Art> getArts()
    {
        return arts;
    }

    public void reset()
    {
        arts.clear();
    }

    public void readJsonFromFile()
    {
        try
        {
            Type collectionType = new TypeToken<ArrayList<Art>>(){}.getType();
            String filename = Constants.CURRENT_CONTEXT.getFilesDir() + "/" + "a0.json";
            JsonReader js = new JsonReader(new FileReader(filename));
            Gson gson = new Gson();
            arts  = gson.fromJson(js, collectionType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("Values:", arts.toString());
    }

}
