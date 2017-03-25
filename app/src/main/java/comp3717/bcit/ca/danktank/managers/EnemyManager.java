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

    public EnemyManager()
    {
        random = new Random();
        enemies = new ArrayList<>();
        enemySize = 0;
        int i = 0;
    }

    public void killEnemy(Enemy enemy)
    {
        enemies.remove(enemy);
    }

    public void update()
    {
        if(enemies.size() < enemySize)
        {
            int x = (int)((random.nextFloat() * (Constants.SCREEN_WIDTH - Constants.ENEMY_SIZE)) + (Constants.ENEMY_SIZE / 2));
            int y = (int)((random.nextFloat() * (Constants.SCREEN_HEIGHT - Constants.ENEMY_SIZE) / 2) + (Constants.ENEMY_SIZE / 2));
            enemies.add(new Enemy(new Rect(0, 0, Constants.PLAYER_SIZE, Constants.ENEMY_SIZE), Color.rgb(255, 0, 0)));
            enemies.get(enemies.size() - 1).update(new Point(x, y));
        }
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
