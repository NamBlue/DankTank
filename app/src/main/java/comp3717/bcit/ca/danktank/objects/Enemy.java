package comp3717.bcit.ca.danktank.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.Enums;
import comp3717.bcit.ca.danktank.R;
import comp3717.bcit.ca.danktank.managers.AnimationManager;

/**
 * Created by NamBlue on 1/19/2017.
 */

public class Enemy implements GameObject
{
    private Rect rectangle;
    private int color;
    private Animation idleUp, idleDown, idleLeft, idleRight;
    private Animation walkLeft, walkRight, walkUp, walkDown;
    private Animation explode, spawn;
    private AnimationManager animationManager;
    private int animationState;
    private static ArrayList<Rect> walls;
    private Enums.MoveDirection moveDirection;
    private int frames;
    private boolean readyToFire;
    public boolean die;
    public boolean spawning;
    public int dieFrames;
    public int spawnFrames;
    private Point oldPoint;

    public Rect getRectangle()
    {
        return rectangle;
    }

    public Enemy(Rect rectangle, int color)
    {
        readyToFire = false;
        dieFrames = 0;
        spawnFrames = 0;
        frames = 0;
        spawning = true;
        die = false;
        this.rectangle = rectangle;
        this.color = color;
        animationState = 1;
        moveDirection = Enums.MoveDirection.Idle;
        //For Decoding, Producing, Modifying Bitmaps etc.
        BitmapFactory bitmapFactory = new BitmapFactory();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        //Make sure image names are all lowercase or will cause errors!
        Bitmap idleImg = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.e_idle, options);
        Bitmap walk1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.e_move1, options);
        Bitmap walk2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.e_move2, options);
        Bitmap walk3 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.e_move3, options);
        Bitmap explode1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explode1, options);
        Bitmap explode2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explode2, options);
        Bitmap explode3 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explode3, options);
        Bitmap spawn1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.spawn1, options);
        Bitmap spawn2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.spawn2, options);
        Bitmap spawn3 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.spawn3, options);
        Bitmap spawn4 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.spawn4, options);
        explode = new Animation(new Bitmap[]{explode1, explode2, explode3},0.5f);
        spawn = new Animation(new Bitmap[]{spawn1, spawn2, spawn3, spawn4},0.10f);
        idleUp = new Animation(new Bitmap[]{idleImg}, 5f);
        walkUp = new Animation(new Bitmap[]{walk1, walk2, walk3, idleImg}, 0.5f);

        //walkleft requires to reflect the image on the vertical axis.
        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        idleImg = Bitmap.createBitmap(idleImg, 0, 0, idleImg.getWidth(), idleImg.getHeight(), matrix, false);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), matrix, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), matrix, false);
        walk3 = Bitmap.createBitmap(walk3, 0, 0, walk3.getWidth(), walk3.getHeight(), matrix, false);
        idleRight = new Animation(new Bitmap[]{idleImg}, 5);
        walkRight = new Animation(new Bitmap[]{walk1, walk2, walk3, idleImg}, 0.5f);

        matrix.setRotate(-180);
        idleImg = Bitmap.createBitmap(idleImg, 0, 0, idleImg.getWidth(), idleImg.getHeight(), matrix, false);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), matrix, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), matrix, false);
        walk3 = Bitmap.createBitmap(walk3, 0, 0, walk3.getWidth(), walk3.getHeight(), matrix, false);
        idleLeft = new Animation(new Bitmap[]{idleImg}, 5);
        walkLeft = new Animation(new Bitmap[]{walk1, walk2, walk3, idleImg}, 0.5f);

        matrix.setRotate(-90);
        idleImg = Bitmap.createBitmap(idleImg, 0, 0, idleImg.getWidth(), idleImg.getHeight(), matrix, false);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), matrix, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), matrix, false);
        walk3 = Bitmap.createBitmap(walk3, 0, 0, walk3.getWidth(), walk3.getHeight(), matrix, false);
        idleDown  = new Animation(new Bitmap[]{idleImg}, 5);
        walkDown = new Animation(new Bitmap[]{walk1, walk2, walk3, idleImg}, 0.5f);
        animationManager = new AnimationManager(new Animation[]{idleUp, idleDown, idleLeft, idleRight, walkUp, walkDown, walkLeft, walkRight, explode, spawn});
    }

    public static void setWalls(ArrayList<Rect> wall)
    {
        walls = wall;
    }

    @Override
    public void draw(Canvas canvas)
    {
        //Paint paint = new Paint();
        //paint.setColor(color);
        //canvas.drawRect(rectangle, paint);
        animationManager.draw(canvas, rectangle);
    }

    public boolean collided(Rect object)
    {
        if(die)
        {
            return false;
        }
        return Rect.intersects(rectangle, object);
    }

    public int getAnimationState()
    {
        return animationState;
    }

    public boolean hasLockon(Rect object)
    {
        if(readyToFire)
        {
        /*0 for idleUp, 1 idleDown, 2 idleLeft, 3 idleRight,
          4 walkUP, 5 walkDown, 6 walking left, 7 walking right
          8 explode, 9 spawn*/
            if (animationState == 0 || animationState == 1 || animationState == 4 || animationState == 5)
            {
                if (rectangle.centerX() <= object.right && rectangle.centerX() >= object.left)
                {
                    readyToFire = false;
                    return true;
                }
            }
            else if (animationState == 2 || animationState == 3 || animationState == 6 || animationState == 7)
            {
                if (rectangle.centerY() <= object.bottom && rectangle.centerY() >= object.top)
                {
                    readyToFire = false;
                    return true;
                }
            }
            readyToFire = false;
        }
        return false;
    }

    @Override
    public void update()
    {
        if (die)
        {
            animationState = 8;
            dieFrames++;
        }
        else if (spawning)
        {
            animationState = 9;
            spawnFrames++;
            if(spawnFrames > 90)
            {
                spawning = false;
                animationState = 1;
                spawnFrames = 0;
            }
        }
        else
        {
            move();
            frames++;
        }

        animationManager.playAnimation(animationState);
        animationManager.update();
    }

    @Override
    public void reset()
    {
        animationState = 1;
    }

    public boolean die()
    {
        if (die || spawning)
        {
            return false;
        }
        else
        {
            die = true;
            return die;
        }
    }

    private void move()
    {
        if(frames > (30 * Constants.ENEMY_ACTIVENESS))
        {
            frames = 0;
            Random ran = new Random();
            int action = ran.nextInt(6);
            switch (action)
            {
                case 0:
                    moveDirection  = Enums.MoveDirection.Up;
                    break;
                case 1:
                    moveDirection = Enums.MoveDirection.Down;
                    break;
                case 2:
                    moveDirection = Enums.MoveDirection.Left;
                    break;
                case 3:
                    moveDirection = Enums.MoveDirection.Right;
                    break;
                default:
                    moveDirection = Enums.MoveDirection.Idle;
                    break;
            }
            if(ran.nextFloat() < Constants.ENEMY_FIRE_CHANCE)
                readyToFire = true;
        }
        Point temp;
        oldPoint = new Point(rectangle.centerX(), rectangle.centerY());
        Rect playRect = Player.getRectangle();
        switch (moveDirection)
        {
            case Up:
                temp = new Point(rectangle.centerX(), rectangle.centerY() - Constants.ENEMY_SPEED);
                //For enemy - player collision
                if(playRect.contains(temp.x - (Constants.ENEMY_SIZE/2),temp.y - (Constants.ENEMY_SIZE/2))
                        || playRect.contains(temp.x + (Constants.ENEMY_SIZE/2),temp.y - (Constants.ENEMY_SIZE/2)))
                {
                    temp.y += Constants.ENEMY_SPEED;
                    update(temp);
                    break;
                }
                //For Enemy - enemy collision
                for(Rect rect : walls)
                {
                    if(rect.contains(temp.x - (Constants.ENEMY_SIZE/2),temp.y - (Constants.ENEMY_SIZE/2))
                            || rect.contains(temp.x + (Constants.ENEMY_SIZE/2),temp.y - (Constants.ENEMY_SIZE/2))
                            || ((temp.y - (Constants.ENEMY_SIZE/2)) < 0))
                    {
                        temp.y += Constants.ENEMY_SPEED;
                    }
                }
                update(temp);
                break;
            case Down:
                temp = new Point(rectangle.centerX(), rectangle.centerY() + Constants.ENEMY_SPEED);
                if(playRect.contains(temp.x - (Constants.ENEMY_SIZE/2),temp.y + (Constants.ENEMY_SIZE/2))
                        || playRect.contains(temp.x + (Constants.ENEMY_SIZE/2),temp.y + (Constants.ENEMY_SIZE/2)))
                {
                    temp.y -= Constants.ENEMY_SPEED;
                    update(temp);
                    break;
                }
                for(Rect rect : walls)
                {
                    if(rect.contains(temp.x - (Constants.ENEMY_SIZE/2),temp.y + (Constants.ENEMY_SIZE/2))
                            || rect.contains(temp.x + (Constants.ENEMY_SIZE/2),temp.y + (Constants.ENEMY_SIZE/2))
                            ||((temp.y + (Constants.ENEMY_SIZE/2)) > Constants.SCREEN_HEIGHT))
                    {
                        temp.y -= Constants.ENEMY_SPEED;
                    }
                }
                update(temp);
                break;
            case Left:
                temp = new Point(rectangle.centerX() - Constants.ENEMY_SPEED, rectangle.centerY());
                if(playRect.contains(temp.x  - (Constants.ENEMY_SIZE/2), temp.y + (Constants.ENEMY_SIZE/2))
                        || playRect.contains(temp.x  - (Constants.ENEMY_SIZE/2), temp.y - (Constants.ENEMY_SIZE/2)))
                {
                    temp.x += Constants.ENEMY_SPEED;
                    update(temp);
                    break;
                }
                for(Rect rect : walls)
                {
                    if(rect.contains(temp.x  - (Constants.ENEMY_SIZE/2), temp.y + (Constants.ENEMY_SIZE/2))
                            || rect.contains(temp.x  - (Constants.ENEMY_SIZE/2), temp.y - (Constants.ENEMY_SIZE/2))
                            || ((temp.x - (Constants.ENEMY_SIZE/2)) < 0))
                    {
                        temp.x += Constants.ENEMY_SPEED;
                    }
                }
                update(temp);
                break;
            case Right:
                temp = new Point(rectangle.centerX() + Constants.ENEMY_SPEED, rectangle.centerY());
                if(playRect.contains(temp.x  + (Constants.ENEMY_SIZE/2), temp.y + (Constants.ENEMY_SIZE/2))
                        || playRect.contains(temp.x  + (Constants.ENEMY_SIZE/2), temp.y - (Constants.ENEMY_SIZE/2)))
                {
                    temp.x -= Constants.ENEMY_SPEED;
                    update(temp);
                    break;
                }
                for(Rect rect : walls)
                {
                    if(rect.contains(temp.x  + (Constants.ENEMY_SIZE/2), temp.y + (Constants.ENEMY_SIZE/2))
                            || rect.contains(temp.x  + (Constants.ENEMY_SIZE/2), temp.y - (Constants.ENEMY_SIZE/2))
                            || ((temp.x + (Constants.ENEMY_SIZE/2)) > Constants.SCREEN_WIDTH))
                    {
                        temp.x -= Constants.ENEMY_SPEED;
                    }
                }
                update(temp);
                break;
            default:
                break;
        }
    }

    public void undoMove()
    {
        if(oldPoint != null)
        {
            update(oldPoint);
        }
    }


    public void update(Point point)
    {
        float oldleft = rectangle.left;
        float oldtop = rectangle.top;

        rectangle.set(point.x - rectangle.width()/2,
                        point.y - rectangle.height()/2,
                        point.x + rectangle.width()/2,
                        point.y + rectangle.height()/2);
        /*0 for idleUp, 1 idleDown, 2 idleLeft, 3 idleRight,
          4 walkUP, 5 walkDown, 6 walking left, 7 walking right
          8 explode, 9 spawn
         */
        // > 5 for bigger movements before animating movements, else idle
        int deadzone = (int) (Constants.SCREEN_WIDTH * .004);
        if (rectangle.left - oldleft > deadzone)
        {
            animationState = 7;
        }
        else if (rectangle.left - oldleft < -deadzone)
        {
            animationState = 6;
        }
        else if (rectangle.top - oldtop > deadzone)
        {
            animationState = 5;
        }
        else if (rectangle.top - oldtop < - deadzone)
        {
            animationState = 4;
        }
        else if (animationState == 7)
        {
            animationState = 3;
        }
        else if (animationState == 6)
        {
            animationState = 2;
        }
        else if (animationState == 5)
        {
            animationState = 1;
        }
        else if (animationState == 4)
        {
            animationState = 0;
        }
    }

}
