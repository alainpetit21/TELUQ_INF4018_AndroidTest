package com.bianisoft.engine;

import android.app.Application;
import android.content.Context;

import com.bianisoft.engine.helper.datatypes.Int;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


public class PresentationApp extends Application {
    private static Context mContext;
    private static GL10 objGL;
    private static PresentationApp theApp;

    private ArrayList<Int> arStackScreenIdx;
    private Screen m_scrCur;

    private ArrayList<Screen> arAllScreens;
    private String stGameName;
    private long nlastFrameTick;
    private long nthisFrameTick;
    private int nCptLoop;
    private boolean isRunning;


    public PresentationApp(String p_stName){
        stGameName = p_stName;
        theApp = this;

        arStackScreenIdx = new ArrayList<Int>();
        arAllScreens = new ArrayList<Screen>();
        isRunning = true;
    }

    public static PresentationApp get(){
        return PresentationApp.theApp;
    }

    public static void exit(){
        PresentationApp.theApp.isRunning = false;
    }

    public static boolean isRunning(){
        return PresentationApp.theApp.isRunning;
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        PresentationApp.mContext = mContext;
    }

    public static GL10 getGL10() {
        return objGL;
    }

    public static void setGL(GL10 pGL10) {
        PresentationApp.objGL = pGL10;
    }

    public static PresentationApp getAppInstance() {
        return PresentationApp.theApp;
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
        PresentationApp.setContext(null);
    }


    public void manage(){
        nthisFrameTick = System.nanoTime();

//        float nRationTime= (nthisFrameTick - nlastFrameTick)/16666667.0f;
        float nRationTime= 1f;

        PresentationApp.theApp.m_scrCur.manage(nRationTime);

        nlastFrameTick = nthisFrameTick;
        nCptLoop += 1;
    }

    public void load(){}

    public void unload(){}

    public String getVersion() { return "";}
}
