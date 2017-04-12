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
    private static MediaPlayer pickup = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.pickup);
    private static MediaPlayer impact = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.impact);
    private static MediaPlayer warp = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.warp);
    private static MediaPlayer warp1 = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.warp);

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

    public static void warp()
    {
        try {
            if(!warp1.isPlaying())
            {
                warp1.seekTo(0);
                warp1.setVolume(.5f, .5f);
                warp1.start();
            }
            else
            {
                warp.seekTo(0);
                warp.setVolume(.5f, .5f);
                warp.start();
            }
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

    public static void pickup()
    {
        try {
            pickup.seekTo(0);
            pickup.setVolume(1f, 1f);
            pickup.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void impact()
    {
        try {
            impact.seekTo(0);
            impact.setVolume(1f, 1f);
            impact.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
