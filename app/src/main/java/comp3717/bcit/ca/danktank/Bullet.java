package comp3717.bcit.ca.danktank;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.provider.Settings;

/**
 * Created by NamBlue on 1/19/2017.
 */

public class Bullet implements GameObject
{
    //the obstacle will consist of two rectangles with a gap in-between
    private Rect rectangle;
    private int color;
    private Enums.MoveDirection direction;
    private float speed;

    public Rect getRectangle()
    {
        return rectangle;
    }

    public Bullet(int color, int startX, int startY, Enums.MoveDirection moveDirection)
    {
        speed = Constants.BULLET_SPEED;
        direction = moveDirection;
        this.color = color;
        int left = 0, right = 0, top = 0, bottom = 0;
        switch (moveDirection)
        {
            case Up:
                left = startX  - (Constants.BULLET_WIDTH / 2);
                right = startX + (Constants.BULLET_WIDTH / 2);
                top = startY + Constants.BULLET_HEIGHT;
                bottom = startY;
                break;
            case Down:
                left = startX - (Constants.BULLET_WIDTH / 2);
                top = startY;
                right  = startX + (Constants.BULLET_WIDTH / 2);
                bottom = startY + Constants.BULLET_HEIGHT;
                break;
            case Left:
                left = startX - (Constants.BULLET_HEIGHT);
                top = startY - (Constants.BULLET_WIDTH / 2);
                right  = startX;
                bottom = startY + Constants.BULLET_WIDTH / 2;
                break;
            case Right:
                left = startX;
                top = startY - (Constants.BULLET_WIDTH / 2);
                right  = startX - (Constants.BULLET_HEIGHT);
                bottom = startY + Constants.BULLET_WIDTH / 2;
                break;
        }
        this.rectangle = new Rect(left, top, right, bottom);
    }

    public boolean playerCollided(RectPlayer player)
    {
        return Rect.intersects(rectangle, player.getRectangle());
    }

    private void move()
    {
        switch(direction)
        {
            case Up:
                rectangle.top -= speed;
                rectangle.bottom -= speed;
                break;
            case Down:
                rectangle.top += speed;
                rectangle.bottom += speed;
                break;
            case Right:
                rectangle.left += speed;
                rectangle.right += speed;
                break;
            case Left:
                rectangle.left -= speed;
                rectangle.right -= speed;
                break;
            default:
                break;
        }
        System.out.println(direction);
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
        move();
    }

    @Override
    public void reset()
    {

    }

}
