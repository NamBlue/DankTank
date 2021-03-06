package comp3717.bcit.ca.danktank;

import android.content.Context;

/**
 * Created by NamBlue on 1/20/2017.
 */

public class Constants
{
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    //Delay before displaying game over, each second is 30
    public static int GAMEOVER_TIME = 165;
    public static Context CURRENT_CONTEXT;
    public static long INIT_TIME;
    public static int TANK_DIE_FRAMES = 70;
    //Below are all set in GameplayScene
    public static int PLAYER_SIZE = 90;
    public static int ENEMY_SIZE = 90;
    public static int POWERUP_SIZE = 90;
    public static int PLAYER_SPEED = 15; //For player movement speed
    public static int ENEMY_SPEED = 15;
    public static float ENEMY_ACTIVENESS = 1;
    public static float ENEMY_FIRE_CHANCE = .5f;
    public static int BULLET_WIDTH = 15; //For bullet width
    public static int BULLET_HEIGHT = 35; //For bullet height
    public static float BULLET_SPEED = 30; //For bullet movement speed
    public static int BULLET_GAP = PLAYER_SIZE; //For the gap between then tank and bullet desired when spawining
    public static final int TITLE_BLINK_SPEED = 5;
}
