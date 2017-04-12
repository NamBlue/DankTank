package comp3717.bcit.ca.danktank.managers;

import android.media.MediaPlayer;

import java.util.ArrayList;

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
    private static ArrayList<MediaPlayer> warp = new ArrayList<>();

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
            if(warp.size() == 0)
            {
                warp.add(MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.warp));
            }
            else
            {
                int i = 0;
                while(i < warp.size())
                {
                    if(!warp.get(i).isPlaying())
                    {
                        warp.get(i).seekTo(0);
                        warp.get(i).setVolume(.5f, .5f);
                        warp.get(i).start();
                        break;
                    }
                    ++i;
                }
                if(i == warp.size())
                {
                    warp.add(MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.warp));
                    warp.get(i).seekTo(0);
                    warp.get(i).setVolume(.5f, .5f);
                    warp.get(i).start();
                }
                else if (i < warp.size() - 1)
                {
                    warp.remove(warp.size() - 1);
                }
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
