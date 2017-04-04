package comp3717.bcit.ca.danktank.managers;

import android.media.MediaPlayer;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.R;

import static comp3717.bcit.ca.danktank.managers.SceneManager.ACTIVE_SCENE;
import static comp3717.bcit.ca.danktank.managers.SceneManager.sceneChanged;


/**
 * Created by steve on 2017-03-24.
 */

public class MusicManager {

    MediaPlayer mPlayer;

    public MusicManager(){
        mPlayer = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.title);
        mPlayer.start();
    }

    public void pause()
    {
        mPlayer.pause();
    }

    public void resume()
    {
        mPlayer.start();
    }

    public void update()
    {
        if (sceneChanged) {
            switch (ACTIVE_SCENE) {
                case 0:
                    try {
                        if (mPlayer != null && (!mPlayer.isPlaying())) {
                            mPlayer.stop();
                            mPlayer.release();
                            mPlayer = null;
                            mPlayer = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.title);
                            mPlayer.setLooping(true);
                            mPlayer.start();
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        if (!mPlayer.isPlaying()) {
                            mPlayer.start();
                        } else if (mPlayer != null) {
                            mPlayer.stop();
                            mPlayer.release();
                            mPlayer = null;
                            mPlayer = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.gameplay);
                            mPlayer.setLooping(true);
                            mPlayer.start();
                        }
                    } catch(Exception e){
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try{
                        if(!mPlayer.isPlaying()){
                            mPlayer.stop();
                            mPlayer.release();
                            mPlayer = null;
                            mPlayer = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.title);
                            mPlayer.setLooping(true);
                            mPlayer.start();
                        }
                    } catch(Exception e){
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try{
                    if(mPlayer.isPlaying()){
                        mPlayer.pause();
                    }
                    } catch(Exception e){
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
