package comp3717.bcit.ca.danktank.objects;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.Enums;
import comp3717.bcit.ca.danktank.R;
import comp3717.bcit.ca.danktank.managers.AnimationManager;

/**
 * Created by NamBlue on 1/19/2017.
 */

public class Bullet implements GameObject
{
    //the obstacle will consist of two rectangles with a gap in-between
    private Rect rectangle;
    private int color;
    private Enums.MoveDirection direction;
    private float speed;
    private Bitmap image;
    private Animation upAnimation, down, left, right;
    private AnimationManager animationManager;

    public Rect getRectangle()
    {
        return rectangle;
    }

    public Bullet(int color, int startX, int startY, Enums.MoveDirection moveDirection)
    {
        BitmapFactory bitmapFactory = new BitmapFactory();
        image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.bullet);
        speed = Constants.BULLET_SPEED;
        direction = moveDirection;
        this.color = color;
        int left = 0, right = 0, top = 0, bottom = 0;
        switch (moveDirection)
        {
            case Up:
                left = startX  - (Constants.BULLET_WIDTH / 2);
                right = startX + (Constants.BULLET_WIDTH / 2);
                top = startY + Constants.BULLET_HEIGHT;
                bottom = startY;
                break;
            case Down:
                left = startX - (Constants.BULLET_WIDTH / 2);
                right  = startX + (Constants.BULLET_WIDTH / 2);
                top = startY;
                bottom = startY + Constants.BULLET_HEIGHT;
                break;
            case Left:
                left = startX - (Constants.BULLET_HEIGHT);
                top = startY - (Constants.BULLET_WIDTH / 2);
                right  = startX;
                bottom = startY + Constants.BULLET_WIDTH / 2;
                break;
            case Right:
                left = startX;
                top = startY - (Constants.BULLET_WIDTH / 2);
                right  = startX + (Constants.BULLET_HEIGHT);
                bottom = startY + Constants.BULLET_WIDTH / 2;
                break;
        }

        Matrix matrix = new Matrix();

        matrix.setRotate(90);
        image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, false);
        this.right = new Animation(new Bitmap[]{image}, 5f);
        matrix.setRotate(90);
        image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, false);
        this.down = new Animation(new Bitmap[]{image}, 5f);
        matrix.setRotate(90);
        image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, false);
        this.left = new Animation(new Bitmap[]{image}, 5f);
        matrix.setRotate(90);
        image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, false);
        this.upAnimation =  new Animation(new Bitmap[]{image}, 5f);

        animationManager = new AnimationManager(new Animation[]{this.upAnimation, this.down, this.left, this.right});
        this.rectangle = new Rect(left, top, right, bottom);
    }

    public boolean collided(Rect object)
    {
        return Rect.intersects(rectangle, object);
    }

    private void move()
    {
        switch(direction)
        {
            case Up:
                rectangle.top -= speed;
                rectangle.bottom -= speed;
                break;
            case Down:
                rectangle.top += speed;
                rectangle.bottom += speed;
                break;
            case Right:
                rectangle.left += speed;
                rectangle.right += speed;
                break;
            case Left:
                rectangle.left -= speed;
                rectangle.right -= speed;
                break;
            default:
                break;
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        /*Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);*/
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update()
    {
        switch(direction)
        {
            case Up:
                animationManager.playAnimation(0);
                break;
            case Down:
                animationManager.playAnimation(1);
                break;
            case Left:
                animationManager.playAnimation(2);
                break;
            case Right:
                animationManager.playAnimation(3);
                break;

        }
        animationManager.update();
        move();
    }

    @Override
    public void reset()
    {

    }

}
