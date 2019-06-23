package com.bianisoft.engine;

import android.app.Application;
import android.content.Context;

import com.bianisoft.engine.helper.datatypes.Int;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


public class FrontendApp extends Application {
    private static Context mContext;
    private static GL10 objGL;
    private static FrontendApp theApp;

    private ArrayList<Int> arStackScreenIdx;
    private Screen m_scrCur;

    private ArrayList<Screen> arAllScreens;
    private String stGameName;
    private long nlastFrameTick;
    private long nthisFrameTick;
    private int nCptLoop;
    private boolean isRunning;


    public FrontendApp(String p_stName){
        stGameName = p_stName;
        theApp = this;

        arStackScreenIdx = new ArrayList<Int>();
        arAllScreens = new ArrayList<Screen>();
        isRunning = true;
    }

    public static FrontendApp get(){
        return FrontendApp.theApp;
    }

    public static void exit(){
        FrontendApp.theApp.isRunning = false;
    }

    public static boolean isRunning(){
        return FrontendApp.theApp.isRunning;
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        FrontendApp.mContext = mContext;
    }

    public static GL10 getGL10() {
        return objGL;
    }

    public static void setGL(GL10 pGL10) {
        FrontendApp.objGL = pGL10;
    }

    public static FrontendApp getFrontendApp() {
        return FrontendApp.theApp;
    }

    public Screen getCurScreen(){
        return m_scrCur;
    }

    public void setCurScreen(Int p_nScreen){
        setCurScreen(p_nScreen.m_nValue);
    }

    public void setCurScreen(int p_nScreen){
        if(m_scrCur != null)
            m_scrCur.deActivate();

        m_scrCur = arAllScreens.get(p_nScreen);
        m_scrCur.activate();
    }

    public void addScreen(Screen p_scr){
        p_scr.m_nIndex= arAllScreens.size();
        arAllScreens.add(p_scr);
    }

    public void create(){
        nlastFrameTick = System.nanoTime();
        load();
    }

    public void destroy(){
        FrontendApp.setContext(null);
    }


    public void manage(){
        nthisFrameTick = System.nanoTime();

//        float nRationTime= (nthisFrameTick - nlastFrameTick)/16666667.0f;
        float nRationTime= 1f;

        FrontendApp.theApp.m_scrCur.manage(nRationTime);

        nlastFrameTick = nthisFrameTick;
        nCptLoop += 1;
    }

    public void load(){};
    public void unload(){};

    public String getVersion() { return "";}
}
