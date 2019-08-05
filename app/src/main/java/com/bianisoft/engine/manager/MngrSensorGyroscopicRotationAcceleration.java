package com.bianisoft.engine.manager;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

//This is just for display, it does not control anything gameplay things.
//It kinda satisfy the TN3 reqiurement to include "raw" accelerometer since gravity is "kinda" a
//fusionned sensor.
public class MngrSensorGyroscopicRotationAcceleration implements SensorEventListener {
    private static MngrSensorGyroscopicRotationAcceleration objThisInstance;

    private SensorManager objAndroidSensorMngr;
    private Sensor objSensorGyroscope;
    private float[] vGyroscope = null;
    private long lastUpdateGyscope = 0;


    public MngrSensorGyroscopicRotationAcceleration(Context objContext){
        objThisInstance = this;
        objAndroidSensorMngr = (SensorManager) objContext.getSystemService(SENSOR_SERVICE);
        objSensorGyroscope = objAndroidSensorMngr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (objSensorGyroscope != null)
            objAndroidSensorMngr.registerListener(this, objSensorGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        else
            System.out.print("Sensor Gyroscope Not FOUND!!\n");

        vGyroscope = new float[3];
        vGyroscope[0]= 0f;
        vGyroscope[1]= 0f;
        vGyroscope[2]= 0f;

        //First 9 second of sensor seem to not be calibrated, skip those
        lastUpdateGyscope = System.currentTimeMillis() + 9000;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long curTime = System.currentTimeMillis();

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            if ((curTime - lastUpdateGyscope) > 100) {
                long diffTime = (curTime - lastUpdateGyscope);
                lastUpdateGyscope = curTime;

                vGyroscope[0] = event.values[0];
                vGyroscope[1] = event.values[1];
                vGyroscope[2] = event.values[2];

                System.out.printf("Got Gyro Data : %2.5f\t%2.5f\t%2.5f\n", vGyroscope[0], vGyroscope[1], vGyroscope[2]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onResume() {
        objAndroidSensorMngr.registerListener(this, objSensorGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        objAndroidSensorMngr.unregisterListener(this);
    }

    public void onDestroy() {
        objAndroidSensorMngr.unregisterListener(this);
    }

    public float[] getGyroData(){
        return vGyroscope;
    }

    public static MngrSensorGyroscopicRotationAcceleration getInstance(){
        return objThisInstance;
    }
}
