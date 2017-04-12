package comp3717.bcit.ca.danktank.managers;

import android.media.MediaPlayer;

import comp3717.bcit.ca.danktank.Constants;
import comp3717.bcit.ca.danktank.R;

/**
 * Created by NamBlue on 4/11/2017.
 */

public class SFX_Manager
{
    private static MediaPlayer explode = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.explode);
    private static MediaPlayer fire = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.fire);

    public static void explode()
    {
        try {
            explode.seekTo(0);
            explode.setVolume(.5f, .5f);
            explode.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fire()
    {
        try {
            fire.seekTo(0);
            fire.setVolume(1f, 1f);
            fire.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
