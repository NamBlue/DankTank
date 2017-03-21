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

        populateObstacles();
    }

    private void populateObstacles()
    {
        int currY = -5 * Constants.SCREEN_HEIGHT/4;

        while(currY < 0)
        {
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH));
            bullets.add(new Bullet(color, xStart, currY));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public boolean playerCollide(RectPlayer player)
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
        if(startTime < Constants.INIT_TIME)
        {
            startTime = Constants.INIT_TIME;
        }
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();

        //8000 ms to move through screen height, increase speed as time goes on 1000.0 = one seconds per speed up
        float speed = (float)(Math.sqrt(1 + (startTime - initTime) / 1000.0) * Constants.SCREEN_HEIGHT/8000.0f);

        for (Bullet bullet : bullets)
        {
            bullet.move(speed * elapsedTime);
        }
        if (bullets.get(bullets.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT)
        {
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH));
            bullets.add(0, new Bullet(color, xStart, bullets.get(0).getRectangle().top - obstacleHeight - obstacleGap));
            bullets.remove(bullets.size() - 1);
            score++;
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
