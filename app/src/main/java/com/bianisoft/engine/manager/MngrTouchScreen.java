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

    public MngrTouchScreen() {
        objThisInstance = this;

        this.bIsOn = false;
        this.nPosXActionDown= -1;
        this.nPosYActionDown= -1;

        this.nMovementX= 0;
        this.nMovementY= 0;
        this.nLastMovementX= 0;
        this.nLastMovementY= 0;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //System.out.print("\nonTouch(View v, MotionEvent event) " +event + "\n");

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            this.bIsOn= true;
            this.nPosXActionDown = (int)event.getX();
            this.nPosYActionDown = (int)event.getY();

        }else if (event.getAction() == MotionEvent.ACTION_UP){
            this.bIsOn= false;
            this.nPosXActionDown = -1;
            this.nPosYActionDown = -1;
            nLastMovementX = nMovementX;
            nLastMovementY = nMovementY;

        }else if (event.getAction() == MotionEvent.ACTION_MOVE){
            nMovementX = (int)event.getX() - nPosXActionDown;
            nMovementY = (int)event.getY() - nPosYActionDown;
        }

        return true;
    }

    public int getMovementX(){
        return bIsOn?nMovementX:nLastMovementX;
    }
    public int getMovementY(){
        return bIsOn?nMovementY:nLastMovementY;
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
