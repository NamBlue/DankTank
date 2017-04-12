package comp3717.bcit.ca.danktank.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.Enums;
import comp3717.bcit.ca.danktank.R;
import comp3717.bcit.ca.danktank.managers.AnimationManager;

/**
 * Created by NamBlue on 1/19/2017.
 */

public class Player implements GameObject
{
    private static Rect rectangle;
    private Rect wallRect;
    private int color;
    private Animation idleUp, idleDown, idleLeft, idleRight;
    private Animation walkLeft, walkRight, walkUp, walkDown;
    private Animation spawn, explode;
    private AnimationManager animationManager;
    private int animationState;
    public boolean startingState, dying;
    private int spawnFrames, dieFrames;

    public static Rect getRectangle()
    {
        return rectangle;
    }

    public Rect getWallRect() {return wallRect;}

    public Player(Rect rectangle, int color)
    {
        dieFrames = 0;
        this.rectangle = rectangle;
        this.color = color;
        animationState = 0;
        spawnFrames = 0;
        dying = false;
        startingState = true;
        //For Decoding, Producing, Modifying Bitmaps etc.
        BitmapFactory bitmapFactory = new BitmapFactory();
        Bitmap spawn1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pspawn1);
        Bitmap spawn2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pspawn2);
        Bitmap spawn3 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pspawn3);
        Bitmap spawn4 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pspawn4);
        spawn = new Animation(new Bitmap[]{spawn1, spawn2, spawn3, spawn4},0.10f);
        //Make sure image names are all lowercase or will cause errors!
        Bitmap idleImg = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p_idle);
        Bitmap walk1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p_move1);
        Bitmap walk2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p_move2);
        Bitmap walk3 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.p_move3);

        Bitmap explode1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explode1);
        Bitmap explode2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explode2);
        Bitmap explode3 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explode3);
        explode = new Animation(new Bitmap[]{explode1, explode2, explode3},0.5f);

        idleUp = new Animation(new Bitmap[]{idleImg}, 5);
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
        animationManager = new AnimationManager(new Animation[]{idleUp, idleDown, idleLeft, idleRight, walkUp, walkDown, walkLeft, walkRight, spawn, explode});
    }

    @Override
    public void draw(Canvas canvas)
    {
        //Paint paint = new Paint();
        //paint.setColor(color);
        //canvas.drawRect(rectangle, paint);
        if(dieFrames < 45)
            animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update()
    {
        animationManager.update();
    }

    @Override
    public void reset()
    {
        startingState = true;
        animationState = 0;
        spawnFrames = 0;
        dying = false;
        dieFrames = 0;
    }

    public void update(Point point, Enums.MoveDirection direction)
    {
        float oldleft = rectangle.left;
        float oldtop = rectangle.top;

        rectangle.set(point.x - rectangle.width()/2,
                        point.y - rectangle.height()/2,
                        point.x + rectangle.width()/2,
                        point.y + rectangle.height()/2);
        if (startingState)
        {
            animationState = 8;
            spawnFrames++;
            if(spawnFrames > 90)
            {
                spawnFrames = 0;
                startingState = false;
                animationState = 0;
            }
        }
        else if (dying)
        {
            dieFrames++;
            animationState = 9;
        }
        else
        {
            //0 for idleUp, 1 idleDown, 2 idleLeft, 3 idleRight, 4 walkUP, 5 walkDown, 6 walking left, 7 walking right animations;
            // > 5 for bigger movements before animating movements, else idle
            if (rectangle.left - oldleft > 5)
            {
                animationState = 7;
            }
            else if (rectangle.left - oldleft < -5)
            {
                animationState = 6;
            }
            else if (rectangle.top - oldtop > 5)
            {
                animationState = 5;
            }
            else if (rectangle.top - oldtop < -5)
            {
                animationState = 4;
            }
            else
            {
                switch (direction)
                {
                    case Left:
                        animationState = 2;
                        break;
                    case Right:
                        animationState = 3;
                        break;
                    case Up:
                        animationState = 0;
                        break;
                    case Down:
                        animationState = 1;
                        break;
                    default:
                        break;
                }
            }
        }


        animationManager.playAnimation(animationState);
        animationManager.update();
    }

}
