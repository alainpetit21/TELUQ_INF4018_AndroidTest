package com.bianisoft.engine.manager;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

/*
2D Coordinate Middle : {0 : 0}  : Gravity Data {0.0; 0.0; 9.8}
2D Coordinate Left : {-10 : -0}  : Gravity Data {5.2; 0.0; 8.2}
2D Coordinate Right : {+10 : +0}  : Gravity Data {-5.2; 0.0; 8.2}
2D Coordinate Down : {0 : -10}  : Gravity Data {0.0; 5.2; 8.2}
2D Coordinate Up : {0 : +10}  : Gravity Data {0.0; -5.2; 8.2}
*/

public class MngrSensorPositionalMappingUsingGravity implements SensorEventListener {
    private static MngrSensorPositionalMappingUsingGravity objThisInstance;

    private SensorManager objAndroidSensorMngr;
    private Sensor objSensorGravity;
    private float[] vGravity = null;
    private float[] vPos = null;
    private long lastUpdateGravity= 0;


    public MngrSensorPositionalMappingUsingGravity(Context objContext){
        objThisInstance = this;
        objAndroidSensorMngr = (SensorManager) objContext.getSystemService(SENSOR_SERVICE);
        objSensorGravity = objAndroidSensorMngr.getDefaultSensor(Sensor.TYPE_GRAVITY);

        if (objSensorGravity != null)
            objAndroidSensorMngr.registerListener(this, objSensorGravity, SensorManager.SENSOR_DELAY_NORMAL);
        else
            System.out.print("Sensor Gravity Not FOUND!!\n");

        vGravity= new float[3];
        vGravity[0]= 0f;
        vGravity[1]= 0f;
        vGravity[2]= 9.81f;

        vPos = new float[2];
        vPos[0]= 0f;
        vPos[1]= 0f;

        //First 9 second of sensor seem to not be calibrated, skip those
        lastUpdateGravity= System.currentTimeMillis() + 9000;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long curTime = System.currentTimeMillis();

        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            if ((curTime - lastUpdateGravity) > 100) {
                long diffTime = (curTime - lastUpdateGravity);
                lastUpdateGravity = curTime;

                vGravity[0] = event.values[0];  //Theses values *2 will be saved in RawSensorCommand
                vGravity[1] = event.values[1];  //Theses values *2 will be saved in RawSensorCommand
                vGravity[2] = event.values[2];

                vPos[0]= -vGravity[0]*2;
                vPos[1]= vGravity[1]*2;

//                System.out.printf("Got Gravity Data : %2.5f\t%2.5f\t%2.5f\n", vGravity[0], vGravity[1], vGravity[2]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onResume() {
        objAndroidSensorMngr.registerListener(this, objSensorGravity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        objAndroidSensorMngr.unregisterListener(this);
    }

    public void onDestroy() {
        objAndroidSensorMngr.unregisterListener(this);
    }

    public float[] getGravity(){
        return vGravity;
    }

    public float[] getPos(){
        return vPos;
    }

    public static MngrSensorPositionalMappingUsingGravity getInstance(){
        return objThisInstance;
    }
}
