package comp3717.bcit.ca.danktank.scenes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import java.io.IOException;
import java.util.ArrayList;

import comp3717.bcit.ca.danktank.managers.BulletManager;
import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.Enums;
import comp3717.bcit.ca.danktank.OrientationData;
import comp3717.bcit.ca.danktank.managers.EnemyManager;
import comp3717.bcit.ca.danktank.objects.Enemy;
import comp3717.bcit.ca.danktank.objects.Player;
import comp3717.bcit.ca.danktank.R;
import comp3717.bcit.ca.danktank.managers.SceneManager;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class GameplayScene implements Scene
{
    //Used to set the bounds for the gameover text box
    private Rect rectBound = new Rect();
    private Rect pauseButton, moveLeftButton, moveRightButton,
            moveUpButton, moveDownButton, fireButton;
    private Player player;
    private Point playerPoint;
    private BulletManager bulletManager;
    private EnemyManager enemyManager;
    private boolean movingPlayer = false;
    private boolean playerFiring = false;
    private boolean gameOver = false;
    private long gameOverTime;
    private OrientationData orientationData;
    //speed to move the player faster as it is more tilted, tracks time elapsed between frames
    private long frameTime;
    //For storing current player direction
    private Enums.MoveDirection moveDirection;
    private MediaPlayer mySound;
    private Bitmap up_image;
    private Bitmap down_image;
    private Bitmap right_image;
    private Bitmap left_image;
    private Bitmap pause_image;
    private Bitmap fire_image;
    private ArrayList<Enemy> enemies;

    public GameplayScene()
    {
        player = new Player(new Rect(0, 0, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE), Color.rgb(255, 0, 0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3 * Constants.SCREEN_HEIGHT/4);
        pauseButton = new Rect(Constants.SCREEN_WIDTH - 150, 0, Constants.SCREEN_WIDTH, 150);
        BitmapFactory bitmapFactory = new BitmapFactory();
        up_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.up_button);
        down_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.down_button);
        right_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.right_button);
        left_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.left_button);
        pause_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pause_button);
        fire_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fire_button);
        moveLeftButton = new Rect(50, Constants.SCREEN_HEIGHT - 350, 150, Constants.SCREEN_HEIGHT - 250);
        moveRightButton = new Rect(200, Constants.SCREEN_HEIGHT - 350, 300, Constants.SCREEN_HEIGHT - 250);
        moveUpButton = new Rect(125, Constants.SCREEN_HEIGHT - 475, 225, Constants.SCREEN_HEIGHT - 375);
        moveDownButton = new Rect(125, Constants.SCREEN_HEIGHT - 225, 225, Constants.SCREEN_HEIGHT - 125);
        fireButton = new Rect(Constants.SCREEN_WIDTH - 150, Constants.SCREEN_HEIGHT - 350, Constants.SCREEN_WIDTH - 50, Constants.SCREEN_HEIGHT - 250);
        moveDirection = Enums.MoveDirection.Up;
        mySound = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.gameplay);
        player.update(playerPoint);

        bulletManager = new BulletManager(50, 150, Color.BLACK);
        enemyManager = new EnemyManager();
        enemyManager.enemySize = 4;

        enemies = enemyManager.getEnemies();



        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
}

    public void reset()
    {
        player.reset();
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3 * Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        bulletManager = new BulletManager(50, 150, Color.BLACK);
        movingPlayer = false;
        playerFiring = false;
        orientationData.newGame();
    }


    private void drawCentreText(Canvas canvas, Paint paint, String text)
    {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(rectBound);
        int cHeight = rectBound.height();
        int cWidth = rectBound.width();
        paint.getTextBounds(text, 0, text.length(), rectBound);
        float x = cWidth / 2f - rectBound.width() / 2f - rectBound.left;
        float y = cHeight / 2f - rectBound.height() / 2f - rectBound.bottom;
        canvas.drawText(text, x ,y ,paint);
    }

    @Override
    public void update()
    {
        if (!gameOver)
        {
            //Keeps the time elapsed to the right time when game is resumed
            if(frameTime < Constants.INIT_TIME)
            {
                frameTime = Constants.INIT_TIME;
            }

            int elapsedTine = (int)(System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();

            //For gyroscope
            if (orientationData.getOrientation() != null && orientationData.getStartingOrientation() != null)
            {
                //index 1 of orientation is the pitch for Y axis movement
                //pitch goes from PI to -PI
                //roll goes from PI/2 to - PI/2 (Need to multiply by 2)
                float pitchDelta = orientationData.getOrientation()[1] - orientationData.getStartingOrientation()[1];
                float rollDelta = orientationData.getOrientation()[2] - orientationData.getStartingOrientation()[2];

                //1000f means 2000msec to move across the screen when fully tilted
                float xspeed = 2 * rollDelta * Constants.SCREEN_WIDTH/2000f;
                float yspeed = pitchDelta * Constants.SCREEN_HEIGHT/2000f;

                //5 is the pixel margin for error correction
                playerPoint.x += Math.abs(xspeed * elapsedTine) > 5 ? xspeed*elapsedTine : 0;
                playerPoint.y -= Math.abs(yspeed * elapsedTine) > 5 ? yspeed*elapsedTine : 0;
            }

            //For directional key controls
            if(movingPlayer)
            {
                switch(moveDirection)
                {
                    case Left:
                        playerPoint.set(playerPoint.x - Constants.PLAYER_SPEED, playerPoint.y);
                        break;
                    case Right:
                        playerPoint.set(playerPoint.x + Constants.PLAYER_SPEED, playerPoint.y);
                        break;
                    case Up:
                        playerPoint.set(playerPoint.x, playerPoint.y - Constants.PLAYER_SPEED);
                        break;
                    case Down:
                        playerPoint.set(playerPoint.x, playerPoint.y + Constants.PLAYER_SPEED);
                        break;
                    default:
                        break;
                }
            }

            if(playerFiring)
            {
                switch(moveDirection)
                {
                    case Left:
                        bulletManager.addBullet(playerPoint.x - Constants.BULLET_GAP, playerPoint.y, moveDirection);
                        break;
                    case Right:
                        bulletManager.addBullet(playerPoint.x + Constants.BULLET_GAP, playerPoint.y, moveDirection);
                        break;
                    case Up:
                        bulletManager.addBullet(playerPoint.x, playerPoint.y - Constants.BULLET_GAP, moveDirection);
                        break;
                    case Down:
                        bulletManager.addBullet(playerPoint.x, playerPoint.y + Constants.BULLET_GAP, moveDirection);
                        break;
                    default:
                        break;
                }
                playerFiring = false;
            }

            //Bounding player to screen
            if(playerPoint.x < 0)
            {
                playerPoint.x = 0;
            }
            else if (playerPoint.x > Constants.SCREEN_WIDTH)
            {
                playerPoint.x = Constants.SCREEN_WIDTH;
            }
            if(playerPoint.y < 0)
            {
                playerPoint.y = 0;
            }
            else if (playerPoint.y > Constants.SCREEN_HEIGHT)
            {
                playerPoint.y = Constants.SCREEN_HEIGHT;
            }

            player.update(playerPoint);
            bulletManager.update();
            if (bulletManager.collided(player.getRectangle()))
            {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
            for(Enemy enemy: enemies)
            {
                if (bulletManager.collided(enemy.getRectangle()))
                {
                    enemyManager.killEnemy(enemy);
                }
                else
                {
                    enemy.update();
                }
            }
            enemyManager.update();
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);

        player.draw(canvas);

        bulletManager.draw(canvas);
        enemyManager.draw(canvas);

        Paint paint = new Paint();
        canvas.drawBitmap(up_image, null, moveUpButton, new Paint());
        canvas.drawBitmap(down_image, null, moveDownButton, new Paint());
        canvas.drawBitmap(left_image, null, moveLeftButton, new Paint());
        canvas.drawBitmap(right_image, null, moveRightButton, new Paint());
        canvas.drawBitmap(pause_image, null, pauseButton, new Paint());
        canvas.drawBitmap(fire_image, null, fireButton, new Paint());
        if (gameOver)
        {
            //Paint paint = new Paint();
            paint.setTextSize(100);
            //paint.setColor(Color.BLUE);
            drawCentreText(canvas, paint, "Game Over");
        }
    }

    @Override
    public void onExit()
    {

    }

    @Override
    public void receiveTouch(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(!gameOver)
                {
                    /* Decprecated - for drag controls
                    if (player.getRectangle().contains((int) event.getX(), (int) event.getY()))
                    {
                        movingPlayer = true;
                    }
                    */

                    if (fireButton.contains((int) event.getX(), (int) event.getY()))
                    {
                        playerFiring = true;
                    }

                    if (moveLeftButton.contains((int) event.getX(), (int) event.getY()))
                    {
                        movingPlayer = true;
                        moveDirection = Enums.MoveDirection.Left;
                    }

                    if (moveRightButton.contains((int) event.getX(), (int) event.getY()))
                    {
                        movingPlayer = true;
                        moveDirection = Enums.MoveDirection.Right;
                    }

                    if (moveUpButton.contains((int) event.getX(), (int) event.getY()))
                    {
                        movingPlayer = true;
                        moveDirection = Enums.MoveDirection.Up;
                    }

                    if (moveDownButton.contains((int) event.getX(), (int) event.getY()))
                    {
                        movingPlayer = true;
                        moveDirection = Enums.MoveDirection.Down;
                    }
                    //Added by harman to test the pause button
                    if (pauseButton.contains((int) event.getX(), (int) event.getY()))
                    {
                        mySound.stop();
                        try {
                            mySound.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        SceneManager.ACTIVE_SCENE = 3;
                    }

                }
                else if (gameOver && System.currentTimeMillis() - gameOverTime >= Constants.GAMEOVER_TIME)
                {
                    reset();
                    gameOver = false;
                }
                break;
            /* Deprecated - for drag controls
            case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer)
                {
                    playerPoint.set((int) event.getX(), (int) event.getY());
                }
                break;
            */
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }
}
