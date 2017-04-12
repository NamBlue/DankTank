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
        for(int j = 0; j < playerBullets.size(); ++j)
        {
            if(playerBullets.get(j).collided(object))
            {
                playerBullets.remove(j);
                return true;
            }
            else //For bullet - bullet collision
            {
                for(int i = 0; i < enemyBullets.size(); ++i)
                {
                    if (playerBullets.get(j).collided(enemyBullets.get(i).getRectangle()))
                    {
                        playerBullets.remove(j);
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
        for(int i = 0; i < enemyBullets.size(); ++i)
        {
            if(enemyBullets.get(i).collided(object))
            {
                enemyBullets.remove(i);
                return true;
            }
        }
        return false;
    }

    public void collided(ArrayList<Rect> objects)
    {
        for(int i = 0; i < playerBullets.size(); ++i)
        {
            boolean collided = false;
            for (Rect rect : objects)
            {
                if (playerBullets.get(i).collided(rect))
                {
                    playerBullets.remove(i);
                    SFX_Manager.impact();
                    collided = true;
                    break;
                }
            }
            if(collided)
            {
                --i;
            }
        }
        for(int i = 0; i < enemyBullets.size(); ++i)
        {
            boolean collided = false;
            for (Rect wall : objects)
            {
                if (enemyBullets.get(i).collided(wall))
                {
                    enemyBullets.remove(i);
                    SFX_Manager.impact();
                    collided = true;
                    break;
                }
            }
            if(collided)
            {
                --i;
            }
        }
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
            for(int i = 0; i < playerBullets.size(); ++i)
            {
                int x = playerBullets.get(i).getRectangle().centerX(), y = playerBullets.get(i).getRectangle().centerY();
                if (y <= 0 || y >= Constants.SCREEN_HEIGHT || x <= 0 || x >= Constants.SCREEN_WIDTH)
                {
                    playerBullets.remove(i);
                    --i;
                    SFX_Manager.impact();
                }
            }
        }
        if(enemyBullets.size() > 0)
        {
            for(int i = 0; i < enemyBullets.size(); ++i)
            {
                int x = enemyBullets.get(i).getRectangle().centerX(), y = enemyBullets.get(i).getRectangle().centerY();
                if (y <= 0 || y >= Constants.SCREEN_HEIGHT || x <= 0 || x >= Constants.SCREEN_WIDTH)
                {
                    enemyBullets.remove(i);
                    --i;
                    SFX_Manager.impact();
                }
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
