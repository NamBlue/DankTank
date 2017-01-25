package comp3717.bcit.ca.danktank;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.Settings;

/**
 * Created by NamBlue on 1/24/2017.
 */

public class OrientationData implements SensorEventListener
{
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnometer;
    private float[] accelOutput;
    private float[] magOutput;
    private float[] orientation = new float[3];
    private float[] startingOrientation = null;

    public float[] getOrientation()
    {
        return orientation;
    }

    public float[] getStartingOrientation()
    {
        return startingOrientation;
    }

    public void newGame()
    {
        startingOrientation = null;
    }

    public OrientationData()
    {
        sensorManager = (SensorManager)Constants.CURRENT_CONTEXT.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void register()
    {
        //Delay is the time elapsed before calling onSensorChanged SENSOR_DELAY_GAME is the recommended value for games
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, magnometer, SensorManager.SENSOR_DELAY_GAME);
    }

    //For when the game isnt running to save resources
    public void pause()
    {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            accelOutput = event.values;
        }
        else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            magOutput = event.values;
        }
        if (accelOutput != null && magOutput != null)
        {
            float[] rotationMatrix = new float[9];
            float[] inclinationMatrix = new float[9];
            boolean success = SensorManager.getRotationMatrix(rotationMatrix,inclinationMatrix, accelOutput, magOutput);
            if (success)
            {
                SensorManager.getOrientation(rotationMatrix, orientation);
                if (startingOrientation == null)
                {
                    startingOrientation = new float[orientation.length];
                    System.arraycopy(orientation, 0, startingOrientation, 0, orientation.length);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}
