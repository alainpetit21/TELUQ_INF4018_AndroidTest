package com.bianisoft.project_inf4018.view;

import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.bianisoft.engine.FrontendApp;
import com.bianisoft.engine.manager.MngrSensorGyroscopicRotationAcceleration;
import com.bianisoft.engine.manager.MngrTouchScreen;
import com.bianisoft.engine.manager.MngrGLRendererAndroid;
import com.bianisoft.engine.manager.MngrSensorPositionalMappingUsingGravity;
import com.bianisoft.project_inf4018.controller.ApplicationFacade;
import com.bianisoft.project_inf4018.model.DomainFacade;


public class ActivityGame extends AppCompatActivity {
    private AppPrototypeTELUQ_INF4018 objApp;
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
        AppPrototypeTELUQ_INF4018 app= (AppPrototypeTELUQ_INF4018)FrontendApp.get();

        if ((FrontendApp.getGL10() != null) && !app.hasGoneThroughLoading){
            objApp.create();
            app.hasGoneThroughLoading = true;
        }else {
            timerHandlerLoading.postDelayed(timerLoading, 1000);
        }
    }

    protected void onTimerGameCalibratingSensors(Runnable pRunnable){
        AppPrototypeTELUQ_INF4018 app= (AppPrototypeTELUQ_INF4018)FrontendApp.get();

        if(!app.hasGoneThroughCalibration){
            app.hasGoneThroughCalibration = true;
        }
    }

    protected void onTimerGameLoop(Runnable pRunnable){
        AppPrototypeTELUQ_INF4018 app= (AppPrototypeTELUQ_INF4018)FrontendApp.get();

        if(FrontendApp.isRunning()) {
            if (!app.hasGoneThroughLoading) {
                objApp.create();
                app.hasGoneThroughLoading = true;
            }

            if (app.hasGoneThroughCalibration) {
                objApp.manage();
            }

            glSurfaceView.requestRender();

            timerHandlerRunnable.postDelayed(pRunnable, 16);
        }else{
            this.finish();
        }
    }
}

