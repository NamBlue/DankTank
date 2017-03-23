package comp3717.bcit.ca.danktank;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class BulletManager
{
    //Higher index = lower on screen = higher y value
    private ArrayList<Bullet> bullets;
    //Gap between bullets
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private long startTime;
    private long initTime;

    private int score = 0;

    public BulletManager(int obstacleGap, int obstacleHeight, int color)
    {
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        bullets = new ArrayList<>();

    }

    public void addBullet(int xStart, int yStart, Enums.MoveDirection moveDirection)
    {
        bullets.add(new Bullet(color, xStart, yStart, moveDirection));
    }

    public boolean playerCollide(Player player)
    {
        for(Bullet bullet : bullets)
        {
            if(bullet.playerCollided(player))
            {
                return true;
            }
        }
        return false;
    }

    public void update()
    {
        if(bullets.size() > 0)
        {
            for (Bullet bullet : bullets)
            {
                bullet.update();
            }
            clearBullet();
        }
    }

    /**
     * Remove bullets that are off-screen
     */
    private void clearBullet()
    {
        int x = bullets.get(bullets.size() - 1).getRectangle().centerX(), y = bullets.get(bullets.size() - 1).getRectangle().centerY();
        if (y <= 0 || y >= Constants.SCREEN_HEIGHT || x <= 0 || x >= Constants.SCREEN_WIDTH)
        {
            bullets.remove(0);
        }
    }


    public void draw(Canvas canvas)
    {
        for(Bullet bullet : bullets)
        {
            bullet.draw(canvas);
        }
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.BLUE);
        canvas.drawText("" + score, Constants.SCREEN_WIDTH * 0.10f, Constants.SCREEN_HEIGHT * 0.10f, paint);
    }
}
