package comp3717.bcit.ca.danktank;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import comp3717.bcit.ca.danktank.managers.MusicManager;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback
{
    final String pubArtFile = "http://opendata.newwestcity.ca/downloads/public-art/PUBLIC_ART.json";
    final String artistFile = "http://opendata.newwestcity.ca/downloads/artists/ARTISTS.json";
    private GamePanel gamePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Get fullscreen and no title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Constants.SCREEN_WIDTH = displayMetrics.widthPixels;
        Constants.SCREEN_HEIGHT = displayMetrics.heightPixels;
        if(isNetworkPermissionGranted())
            new DownloadFileFromURL().execute(pubArtFile, artistFile);

        gamePanel = new GamePanel(this);
        setContentView(gamePanel);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        MusicManager.getInstance().pause();
    }

    @Override
    protected  void onResume()
    {
        super.onResume();
        MusicManager.getInstance().resume();
    }


    private boolean isNetworkPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.INTERNET)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            finish();
            startActivity(getIntent());
        }
    }

    private class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            for(int i = 0; i < f_url.length; i++){
                try {
                    String root = Environment.getExternalStorageDirectory().toString();

                    System.out.println("Downloading");
                    URL url = new URL(f_url[i]);

                    URLConnection conection = url.openConnection();
                    conection.connect();
                    // getting file length
                    int lenghtOfFile = conection.getContentLength();

                    // input stream to read file - with 8k buffer
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    // Output stream to write file
                    String fileName = "a" +i+".json";
                    OutputStream output = openFileOutput(fileName, Context.MODE_PRIVATE);
                    byte data[] = new byte[1024];

                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;

                        // writing data to file
                        output.write(data, 0, count);

                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();

                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {

        }

    }
}