package comp3717.bcit.ca.danktank.managers;

import android.graphics.Canvas;
import android.graphics.Color;
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
    private ArrayList<Bullet> enemyBullets;
    //Gap between playerBullets
    private int color;

    public BulletManager()
    {
        this.color = Color.BLACK;

        playerBullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();
    }

    public void addPlayerBullet(int xStart, int yStart, Enums.MoveDirection moveDirection)
    {
        if(playerBullets.size() == 0)
        {
            SFX_Manager.fire();
            playerBullets.add(new Bullet(color, xStart, yStart, moveDirection));
        }
    }

    public void addEnemyBullet(int xStart, int yStart, Enums.MoveDirection moveDirection)
    {
        enemyBullets.add(new Bullet(color, xStart, yStart, moveDirection));
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
            else
            {
                for(int i = 0; i < enemyBullets.size(); ++i)
                {
                    if (bullet.collided(enemyBullets.get(i).getRectangle()))
                    {
                        playerBullets.remove(bullet);
                        enemyBullets.remove(i);
                        SFX_Manager.impact();
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean enemyCollided(Rect object)
    {
        for(Bullet ebullet : enemyBullets)
        {
            if(ebullet.collided(object))
            {
                enemyBullets.remove(ebullet);
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
                    SFX_Manager.impact();
                    return true;
                }
            }
        }
        for(Bullet ebullet : enemyBullets)
        {
            for (Rect rect : objects)
            {
                if (ebullet.collided(rect))
                {
                    enemyBullets.remove(ebullet);
                    SFX_Manager.impact();
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
        if (enemyBullets.size() > 0)
        {
            for (Bullet bullet : enemyBullets)
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
        if(playerBullets.size() > 0)
        {
            int x = playerBullets.get(playerBullets.size() - 1).getRectangle().centerX(), y = playerBullets.get(playerBullets.size() - 1).getRectangle().centerY();
            if (y <= 0 || y >= Constants.SCREEN_HEIGHT || x <= 0 || x >= Constants.SCREEN_WIDTH)
            {
                playerBullets.remove(0);
                SFX_Manager.impact();
            }
        }
        if(enemyBullets.size() > 0)
        {
            int x = enemyBullets.get(enemyBullets.size() - 1).getRectangle().centerX(), y = enemyBullets.get(enemyBullets.size() - 1).getRectangle().centerY();
            if (y <= 0 || y >= Constants.SCREEN_HEIGHT || x <= 0 || x >= Constants.SCREEN_WIDTH)
            {
                enemyBullets.remove(0);
                SFX_Manager.impact();
            }
        }
    }


    public void draw(Canvas canvas)
    {
        for(Bullet bullet : playerBullets)
        {
            bullet.draw(canvas);
        }
        for(Bullet bullet : enemyBullets)
        {
            bullet.draw(canvas);
        }
    }
}
