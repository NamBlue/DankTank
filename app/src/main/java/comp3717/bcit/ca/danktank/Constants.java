package comp3717.bcit.ca.danktank;

import android.content.Context;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class Constants
{
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    //Delay before displaying game over
    public static int GAMEOVER_TIME = 2000;
    public static Context CURRENT_CONTEXT;
    public static long INIT_TIME;
    public static final int PLAYER_SIZE = 90;
    public static final int ENEMY_SIZE = 90;
    public static final int PLAYER_SPEED = 15; //For player movement speed
    public static final int BULLET_WIDTH = 15; //For bullet width
    public static final int BULLET_HEIGHT = 35; //For bullet height
    public static final float BULLET_SPEED = 30; //For bullet movement speed
    public static final int BULLET_GAP = PLAYER_SIZE; //For the gap between then tank and bullet desired when spawining

}
