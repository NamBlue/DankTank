package comp3717.bcit.ca.danktank.objects;

import android.graphics.Canvas;

/**
 * Created by NamBlue on 1/19/2017.
 */

public interface GameObject
{
    public void draw(Canvas canvas);
    public void update();
    public void reset();
}
