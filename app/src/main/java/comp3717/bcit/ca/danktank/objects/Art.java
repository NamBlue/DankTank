package comp3717.bcit.ca.danktank.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.R;
import comp3717.bcit.ca.danktank.managers.AnimationManager;

/**
 * Created by panda on 2017-04-02.
 */

public class Art implements GameObject {

    private Rect rectangle;
    private String name;
    private String description;
    private String address;
    private String city;
    private Bitmap artImg;
    private boolean pickedUp;
    private boolean visible;
    private AnimationManager animationManager;
    private Animation dropped;

    public Art(String name, String description, String address, String city)
    {
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        BitmapFactory bitmapFactory = new BitmapFactory();
        this.artImg = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.flag);
        this.dropped = new Animation(new Bitmap[]{artImg}, 5f);
        this.pickedUp = false;
        this.visible = false;
        animationManager = new AnimationManager(new Animation[]{dropped});
    }

    public void setRectangle(Rect rect){
        this.rectangle = rect;
    }

    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        animationManager.update();
    }

    @Override
    public void reset() {

    }
}
