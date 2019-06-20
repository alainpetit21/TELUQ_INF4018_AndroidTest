package com.bianisoft.androittest.view;

import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.bianisoft.engine.FrontendApp;
import com.bianisoft.engine.manager.MngrTouchScreen;
import com.bianisoft.engine.manager.MngrGLRendererAndroid;
import com.bianisoft.engine.manager.MngrSensorInterpretedLinearAcceleration;
import com.bianisoft.engine.manager.MngrSensorPositionalMappingUsingGravity;


public class ActivityGame extends AppCompatActivity {
    private AppPrototypeTELUQ_INF4018 objApp;
    private GLSurfaceView glSurfaceView;
    private MngrGLRendererAndroid objMngrRenderer;
    private MngrSensorPositionalMappingUsingGravity objMngrSensorsPositional;
    private MngrSensorInterpretedLinearAcceleration objMngrSensorsLinearAccel;

    private boolean hasGoneThroughLoading;
    private boolean hasGoneThroughCalibration;


    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandlerRunnable;
    Runnable timerRunnable;

    Handler timerHandlerLoading;
    Runnable timerLoading;

    Handler timerHandlerCalibrating;
    Runnable timerCalibrating;


    public ActivityGame() {
        super();

        hasGoneThroughLoading = false;
        hasGoneThroughCalibration = false;

        //runs without a timer by reposting this handler at the end of the runnable
        timerHandlerRunnable = new Handler();
        timerRunnable = new Runnable() {

            @Override
            public void run() {
                if(!hasGoneThroughLoading) {
                    objApp.create();
                    hasGoneThroughLoading= true;
                }

                if(hasGoneThroughCalibration) {
                    objApp.manage();
                }

                glSurfaceView.requestRender();

                timerHandlerRunnable.postDelayed(this, 16);
            }
        };

        timerHandlerLoading = new Handler();
        timerLoading = new Runnable() {

            @Override
            public void run() {
                if ((FrontendApp.getGL10() != null) && !hasGoneThroughLoading){
                    objApp.create();
                    hasGoneThroughLoading = true;
                }else {
                    timerHandlerLoading.postDelayed(timerLoading, 1000);
                }
            }
        };


        timerHandlerCalibrating = new Handler();
        timerCalibrating = new Runnable() {

            @Override
            public void run() {
                if(!hasGoneThroughCalibration){
                    hasGoneThroughCalibration = true;
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        objApp= new AppPrototypeTELUQ_INF4018();
        FrontendApp.setContext(this);

        objMngrRenderer = new MngrGLRendererAndroid();
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(objMngrRenderer);
        glSurfaceView.setOnTouchListener(new MngrTouchScreen());

        setContentView(glSurfaceView);

        timerHandlerRunnable.postDelayed(timerRunnable, 16);
        timerHandlerCalibrating.postDelayed(timerCalibrating, 10000);

        objMngrSensorsPositional = new MngrSensorPositionalMappingUsingGravity(this);
        objMngrSensorsLinearAccel = new MngrSensorInterpretedLinearAcceleration(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerHandlerRunnable.removeCallbacks(timerRunnable);
        objMngrSensorsPositional.onDestroy();
        objMngrSensorsLinearAccel.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        timerHandlerRunnable.postDelayed(timerRunnable, 16);
        glSurfaceView.onResume();
        objMngrSensorsPositional.onResume();
        objMngrSensorsLinearAccel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerHandlerRunnable.removeCallbacks(timerRunnable);
        glSurfaceView.onPause();
        objMngrSensorsPositional.onPause();
        objMngrSensorsLinearAccel.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                return true;

            case MotionEvent.ACTION_DOWN:
                return true;

            default:
                return super.onTouchEvent(event);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                glSurfaceView.requestRender();
                return true;

            case KeyEvent.KEYCODE_DPAD_DOWN:
                glSurfaceView.requestRender();
                return true;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                glSurfaceView.requestRender();
                return true;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                glSurfaceView.requestRender();
                return true;

            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}

