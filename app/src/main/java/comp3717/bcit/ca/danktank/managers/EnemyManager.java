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
import comp3717.bcit.ca.danktank.objects.Player;

/**
 * Created by namblue on 3/24/2017.
 */

public class EnemyManager
{
    private ArrayList<Enemy> enemies;
    private Random random;
    private int enemySize;
    private int totalEnemies;
    private int frames;
    private ArrayList<Rect> spawnPoints, diePoints;
    private int enemiesDied;

    public EnemyManager(ArrayList<Rect> spawnPoints, ArrayList<Rect> walls, int totalEnemies, int enemySize)
    {
        random = new Random();
        enemies = new ArrayList<>();
        this.enemySize = enemySize;
        this.totalEnemies = totalEnemies;
        frames = 0;
        enemiesDied = 0;
        this.spawnPoints = spawnPoints;
        diePoints = new ArrayList<>();
        Enemy.setWalls(walls);
    }

    public boolean killEnemy(Enemy enemy)
    {
        return enemy.die();
    }

    public void update()
    {
        Enemy dyingEnemy = null;
        if(enemies.size() < enemySize && frames > 60 && enemies.size() + enemiesDied < totalEnemies)
        {
            int z = random.nextInt(spawnPoints.size());
            Rect temp = spawnPoints.get(z);
            if (!collided(temp) && !Player.getRectangle().intersect(temp))
            {
                enemies.add(new Enemy(new Rect(0, 0, Constants.ENEMY_SIZE, Constants.ENEMY_SIZE), Color.rgb(255, 0, 0)));
                enemies.get(enemies.size() - 1).update(new Point(temp.centerX(), temp.centerY()));
                SFX_Manager.warp();
            }
            frames = 0;
        }
        for(int i = 0; i < enemies.size(); ++i)
        {
            if(enemies.get(i).dieFrames > 30)
            {
                dyingEnemy = enemies.get(i);
            }
            enemies.get(i).update();
            for(int j = 0; j < enemies.size(); ++j)
            {
                if (i != j)
                {
                    if(enemies.get(i).collided(enemies.get(j).getRectangle()))
                    {
                        enemies.get(i).undoMove();
                    }
                }
            }
        }
        if(dyingEnemy != null)
        {
            diePoints.add(dyingEnemy.getRectangle());
            enemies.remove(dyingEnemy);
            enemiesDied++;
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
        enemiesDied = 0;
        enemies.clear();
        diePoints.clear();
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

    public int getEnemiesDied()
    {
        return enemiesDied;
    }
}
