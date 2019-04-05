package com.bianisoft.androittest;

import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.bianisoft.engine.App;
import com.bianisoft.engine.manager.MyGLRenderer;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;
    private MyApp objApp;
    private MyGLRenderer renderer;

    private boolean hasGoneThroughLoading = false;


    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandlerRunnable = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            if(!hasGoneThroughLoading) {
                objApp.create();
                hasGoneThroughLoading= true;
            }

            objApp.manage();
            glSurfaceView.requestRender();

            timerHandlerRunnable.postDelayed(this, 16);
        }
    };

    Handler timerHandlerLoading = new Handler();
    Runnable timerLoading = new Runnable() {

        @Override
        public void run() {
            if ((objApp.m_objGL != null) && !hasGoneThroughLoading){
                objApp.create();
                hasGoneThroughLoading = true;
            }else {
                timerHandlerLoading.postDelayed(timerLoading, 1000);
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        objApp= new MyApp(displayMetrics.widthPixels, displayMetrics.heightPixels);
        objApp.m_objAndroidContext= this;

        renderer = new MyGLRenderer();
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(renderer);
        setContentView(glSurfaceView);

        //timerHandlerLoading.postDelayed(timerLoading, 1);
        timerHandlerRunnable.postDelayed(timerRunnable, 16);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timerHandlerRunnable.postDelayed(timerRunnable, 16);
        //timerHandlerLoading.postDelayed(timerLoading, 16);
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timerHandlerRunnable.removeCallbacks(timerRunnable);
        //timerHandlerLoading.removeCallbacks(timerLoading);
        glSurfaceView.onPause();
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

