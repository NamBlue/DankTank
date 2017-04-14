package comp3717.bcit.ca.danktank.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.R;
import comp3717.bcit.ca.danktank.managers.AnimationManager;

/**
 * Created by namblue on 4/4/2017.
 */

public class Powerup implements GameObject
{
    private String Name, Descriptn, Address, city;
    private Rect rectangle;
    private Animation idle;
    private AnimationManager animationManager;

    public Powerup(String name, String desc, String address, String city, Rect rect)
    {
        if(name == null)
        {
            name = "No Name";
        }
        if(desc == null)
        {
            desc = "No Summary Exists";
        }
        else if (desc.isEmpty())
        {
            desc = "No Summary Exists";
        }
        if(address == null)
        {
            address = "No Address Exists";
        }
        if (city == null )
        {
            city = "No City Exists";
        }
        this.Name = name;
        if(desc.length() > 130)
        {
            desc = desc.substring(0, 130) + "...";
        }
        this.Descriptn = desc;
        this.Address = address;
        this.city = city;
        this.rectangle = rect;
        BitmapFactory bitmapFactory = new BitmapFactory();
        Bitmap idleImg = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.flag);
        idle = new Animation(new Bitmap[]{idleImg}, 5f);
        animationManager = new AnimationManager(new Animation[]{idle});
    }

    public boolean collided(Rect object)
    {
        return Rect.intersects(rectangle, object);
    }

    @Override
    public void draw(Canvas canvas)
    {
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update()
    {
        animationManager.playAnimation(0);
        animationManager.update();
    }

    @Override
    public void reset()
    {

    }

    public String getName(){
        return this.Name;
    }

    public String getAddress(){
        return this.Address;
    }

    public String getDescriptn() {
        return this.Descriptn;
    }

    public String getCity(){
        return this.city;
    }
}