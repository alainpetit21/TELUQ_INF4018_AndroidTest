package com.bianisoft.project_inf4018.view;

import com.bianisoft.engine.PresentationApp;


public class AppTELUQ_INF4018 extends PresentationApp {
    public static final int IDCTX_MYCONTEXT	= 0x0;

    public boolean hasGoneThroughLoading;
    public boolean hasGoneThroughCalibration;


    public AppTELUQ_INF4018(){
        super("AppTELUQ_INF4018");
        hasGoneThroughLoading= false;
        hasGoneThroughCalibration= false;
    }

    public String getVersion(){
        return "0.1";
    }

    public void load(){
        addScreen(new ScrGameScreen());
        setCurScreen(IDCTX_MYCONTEXT);
    }
}