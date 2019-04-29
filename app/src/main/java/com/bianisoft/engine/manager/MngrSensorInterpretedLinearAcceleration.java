package com.bianisoft.engine.manager;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;
import static java.lang.Math.abs;

/*
1D Vector Up : {+10} : Linear Accel {[2 to 5], ignoring following 2 seconds of reading}
1D Vector Down : {-10} : Linear Accel {[-2 to -5], ignoring following 2 seconds of reading}
*/

public class MngrSensorInterpretedLinearAcceleration implements SensorEventListener {
    private static MngrSensorInterpretedLinearAcceleration objThisInstance;

    private SensorManager objAndroidSensorMngr;
    private Sensor objSensorLinearAccelereration;
    private float[] vLinearAccel = null;
    private float[] vValue = null;
    private long[] nIgnoringCountPerAxis= null;
    private long lastUpdate= 0;


    public MngrSensorInterpretedLinearAcceleration(Context objContext){
        objThisInstance = this;
        objAndroidSensorMngr = (SensorManager) objContext.getSystemService(SENSOR_SERVICE);
        objSensorLinearAccelereration = objAndroidSensorMngr.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        if (objSensorLinearAccelereration != null)
            objAndroidSensorMngr.registerListener(this, objSensorLinearAccelereration, SensorManager.SENSOR_DELAY_NORMAL);
        else
            System.out.print("Sensor TYPE_LINEAR_ACCELERATION Not FOUND!!\n");

        vLinearAccel= new float[3];
        vLinearAccel[0]= 0f;
        vLinearAccel[1]= 0f;
        vLinearAccel[2]= 0f;

        vValue= new float[3];
        vValue[0]= 0f;
        vValue[1]= 0f;
        vValue[2]= 0f;

        nIgnoringCountPerAxis= new long[3];


        //First 10 second of sensor seem to not be calibrated, skip those
        lastUpdate= System.currentTimeMillis() + 10000;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long curTime = System.currentTimeMillis();

        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                for(int i= 0; i < 3; ++i) {

                    vLinearAccel[i] = event.values[i];

                    if (nIgnoringCountPerAxis[i] <= 0) {
                        if (Math.abs(vLinearAccel[i]) > 1f) {
                            vValue[i] = Math.copySign(Math.min(Math.abs(vLinearAccel[i]) - 1f, 10f) * 6.6f, vLinearAccel[i]);    //Theses values will be saved in RawSensorCommand
                            nIgnoringCountPerAxis[i]= 250;
                        }
                    }else {
                        nIgnoringCountPerAxis[i] -= 100;
                    }
                }

                System.out.printf("Managing Linear Acceleration Sensor : RawData : %2.5f\t,%2.5f\t,%2.5f\t\tInterpreted as : %2.5f\t,%2.5f\t,%2.5f\n", vLinearAccel[0], vLinearAccel[1], vLinearAccel[2], vValue[0], vValue[1], vValue[2]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onResume() {
        objAndroidSensorMngr.registerListener(this, objSensorLinearAccelereration, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        objAndroidSensorMngr.unregisterListener(this);
    }

    public void onDestroy() {
        objAndroidSensorMngr.unregisterListener(this);
    }

    public float[] getValue(){
        return vValue;
    }

    public void resetAxis(int nAxisToReset){
        vValue[nAxisToReset]= 0f;
    }

    public static MngrSensorInterpretedLinearAcceleration getInstance(){
        return objThisInstance;
    }
}
