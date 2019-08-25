package com.bianisoft.engine.manager;

import android.view.MotionEvent;
import android.view.View;

public class MngrTouchScreen implements View.OnTouchListener {
    private static MngrTouchScreen objThisInstance;

    private boolean bIsOn;
    private int nPosXActionDown;
    private int nPosYActionDown;

    private int nMovementX;
    private int nMovementY;

    private int nLastMovementX;
    private int nLastMovementY;

    private int nPosLastClickX;
    private int nPosLastClickY;

    public MngrTouchScreen() {
        objThisInstance = this;

        bIsOn = false;
        nPosXActionDown= -1;
        nPosYActionDown= -1;

        nPosLastClickX= -1;
        nPosLastClickY= -1;

        nMovementX= 0;
        nMovementY= 0;
        nLastMovementX= 0;
        nLastMovementY= 0;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //System.out.print("\nonTouch(View v, MotionEvent event) " +event + "\n");

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            bIsOn= true;
            nPosXActionDown = (int)event.getX();
            nPosYActionDown = (int)event.getY();
            nPosLastClickX= -1;
            nPosLastClickY= -1;


        }else if (event.getAction() == MotionEvent.ACTION_UP){
            bIsOn= false;
            nPosXActionDown = -1;
            nPosYActionDown = -1;

            //If movement if smaller than 50 pixel, assuming that we are not trying to move, but click on a button
            if((Math.abs(nMovementX) > 50) || (Math.abs(nMovementY) > 50)){
                nLastMovementX = nMovementX;
                nLastMovementY = nMovementY;
            }else{
                 //This is a click
                nPosLastClickX= (int)event.getX();
                nPosLastClickY= (int)event.getY();
                v.performClick();
            }
        }else if (event.getAction() == MotionEvent.ACTION_MOVE){
            nMovementX = (int)event.getX() - nPosXActionDown;
            nMovementY = (int)event.getY() - nPosYActionDown;
        }

        return true;
    }

    //Try to Change that to observer Pattern
    public int getClickX(){
        MngrGLRendererAndroid objRenderer= MngrGLRendererAndroid.getInstance();
        float value =  (float)nPosLastClickX / (float)objRenderer.getSurfaceWidth() * 100.0f;

        return (int)value;
    }

    public int getClickY(){
        MngrGLRendererAndroid objRenderer= MngrGLRendererAndroid.getInstance();
        float value =  (float)nPosLastClickY / (float)objRenderer.getSurfaceHeight() * 100.0f;

        return (int)value;
    }

    public int resetClicksystem(){

        return nPosLastClickY= nPosLastClickX= -1;
    }

    //Basically threat lower than 50 as if it was not "activated" yet
    public int getMovementX(){
        if( bIsOn) {
            if (Math.abs(nMovementX) > 50) {
                return nMovementX;
            }else {
                return nLastMovementX;
            }
        }else {
            return nLastMovementX;
        }
    }

    //Basically threat lower than 50 as if it was not "activated" yet
    public int getMovementY(){
        if( bIsOn) {
            if (Math.abs(nMovementY) > 50) {
                return nMovementY;
            }else {
                return nLastMovementY;
            }
        }else {
            return nLastMovementY;
        }
    }

    public void setMovementX(int p_nValue){
        if(!bIsOn)
            nMovementX= nLastMovementX= p_nValue;
        else
            nMovementX= p_nValue;
    }
    public void setMovementY(int p_nValue){
        if(!bIsOn)
            nMovementY= nLastMovementY= p_nValue;
        else
            nMovementY= p_nValue;
    }

    public static MngrTouchScreen getInstance(){
        return objThisInstance;
    }
}
