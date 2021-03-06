package comp3717.bcit.ca.danktank.scenes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.ArrayList;

import comp3717.bcit.ca.danktank.managers.BulletManager;
import comp3717.bcit.ca.danktank.managers.LevelManager;
import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.Enums;
import comp3717.bcit.ca.danktank.managers.EnemyManager;
import comp3717.bcit.ca.danktank.managers.MusicManager;
import comp3717.bcit.ca.danktank.managers.PowerupManager;
import comp3717.bcit.ca.danktank.managers.SFX_Manager;
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
    //The GUI buttons
    private Rect pauseButton, fireButton, dpadButton;
    //The zone that it will detect the touch
    private Rect pauseZone, moveLeftZone, moveRightZone,
            moveUpZone, moveDownZone, fireZone;
    private Player player;
    private Point playerPoint;
    private BulletManager bulletManager;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PowerupManager powerupManager;
    private boolean movingPlayer = false;
    private boolean playerFiring = false;
    private boolean gameOver = false;
    private boolean win = false;
    public static boolean pause = false;
    private long gameOverTime;
    //speed to move the player faster as it is more tilted, tracks time elapsed between frames
    private long frameTime;
    //For storing current player direction
    private Enums.MoveDirection moveDirection;
    private Bitmap up_image;
    private Bitmap down_image;
    private Bitmap right_image;
    private Bitmap left_image;
    private Bitmap pause_image;
    private Bitmap fire_image;
    private Bitmap dpad_image;
    private float gameOverTextPos;

    private ArrayList<Enemy> enemies;
    public static int score = 0;
    private Paint scorePaint, winLosePaint, controlsPaint;
    private ArrayList<Rect> walls;
    private int gameoverFrames, winFrames;
    public static int level = 1;

    public GameplayScene()
    {
        Constants.PLAYER_SIZE = (int)(Constants.SCREEN_WIDTH * .08);
        Constants.ENEMY_SIZE = (int)(Constants.SCREEN_WIDTH * .08);
        Constants.PLAYER_SPEED = (int)(Constants.SCREEN_WIDTH * .010);
        Constants.BULLET_SPEED = (Constants.SCREEN_WIDTH * .015f);
        Constants.BULLET_WIDTH = (int)(Constants.SCREEN_WIDTH * .015);
        Constants.BULLET_HEIGHT = (int)(Constants.SCREEN_WIDTH * .035);
        Constants.BULLET_GAP = (int)(Constants.SCREEN_WIDTH * .03);
        Constants.POWERUP_SIZE = (int)(Constants.SCREEN_WIDTH * .08);
        Constants.ENEMY_SPEED = (int)(Constants.SCREEN_WIDTH * .008);

        gameOverTextPos = 22;

        player = new Player(new Rect(0, 0, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE), Color.rgb(255, 0, 0));
        BitmapFactory bitmapFactory = new BitmapFactory();
        pause_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pause_button);
        fire_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.fire_button);
        dpad_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pad);

        fireButton = new Rect(Constants.SCREEN_WIDTH*49/60, Constants.SCREEN_HEIGHT*25/30, Constants.SCREEN_WIDTH*58/60, Constants.SCREEN_HEIGHT*28/30);
        pauseButton = new Rect(Constants.SCREEN_WIDTH*26/30, Constants.SCREEN_HEIGHT/30, Constants.SCREEN_WIDTH*29/30, Constants.SCREEN_HEIGHT*3/30);
        dpadButton = new Rect(Constants.SCREEN_WIDTH*0/30, (int)(Constants.SCREEN_HEIGHT*22.5/30), Constants.SCREEN_WIDTH*12/30, Constants.SCREEN_HEIGHT*30/30);

        moveLeftZone = new Rect(0, (int)(Constants.SCREEN_HEIGHT*24.50/30), Constants.SCREEN_WIDTH*4/30, (int)(Constants.SCREEN_HEIGHT*28/30));
        moveRightZone = new Rect(Constants.SCREEN_WIDTH*8/30, (int)(Constants.SCREEN_HEIGHT*24.50/30), Constants.SCREEN_WIDTH*12/30, (int)(Constants.SCREEN_HEIGHT*28/30));
        moveUpZone = new Rect((int)(Constants.SCREEN_WIDTH*3/30), (int)(Constants.SCREEN_HEIGHT*22/30), (int)(Constants.SCREEN_WIDTH*9/30), (int)(Constants.SCREEN_HEIGHT*25.5/30));
        moveDownZone = new Rect((int)(Constants.SCREEN_WIDTH*3/30), Constants.SCREEN_HEIGHT*27/30, (int)(Constants.SCREEN_WIDTH*9/30), Constants.SCREEN_HEIGHT*30/30);
        fireZone = new Rect(Constants.SCREEN_WIDTH*42/60, (int)(Constants.SCREEN_HEIGHT*22.5/30), Constants.SCREEN_WIDTH*60/60, Constants.SCREEN_HEIGHT*30/30);
        pauseZone = new Rect(Constants.SCREEN_WIDTH*24/30, 0, Constants.SCREEN_WIDTH*30/30, Constants.SCREEN_HEIGHT*4/30);

        moveDirection = Enums.MoveDirection.Up;
        gameoverFrames = 0;
        winFrames = 0;

        bulletManager = new BulletManager();
        powerupManager = new PowerupManager();
        levelManager = new LevelManager(level);
        walls = levelManager.getWallTiles();
        enemyManager = new EnemyManager(levelManager.getSpawnpoints(),
                levelManager.getWallTiles(),
                levelManager.getTotalEnemiesForThisLevel(),
                levelManager.getMaxEnemySize());
        playerPoint = new Point(levelManager.getPlayerSpawn().centerX(), levelManager.getPlayerSpawn().centerY());
        player.update(playerPoint, moveDirection);

        enemies = enemyManager.getEnemies();

        frameTime = System.currentTimeMillis();
        winLosePaint = new Paint();
        winLosePaint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));
        winLosePaint.setColor(Color.BLUE);
        winLosePaint.setTextAlign(Paint.Align.CENTER);
        controlsPaint = new Paint();
        scorePaint = new Paint();
        scorePaint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));
        scorePaint.setColor(Color.BLUE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
    }

    public void reset()
    {
        if(!pause)
        {
            winFrames = 0;
            gameoverFrames = 0;
            levelManager = new LevelManager(level);
            walls = levelManager.getWallTiles();
            enemyManager = new EnemyManager(levelManager.getSpawnpoints(),
                    levelManager.getWallTiles(),
                    levelManager.getTotalEnemiesForThisLevel(),
                    levelManager.getMaxEnemySize());
            enemies = enemyManager.getEnemies();
            player.reset();
            playerPoint = new Point(levelManager.getPlayerSpawn().centerX(), levelManager.getPlayerSpawn().centerY());
            moveDirection = Enums.MoveDirection.Up;
            player.update(playerPoint, moveDirection);
            bulletManager = new BulletManager();
            movingPlayer = false;
            playerFiring = false;
            enemyManager.reset();
            win = false;
            gameOver = false;
            score = 0;
            powerupManager.reset();
            frameTime = System.currentTimeMillis();
            SFX_Manager.warp();
        }
        else
        {
            pause = false;
        }
    }

    @Override
    public void update()
    {
        if (!gameOver && !win && !pause)
        {
            //Keeps the time elapsed to the right time when game is resumed
            if(frameTime < Constants.INIT_TIME)
            {
                frameTime = Constants.INIT_TIME;
            }

            int elapsedTine = (int)(System.currentTimeMillis() - frameTime);
            frameTime = System.currentTimeMillis();

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
                //Bounding player to screen
                if(playerPoint.x < 0 + Constants.PLAYER_SIZE / 2)
                {
                    playerPoint.x = 0 + Constants.PLAYER_SIZE / 2;
                }
                else if (playerPoint.x > Constants.SCREEN_WIDTH - Constants.PLAYER_SIZE / 2)
                {
                    playerPoint.x = Constants.SCREEN_WIDTH - Constants.PLAYER_SIZE / 2;
                }
                if(playerPoint.y < 0 + Constants.PLAYER_SIZE / 2)
                {
                    playerPoint.y = 0 + Constants.PLAYER_SIZE / 2;
                }
                else if (playerPoint.y > Constants.SCREEN_HEIGHT - Constants.PLAYER_SIZE / 2)
                {
                    playerPoint.y = Constants.SCREEN_HEIGHT - Constants.PLAYER_SIZE / 2;
                }
            }

            Rect temp = new Rect(playerPoint.x - (Constants.PLAYER_SIZE /2),
                    playerPoint.y - (Constants.PLAYER_SIZE /2),
                    playerPoint.x + (Constants.PLAYER_SIZE /2),
                    playerPoint.y + (Constants.PLAYER_SIZE /2));

            Rect wallTemp = new Rect(playerPoint.x - (Constants.PLAYER_SIZE /2),
                    playerPoint.y - (Constants.PLAYER_SIZE /2) + (int)(Constants.PLAYER_SIZE * .10),
                    playerPoint.x + (Constants.PLAYER_SIZE /2),
                    playerPoint.y + (Constants.PLAYER_SIZE /2) - (int)(Constants.PLAYER_SIZE * .10));

            //undo the movements if collided
            if (enemyManager.collided(temp) || levelManager.collided(wallTemp))
            {
                switch(moveDirection)
                {
                    case Left:
                        playerPoint.set(playerPoint.x + Constants.PLAYER_SPEED, playerPoint.y);
                        break;
                    case Right:
                        playerPoint.set(playerPoint.x - Constants.PLAYER_SPEED, playerPoint.y);
                        break;
                    case Up:
                        playerPoint.set(playerPoint.x, playerPoint.y + Constants.PLAYER_SPEED);
                        break;
                    case Down:
                        playerPoint.set(playerPoint.x, playerPoint.y - Constants.PLAYER_SPEED);
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
                        bulletManager.addPlayerBullet(playerPoint.x - Constants.BULLET_GAP, playerPoint.y, moveDirection);
                        break;
                    case Right:
                        bulletManager.addPlayerBullet(playerPoint.x + Constants.BULLET_GAP, playerPoint.y, moveDirection);
                        break;
                    case Up:
                        bulletManager.addPlayerBullet(playerPoint.x, playerPoint.y - Constants.BULLET_GAP, moveDirection);
                        break;
                    case Down:
                        bulletManager.addPlayerBullet(playerPoint.x, playerPoint.y + Constants.BULLET_GAP, moveDirection);
                        break;
                    default:
                        break;
                }
                playerFiring = false;
            }

            player.update(playerPoint, moveDirection);
            bulletManager.update();
            if (bulletManager.enemyCollided(player.getRectangle()))
            {
                player.dying = true;
                gameOver = true;
                gameOverTextPos = 22;
                MusicManager.getInstance().playGameOver();
                gameOverTime = System.currentTimeMillis();
                SFX_Manager.explode();
            }

            for(Enemy enemy: enemies)
            {
                if (!enemy.die && bulletManager.collided(enemy.getRectangle()))
                {
                    if(enemyManager.killEnemy(enemy))
                    {
                        score += 100;
                        SFX_Manager.explode();
                    }
                }
                else if(enemy.hasLockon(player.getRectangle()))
                {
                    switch(enemy.getAnimationState())
                    {
                        case 2:
                        case 6:
                            bulletManager.addEnemyBullet(enemy.getRectangle().centerX() - Constants.BULLET_GAP, enemy.getRectangle().centerY(), Enums.MoveDirection.Left);
                            break;
                        case 3:
                        case 7:
                            bulletManager.addEnemyBullet(enemy.getRectangle().centerX() + Constants.BULLET_GAP, enemy.getRectangle().centerY(), Enums.MoveDirection.Right);
                            break;
                        case 0:
                        case 4:
                            bulletManager.addEnemyBullet(enemy.getRectangle().centerX(), enemy.getRectangle().centerY() - Constants.BULLET_GAP, Enums.MoveDirection.Up);
                            break;
                        case 1:
                        case 5:
                            bulletManager.addEnemyBullet(enemy.getRectangle().centerX(), enemy.getRectangle().centerY() + Constants.BULLET_GAP, Enums.MoveDirection.Down);
                            break;
                        default:
                            break;
                    }
                    SFX_Manager.fire();
                }

            }

            Rect tempRect = enemyManager.popDiePoint();
            if(tempRect != null)
            {
                powerupManager.spawnPowerup(tempRect);
            }
            if(powerupManager.collided(player.getRectangle()))
            {
                score += 100;
                SFX_Manager.pickup();
            }
            powerupManager.update();
            enemyManager.update();

            bulletManager.collided(walls);
            if(enemyManager.getEnemiesDied() == levelManager.getTotalEnemiesForThisLevel())
            {
                win = true;
                //powerupManager.collectDroppedPowerups(); //Disable automatic collection of powerups
                gameOverTime = System.currentTimeMillis();
                MusicManager.getInstance().playVictory();
            }
        }
        else if (gameOver)
        {
            player.update(playerPoint, moveDirection);
            if (gameOverTextPos >  10)
            {
                gameOverTextPos -= .07f;
            }
            ++gameoverFrames;
        }
        else if(win)
        {
            ++winFrames;
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        levelManager.draw(canvas);
        player.draw(canvas);

        bulletManager.draw(canvas);

        powerupManager.draw(canvas);
        enemyManager.draw(canvas);

        canvas.drawBitmap(pause_image, null, pauseButton, controlsPaint);
        canvas.drawBitmap(fire_image, null, fireButton, controlsPaint);
        canvas.drawBitmap(dpad_image, null, dpadButton, controlsPaint);
        if (gameOver)
        {
            winLosePaint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));
            canvas.drawText("Game Over", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT * gameOverTextPos / 20, winLosePaint);
        }
        if (win)
        {
            winLosePaint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));
            canvas.drawText("You win!", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT * 10 / 20, winLosePaint);
            winLosePaint.setTextSize((int)(Constants.SCREEN_WIDTH * .06));
            canvas.drawText("New Westminster is safe!", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT * 12 / 20, winLosePaint);
            canvas.drawText("but for how long...", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT * 13 / 20, winLosePaint);
            canvas.drawText("Press anywhere to continue", Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT * 14 / 20, winLosePaint);
            winLosePaint.setTextSize((int)(Constants.SCREEN_WIDTH * .1));
        }
        canvas.drawText("" + score, Constants.SCREEN_WIDTH * 0.10f, Constants.SCREEN_HEIGHT * 0.10f, winLosePaint);
    }

    @Override
    public void onExit()
    {

    }

    @Override
    public void receiveTouch(MotionEvent event)
    {
        int maskedAction = event.getActionMasked();
        switch (maskedAction)
        {
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && !win && !player.startingState)
                {
                    /* Decprecated - for drag controls
                    if (player.getRectangle().contains((int) event.getX(), (int) event.getY()))
                    {
                        movingPlayer = true;
                    }
                    */

                    if (fireZone.contains((int) event.getX(), (int) event.getY()))
                    {
                        playerFiring = true;
                    }

                    if (moveLeftZone.contains((int) event.getX(), (int) event.getY()))
                    {
                        movingPlayer = true;
                        moveDirection = Enums.MoveDirection.Left;
                    }

                    if (moveRightZone.contains((int) event.getX(), (int) event.getY()))
                    {
                        movingPlayer = true;
                        moveDirection = Enums.MoveDirection.Right;
                    }

                    if (moveUpZone.contains((int) event.getX(), (int) event.getY()))
                    {
                        movingPlayer = true;
                        moveDirection = Enums.MoveDirection.Up;
                    }

                    if (moveDownZone.contains((int) event.getX(), (int) event.getY()))
                    {
                        movingPlayer = true;
                        moveDirection = Enums.MoveDirection.Down;
                    }
                    //Added by harman to test the pause button
                    if (pauseZone.contains((int) event.getX(), (int) event.getY()))
                    {
                        pause = true;
                        SceneManager.ACTIVE_SCENE = 3;
                    }
                }
                else if (gameOver && gameoverFrames >= Constants.GAMEOVER_TIME)
                {
                    pause = false;
                    SceneManager.ACTIVE_SCENE = 2;
                    MusicManager.getInstance().playTitle();
                }
                else if(win && winFrames >= Constants.GAMEOVER_TIME)
                {
                    pause = false;
                    //powerupManager.collectDroppedPowerups();
                    SceneManager.ACTIVE_SCENE = 6;
                }
                /* Deprecated - for drag controls
                case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer)
                {
                    playerPoint.set((int) event.getX(), (int) event.getY());
                }
                break;
                */
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if(!gameOver && !win && !player.startingState)
                {
                    /* Decprecated - for drag controls
                    if (player.getRectangle().contains((int) event.getX(), (int) event.getY()))
                    {
                        movingPlayer = true;
                    }
                    */
                    int pointerIndex = event.getActionIndex();
                    if (fireButton.contains((int) event.getX(pointerIndex), (int) event.getY(pointerIndex)))
                    {
                        playerFiring = true;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }
}