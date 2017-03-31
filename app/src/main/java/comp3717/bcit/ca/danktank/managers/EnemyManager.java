package comp3717.bcit.ca.danktank.managers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.objects.Bullet;
import comp3717.bcit.ca.danktank.objects.Enemy;

/**
 * Created by namblue on 3/24/2017.
 */

public class EnemyManager
{
    private ArrayList<Enemy> enemies;
    private BulletManager bulletManager;
    private Random random;
    public int enemySize;
    private int frames;

    public EnemyManager()
    {
        random = new Random();
        enemies = new ArrayList<>();
        enemySize = 0;
        frames = 0;
    }

    public boolean killEnemy(Enemy enemy)
    {
        return enemy.die();
    }

    public void update()
    {
        if(enemies.size() < enemySize && frames > 30)
        {
            int x = (int)((random.nextFloat() * (Constants.SCREEN_WIDTH - Constants.ENEMY_SIZE)) + (Constants.ENEMY_SIZE / 2));
            int y = (int)((random.nextFloat() * (Constants.SCREEN_HEIGHT - Constants.ENEMY_SIZE) / 2) + (Constants.ENEMY_SIZE / 2));
            enemies.add(new Enemy(new Rect(0, 0, Constants.PLAYER_SIZE, Constants.ENEMY_SIZE), Color.rgb(255, 0, 0)));
            enemies.get(enemies.size() - 1).update(new Point(x, y));
            frames = 0;
        }
        for(Enemy enemy: enemies)
        {
            if(enemy.dieFrames > 30)
            {
                enemies.remove(enemy);
            }
        }
        frames ++;
    }

    public boolean collided(Rect object)
    {
        for(Enemy enemy : enemies)
        {
            if(enemy.collided(object))
            {
                return true;
            }
        }
        return false;
    }

    public void reset()
    {
        enemies.clear();
    }

    public void draw(Canvas canvas)
    {
        for(Enemy enemy: enemies)
        {
            enemy.draw(canvas);
        }
    }

    public ArrayList<Enemy> getEnemies()
    {
        return enemies;
    }
}
