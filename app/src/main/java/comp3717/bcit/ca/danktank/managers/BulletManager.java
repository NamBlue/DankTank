package comp3717.bcit.ca.danktank.managers;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

import comp3717.bcit.ca.danktank.objects.Bullet;
import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.Enums;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class BulletManager
{
    //Higher index = lower on screen = higher y value
    private ArrayList<Bullet> playerBullets;
    //Gap between playerBullets
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private long startTime;
    private long initTime;



    public BulletManager(int obstacleGap, int obstacleHeight, int color)
    {
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();

        playerBullets = new ArrayList<>();

    }

    public void addPlayerBullet(int xStart, int yStart, Enums.MoveDirection moveDirection)
    {
        if(playerBullets.size() == 0)
            playerBullets.add(new Bullet(color, xStart, yStart, moveDirection));
    }

    public Rect getPlayerBullet()
    {
        if(playerBullets.size() > 0)
        {
            return playerBullets.get(0).getRectangle();
        }
        return null;
    }

    public boolean collided(Rect object)
    {
        for(Bullet bullet : playerBullets)
        {
            if(bullet.collided(object))
            {
                playerBullets.remove(bullet);
                return true;
            }
        }
        return false;
    }

    public boolean collided(ArrayList<Rect> objects)
    {
        for(Bullet bullet : playerBullets)
        {
            for (Rect rect : objects)
            {
                if (bullet.collided(rect))
                {
                    playerBullets.remove(bullet);
                    return true;
                }
            }
        }
        return false;
    }

    public void update()
    {
        if(playerBullets.size() > 0)
        {
            for (Bullet bullet : playerBullets)
            {
                bullet.update();
            }
            clearBullet();
        }
    }

    /**
     * Remove playerBullets that are off-screen
     */
    private void clearBullet()
    {
        int x = playerBullets.get(playerBullets.size() - 1).getRectangle().centerX(), y = playerBullets.get(playerBullets.size() - 1).getRectangle().centerY();
        if (y <= 0 || y >= Constants.SCREEN_HEIGHT || x <= 0 || x >= Constants.SCREEN_WIDTH)
        {
            playerBullets.remove(0);
        }
    }


    public void draw(Canvas canvas)
    {
        for(Bullet bullet : playerBullets)
        {
            bullet.draw(canvas);
        }
    }
}
