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
    private Random random;
    public int enemySize;
    private int frames;
    private ArrayList<Rect> spawnPoints, diePoints, walls;

    public EnemyManager(ArrayList<Rect> spawnPoints, ArrayList<Rect> walls)
    {
        random = new Random();
        enemies = new ArrayList<>();
        enemySize = 0;
        frames = 0;
        this.spawnPoints = spawnPoints;
        diePoints = new ArrayList<>();
        this.walls = walls;
        Enemy.setWalls(walls);
    }

    public boolean killEnemy(Enemy enemy)
    {
        return enemy.die();
    }

    public void update()
    {
        Enemy dyingEnemy = null;
        if(enemies.size() < enemySize && frames > 60)
        {
            int z = random.nextInt(spawnPoints.size());
            Rect temp = spawnPoints.get(z);
            if (!collided(temp))
            {
                enemies.add(new Enemy(new Rect(0, 0, Constants.ENEMY_SIZE, Constants.ENEMY_SIZE), Color.rgb(255, 0, 0)));
                enemies.get(enemies.size() - 1).update(new Point(temp.centerX(), temp.centerY()));
            }
            frames = 0;
        }
        for(Enemy enemy: enemies)
        {
            if(enemy.dieFrames > 30)
            {
                dyingEnemy = enemy;
            }
            enemy.update();
        }
        if(dyingEnemy != null)
        {
            diePoints.add(dyingEnemy.getRectangle());
            enemies.remove(dyingEnemy);
        }
        frames ++;
    }

    public Rect popDiePoint()
    {
        Rect temp = null;
        if(diePoints.size() > 0)
        {
            temp =  diePoints.get(diePoints.size() - 1);
            diePoints.remove(diePoints.size() - 1);
        }
        return temp;
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
