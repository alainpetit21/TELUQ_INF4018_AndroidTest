package com.bianisoft.project_inf4018.view;

import com.bianisoft.engine.FrontendApp;

public class AppPrototypeTELUQ_INF4018 extends FrontendApp {
    public static final int IDCTX_MYCONTEXT	= 0x0;

    public boolean hasGoneThroughLoading;
    public boolean hasGoneThroughCalibration;


    public AppPrototypeTELUQ_INF4018(){
        super("AppPrototypeTELUQ_INF4018");
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

    public void manage(){
        super.manage();
    }
}