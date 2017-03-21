package comp3717.bcit.ca.danktank;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by NamBlue on 1/19/2017.
 */

public class Bullet implements GameObject
{
    //the obstacle will consist of two rectangles with a gap in-between
    private Rect rectangle;
    private int color;
    private Enums.MoveDirection direction;

    public Rect getRectangle()
    {
        return rectangle;
    }

    public Bullet(int color, int startX, int startY)
    {
        direction = Enums.MoveDirection.Down;
        this.color = color;
        this.rectangle = new Rect(startX, startY, startX + Constants.BULLET_WIDTH, startY + Constants.BULLET_HEIGHT);
    }

    public boolean playerCollided(RectPlayer player)
    {
        return Rect.intersects(rectangle, player.getRectangle());
    }

    public void move(Float amount)
    {
        switch(direction)
        {
            case Up:
                rectangle.top -= amount;
                rectangle.bottom -= amount;
            case Down:
                rectangle.top += amount;
                rectangle.bottom += amount;
            case Right:
                rectangle.left += amount;
                rectangle.right += amount;
            case Left:
                rectangle.left -= amount;
                rectangle.right -= amount;
            default:
                break;
        }
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
