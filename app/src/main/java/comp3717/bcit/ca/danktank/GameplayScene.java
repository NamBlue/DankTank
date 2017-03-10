package comp3717.bcit.ca.danktank;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import java.io.IOException;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class GameplayScene implements Scene
{
    //Used to set the bounds for the gameover text box
    private Rect rectBound = new Rect();
    private Rect pauseButton, moveLeftButton, moveRightButton,
            moveUpButton, moveDownButton, fireButton;
    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private boolean movingPlayer = false;
    private boolean gameOver = false;
    private long gameOverTime;
    private OrientationData orientationData;
    //speed to move the player faster as it is more tilted, tracks time elapsed between frames
    private long frameTime;
    //For storing current player direction
    private Enums.MoveDirection moveDirection;
    private MediaPlayer mySound;

    public GameplayScene()
    {
        player = new RectPlayer(new Rect(100, 100, 225, 225), Color.rgb(255, 0, 0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3 * Constants.SCREEN_HEIGHT/4);
        pauseButton = new Rect(Constants.SCREEN_WIDTH - 150, 0, Constants.SCREEN_WIDTH, 150);

        moveLeftButton = new Rect(50, Constants.SCREEN_HEIGHT - 350, 150, Constants.SCREEN_HEIGHT - 250);
        moveRightButton = new Rect(200, Constants.SCREEN_HEIGHT - 350, 300, Constants.SCREEN_HEIGHT - 250);
        moveUpButton = new Rect(125, Constants.SCREEN_HEIGHT - 475, 225, Constants.SCREEN_HEIGHT - 375);
        moveDownButton = new Rect(125, Constants.SCREEN_HEIGHT - 225, 225, Constants.SCREEN_HEIGHT - 125);
        fireButton = new Rect(Constants.SCREEN_WIDTH - 150, Constants.SCREEN_HEIGHT - 350, Constants.SCREEN_WIDTH - 50, Constants.SCREEN_HEIGHT - 250);
        moveDirection = Enums.MoveDirection.None;
        mySound = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.gameplay);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(50, 150, Color.BLACK);

        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
}

    public void reset()
    {
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3 * Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(50, 150, Color.BLACK);
        movingPlayer = false;
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
        mySound.start();
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
            obstacleManager.update();
            if (obstacleManager.playerCollide(player))
            {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);

        player.draw(canvas);

        obstacleManager.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(pauseButton, paint);
        canvas.drawRect(moveLeftButton, paint);
        canvas.drawRect(moveRightButton, paint);
        canvas.drawRect(moveUpButton, paint);
        canvas.drawRect(moveDownButton, paint);
        canvas.drawRect(fireButton, paint);
        if (gameOver)
        {
            //Paint paint = new Paint();
            paint.setTextSize(100);
            //paint.setColor(Color.BLUE);
            drawCentreText(canvas, paint, "Game Over");
        }
    }

    @Override
    public void terminate()
    {
        mySound.stop();
        try {
            mySound.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SceneManager.ACTIVE_SCENE = 0;
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
                    terminate();
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
