package comp3717.bcit.ca.danktank.managers;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;


import java.util.ArrayList;
import java.util.Random;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.R;
import comp3717.bcit.ca.danktank.scenes.Scene;

import static comp3717.bcit.ca.danktank.MainThread.canvas;

/**
 * Created by steve on 2017-03-31.
 */

public class LevelManager implements Scene {
    private Rect testrect = new Rect();
    private boolean [][] map;
    private Bitmap Brick_image;
    private Bitmap Ice_image;
    private Random r;
    public static int level = 1;
    private ArrayList<Rect> spawnpoints;
    private ArrayList<Rect> brick_tiles;
    private Rect player_spawn;
    private static int totalEnemiesForThisLevel;
    private int maxEnemySize;
    private Bitmap backGroundImage;

    public LevelManager(int lvl){
        BitmapFactory bitmapFactory = new BitmapFactory();
        Brick_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.brick);
        Ice_image = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.ice);
        brick_tiles = new ArrayList<Rect>();
        spawnpoints = new ArrayList<Rect>();
        r = new Random();
        map = new boolean[10][20];
        level = lvl;
        populateMap(level);
        if(level == 1) {
            backGroundImage = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.desert);
        }else if(level == 2){
            backGroundImage = bitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.snow);
        }




    }

    public static int gettotalEnemiesForThisLevel(){return totalEnemiesForThisLevel;}

    public ArrayList<Rect> getSpawnpoints(){
        return spawnpoints;
    }

    public ArrayList<Rect> getBrickTiles(){
        return brick_tiles;
    }

    public Rect getPlayerSpawn(){
        return player_spawn;
    }

    public boolean collided(Rect object)
    {
        for(Rect rect: brick_tiles)
        {
            if(Rect.intersects(rect, object))
                return true;
        }
        return false;
    }

    public int getTotalEnemiesForThisLevel()
    {
        return totalEnemiesForThisLevel;
    }

    public int getMaxEnemySize()
    {
        return maxEnemySize;
    }

    private void populateMap(int level)
    {
        switch(level) {
            case 1 :
                totalEnemiesForThisLevel = 1;
                maxEnemySize = 4;
                //Brick layout for surrounding controls
                for(int i = 0; i < 3; i++){
                    map[i][15] = true;
                }
                for(int j = 15; j < 20; j++){
                    map[3][j] = true;
                }
                for(int i = 7; i < 10; i++){
                    map[i][15] = true;
                }
                for(int j = 15; j < 20; j++){
                    map[7][j] = true;
                }

                //Terrain map tiles
                for(int i = 0; i < 4; i = i+2)
                    map[i][10] = true;
                for(int i = 6; i < 9; i = i+2)
                    map[i][10] = true;

                for(int j = 9; j > 7; j--)
                    map[3][j] = true;

                for(int j = 8; j > 5; j--)
                    map[7][j] = true;

                for(int j = 8; j > 5; j--)
                    map[9][j] = true;

                for(int i = 0; i < 10; i = i+3)
                    map[i][8] = true;

                map[6][5] = true;
                map[6][6] = true;

                map[3][7] = true;
                map[5][13] = true;
                map[4][8] = true;
                map[4][18] = true;
                map[4][9] = true;
                map[5][1] = true;
                map[6][1] = true;
                map[0][12] = true;
                map[0][13] = true;
                map[1][12] = true;
                map[1][13] = true;
                map[3][12] = true;

                map[6][15] = true;
                map[5][15] = true;
                map[6][16] = true;
                map[7][12] = true;
                map[8][12] = true;


                map[8][11] = true;
                map[8][13] = true;
                map[9][13] = true;

                map[0][3] = true;
                map[1][3] = true;

                map[4][3] = true;
                map[5][3] = true;
                map[6][3] = true;
                map[8][3] = true;
                map[8][4] = true;

                map[3][1] = true;
                map[2][1] = true;
                map[8][1] = true;

                map[4][13] = true;
                map[4][12] = true;
                map[4][11] = true;

                for(int i = 0; i < 8; i = i+2)
                    map[i][5] = true;

                map[1][7] = true;

                //Spawnpoints
                spawnpoints.add(new Rect(Constants.SCREEN_WIDTH * 0 / 10, Constants.SCREEN_HEIGHT *
                        0 / 20, Constants.SCREEN_WIDTH * (0 + 1) / 10, Constants.SCREEN_HEIGHT *
                        (0 + 1) / 20));

                spawnpoints.add(new Rect(Constants.SCREEN_WIDTH * 3 / 10, Constants.SCREEN_HEIGHT *
                        5 / 20, Constants.SCREEN_WIDTH * (3 + 1) / 10, Constants.SCREEN_HEIGHT *
                        (5 + 1) / 20));

                spawnpoints.add(new Rect(Constants.SCREEN_WIDTH * 5 / 10, Constants.SCREEN_HEIGHT *
                        7 / 20, Constants.SCREEN_WIDTH * (5 + 1) / 10, Constants.SCREEN_HEIGHT *
                        (7 + 1) / 20));

                spawnpoints.add(new Rect(Constants.SCREEN_WIDTH * 9 / 10, Constants.SCREEN_HEIGHT *
                        5 / 20, Constants.SCREEN_WIDTH * (9 + 1) / 10, Constants.SCREEN_HEIGHT *
                        (5 + 1) / 20));

                player_spawn = new Rect(Constants.SCREEN_WIDTH * 5 / 10, Constants.SCREEN_HEIGHT *
                        19 / 20, Constants.SCREEN_WIDTH * (5 + 1) / 10, Constants.SCREEN_HEIGHT *
                        (19 + 1) / 20);

                for(int i = 0; i < 10; i++){
                    for(int j = 0; j < 20; j++){
                        if(map[i][j] == true) {
                            brick_tiles.add(new Rect(Constants.SCREEN_WIDTH * i / 10,
                                    Constants.SCREEN_HEIGHT * j / 20, Constants.SCREEN_WIDTH * (i + 1) / 10,
                                    Constants.SCREEN_HEIGHT * (j + 1) / 20));
                        }
                    }
                }
                break;
            case 2 :
                totalEnemiesForThisLevel = 4;
                maxEnemySize = 4;
                //Brick layout for surrounding controls
                for(int i = 0; i < 3; i++){
                    map[i][15] = true;
                }
                for(int j = 15; j < 20; j++){
                    map[3][j] = true;
                }
                for(int i = 7; i < 10; i++){
                    map[i][15] = true;
                }
                for(int j = 15; j < 20; j++){
                    map[7][j] = true;
                }

                //Terrain map tiles
                map[1][13] = true;
                map[2][12] = true;
                map[3][11] = true;

                map[8][13] = true;
                map[7][12] = true;
                map[6][11] = true;

                map[4][13] = true;
                map[5][13] = true;

                map[0][9] = true;
                map[2][9] = true;
                map[7][9] = true;
                map[9][9] = true;

                map[5][9] = true;
                map[4][9] = true;
                map[5][8] = true;
                map[4][8] = true;
                map[5][9] = true;
                map[4][9] = true;

                map[3][6] = true;
                map[2][5] = true;
                map[1][4] = true;

                map[8][4] = true;
                map[7][5] = true;
                map[6][6] = true;

                map[4][4] = true;
                map[5][4] = true;

                map[1][2] = true;
                map[2][2] = true;

                map[8][2] = true;
                map[7][2] = true;

                map[4][0] = true;
                map[5][0] = true;

                map[4][1] = true;
                map[5][1] = true;

                //Spawnpoints
                spawnpoints.add(new Rect(Constants.SCREEN_WIDTH * 0 / 10, Constants.SCREEN_HEIGHT *
                        0 / 20, Constants.SCREEN_WIDTH * (0 + 1) / 10, Constants.SCREEN_HEIGHT *
                        (0 + 1) / 20));


                player_spawn = new Rect(Constants.SCREEN_WIDTH * 5 / 10, Constants.SCREEN_HEIGHT *
                        19 / 20, Constants.SCREEN_WIDTH * (5 + 1) / 10, Constants.SCREEN_HEIGHT *
                        (19 + 1) / 20);

                for(int i = 0; i < 10; i++){
                    for(int j = 0; j < 20; j++){
                        if(map[i][j] == true) {
                            brick_tiles.add(new Rect(Constants.SCREEN_WIDTH * i / 10,
                                    Constants.SCREEN_HEIGHT * j / 20, Constants.SCREEN_WIDTH * (i + 1) / 10,
                                    Constants.SCREEN_HEIGHT * (j + 1) / 20));
                        }
                    }
                }
                break;
            default : // Optional
                break;
        }
    }
    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(backGroundImage, null, new Rect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT),null);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (map[i][j] == true) {
                    switch(level) {
                        case 1 :
                        canvas.drawBitmap(Brick_image, null, new Rect(Constants.SCREEN_WIDTH * i / 10,
                                Constants.SCREEN_HEIGHT * j / 20, Constants.SCREEN_WIDTH *
                                (i + 1) / 10, Constants.SCREEN_HEIGHT * (j + 1) / 20), new Paint());
                            break;
                        case 2 :
                        canvas.drawBitmap(Ice_image, null, new Rect(Constants.SCREEN_WIDTH * i / 10,
                                Constants.SCREEN_HEIGHT * j / 20, Constants.SCREEN_WIDTH *
                                (i + 1) / 10, Constants.SCREEN_HEIGHT * (j + 1) / 20), new Paint());
                            break;
                        default :
                            break;
                    }
                }
            }
        }
    }

    @Override
    public void receiveTouch(MotionEvent event) {

    }

    @Override
    public void reset() {

    }

    @Override
    public void onExit() {

    }
}
