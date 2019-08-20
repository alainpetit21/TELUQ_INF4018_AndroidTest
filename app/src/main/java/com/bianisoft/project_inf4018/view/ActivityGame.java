package com.bianisoft.project_inf4018.view;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.bianisoft.engine.PresentationApp;
import com.bianisoft.engine.manager.MngrSensorGyroscopicRotationAcceleration;
import com.bianisoft.engine.manager.MngrTouchScreen;
import com.bianisoft.engine.manager.MngrGLRendererAndroid;
import com.bianisoft.engine.manager.MngrSensorPositionalMappingUsingGravity;
import com.bianisoft.project_inf4018.controller.ApplicationFacade;
import com.bianisoft.project_inf4018.model.DomainFacade;


public class ActivityGame extends AppCompatActivity {
    private AppTELUQ_INF4018 objApp;
    private GLSurfaceView glSurfaceView;

    private MngrGLRendererAndroid objMngrRenderer;
    private MngrSensorPositionalMappingUsingGravity objMngrSensorsPositional;
    private MngrSensorGyroscopicRotationAcceleration objMngrSensorsGyros;


    private Handler timerHandlerRunnable;
    private Runnable timerRunnable;

    private Handler timerHandlerLoading;
    private Runnable timerLoading;

    private Handler timerHandlerCalibrating;
    private Runnable timerCalibrating;


    public ActivityGame() {
        super();

        //runs without a timer by reposting this handler at the end of the runnable
        timerHandlerRunnable = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                onTimerGameLoop(this);
            }
        };

        timerHandlerLoading = new Handler();
        timerLoading = new Runnable() {

            @Override
            public void run() {
                onTimerGameLoading(this);
            }
        };


        timerHandlerCalibrating = new Handler();
        timerCalibrating = new Runnable() {

            @Override
            public void run() {
                onTimerGameCalibratingSensors(this);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        objApp= new AppTELUQ_INF4018();
        PresentationApp.setContext(this);

        objMngrRenderer = new MngrGLRendererAndroid();
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(objMngrRenderer);
        glSurfaceView.setOnTouchListener(new MngrTouchScreen());

        setContentView(glSurfaceView);

        timerHandlerRunnable.postDelayed(timerRunnable, 16);
        timerHandlerCalibrating.postDelayed(timerCalibrating, 10000);

        objMngrSensorsPositional = new MngrSensorPositionalMappingUsingGravity(this);
        objMngrSensorsGyros = new MngrSensorGyroscopicRotationAcceleration(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerHandlerRunnable.removeCallbacks(timerRunnable);
        objMngrRenderer.onDestroy();
        objMngrSensorsPositional.onDestroy();
        objMngrSensorsGyros.onDestroy();
        objMngrRenderer.onDestroy();

        ApplicationFacade.forceSingletonDestruction();
        DomainFacade.forceSingletonDestruction(); //TODO Should not destroy the Domain yet, we will need it on the Score And review Page
    }

    @Override
    protected void onResume() {
        super.onResume();
        timerHandlerRunnable.postDelayed(timerRunnable, 16);
        glSurfaceView.onResume();
        objMngrSensorsPositional.onResume();
        objMngrSensorsGyros.onResume();
        objMngrRenderer.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerHandlerRunnable.removeCallbacks(timerRunnable);
        glSurfaceView.onPause();
        objMngrSensorsPositional.onPause();
        objMngrSensorsGyros.onPause();
        objMngrRenderer.onPause();
    }


    protected void onTimerGameLoading(Runnable pRunnable){
        AppTELUQ_INF4018 app= (AppTELUQ_INF4018) PresentationApp.get();

        if ((PresentationApp.getGL10() != null) && !app.hasGoneThroughLoading){
            objApp.create();
            app.hasGoneThroughLoading = true;
        }else {
            timerHandlerLoading.postDelayed(timerLoading, 1000);
        }
    }

    protected void onTimerGameCalibratingSensors(Runnable pRunnable){
        AppTELUQ_INF4018 app= (AppTELUQ_INF4018) PresentationApp.get();

        if(!app.hasGoneThroughCalibration){
            app.hasGoneThroughCalibration = true;
        }
    }

    protected void onTimerGameLoop(Runnable pRunnable){
        AppTELUQ_INF4018 app= (AppTELUQ_INF4018) PresentationApp.get();

        if(PresentationApp.isRunning()) {
            if (!app.hasGoneThroughLoading) {
                objApp.create();
                app.hasGoneThroughLoading = true;
            }

            if (app.hasGoneThroughCalibration) {
                objApp.manage();
            }

            if(((ScrGameScreen)objApp.getCurScreen()).hasRequestedServerUploading) {
                Intent intentSwitchTitleToSend = new Intent(this, ActivitySend.class);
                startActivity(intentSwitchTitleToSend);
                ((ScrGameScreen)objApp.getCurScreen()).hasRequestedServerUploading = false;
            }

            glSurfaceView.requestRender();

            timerHandlerRunnable.postDelayed(pRunnable, 16);
        }else{
            this.finish();
        }
    }
}

