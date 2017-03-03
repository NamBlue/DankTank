package comp3717.bcit.ca.danktank;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by NamBlue on 1/19/2017.
 */

public class Obstacle implements GameObject
{
    //the obstacle will consist of two rectangles with a gap in-between
    private Rect rectangle;
    private int color;

    public Rect getRectangle()
    {
        return rectangle;
    }

    public Obstacle(int color, int startX, int startY)
    {

        this.color = color;
        this.rectangle = new Rect(startX, startY, startX + Constants.BULLET_WIDTH, startY + Constants.BULLET_HEIGHT);
    }

    public boolean playerCollided(RectPlayer player)
    {
        return Rect.intersects(rectangle, player.getRectangle());
    }

    public void incrementY(Float y)
    {
        rectangle.top += y;
        rectangle.bottom += y;
    }

    @Override
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update()
    {

    }

}
