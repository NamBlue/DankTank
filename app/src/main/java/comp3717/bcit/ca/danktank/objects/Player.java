package comp3717.bcit.ca.danktank.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.R;
import comp3717.bcit.ca.danktank.managers.AnimationManager;

/**
 * Created by NamBlue on 1/19/2017.
 */

public class Player implements GameObject
{
    private Rect rectangle;
    private int color;
    private Animation idleUp, idleDown, idleLeft, idleRight;
    private Animation walkLeft, walkRight, walkUp, walkDown;
    private AnimationManager animationManager;
    private int directionState;
    private  boolean startingState;

    public Rect getRectangle()
    {
        return rectangle;
    }

    public Player(Rect rectangle, int color)
    {
        this.rectangle = rectangle;
        this.color = color;
        directionState = 0;
        startingState = true;
        //For Decoding, Producing, Modifying Bitmaps etc.
        BitmapFactory bitmapFactory = new BitmapFactory();
        //Make sure image names are all lowercase or will cause errors!
        Bitmap idleImg = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.idle);
        Bitmap walk1 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.move1);
        Bitmap walk2 = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.move2);


        idleRight = new Animation(new Bitmap[]{walk1}, 5);
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        //walkleft requires to reflect the image on the vertical axis.
        Matrix matrix = new Matrix();
        matrix.setRotate(-90);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), matrix, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), matrix, false);
        idleUp = new Animation(new Bitmap[]{walk1}, 5);
        walkUp = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        matrix.reset();
        matrix.setRotate(-90);
        idleImg = Bitmap.createBitmap(idleImg, 0, 0, idleImg.getWidth(), idleImg.getHeight(), matrix, false);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), matrix, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), matrix, false);
        walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);
        idleLeft = new Animation(new Bitmap[]{walk1}, 5);

        matrix.reset();
        matrix.setRotate(-90);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), matrix, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), matrix, false);
        idleDown  = new Animation(new Bitmap[]{walk1}, 5);
        walkDown = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);
        animationManager = new AnimationManager(new Animation[]{idleUp, idleDown, idleLeft, idleRight, walkUp, walkDown, walkLeft, walkRight});
    }

    @Override
    public void draw(Canvas canvas)
    {
        //Paint paint = new Paint();
        //paint.setColor(color);
        //canvas.drawRect(rectangle, paint);
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
        directionState = 0;
    }

    public void update(Point point)
    {
        float oldleft = rectangle.left;
        float oldtop = rectangle.top;

        rectangle.set(point.x - rectangle.width()/2,
                        point.y - rectangle.height()/2,
                        point.x + rectangle.width()/2,
                        point.y + rectangle.height()/2);
        //0 for idleUp, 1 idleDown, 2 idleLeft, 3 idleRight, 4 walkUP, 5 walkDown, 6 walking left, 7 walking right animations;
        // > 5 for bigger movements before animating movements, else idle
        if (rectangle.left - oldleft > 5)
        {
            directionState = 7;
        }
        else if (rectangle.left - oldleft < -5)
        {
            directionState = 6;
        }
        else if (rectangle.top - oldtop > 5)
        {
            directionState = 5;
        }
        else if (rectangle.top - oldtop < - 5)
        {
            directionState = 4;
        }
        else if (directionState == 7)
        {
            directionState = 3;
        }
        else if (directionState == 6)
        {
            directionState = 2;
        }
        else if (directionState == 5)
        {
            directionState = 1;
        }
        else if (directionState == 4)
        {
            directionState = 0;
        }
        if (startingState)
        {
            directionState = 0;
            startingState = false;
        }

        animationManager.playAnimation(directionState);
        animationManager.update();
    }

}
