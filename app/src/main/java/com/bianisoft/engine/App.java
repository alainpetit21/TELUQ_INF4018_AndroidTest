package com.bianisoft.engine;

import com.bianisoft.engine.helper.datatypes.Int;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public abstract class App {
    public static App	g_theApp;
    public static int	g_nWidth;
    public static int	g_nHeight;

    public ArrayList<Context>	m_arObj			= new ArrayList<Context>();
    public ArrayList<Int>		m_stkIdxContext	= new ArrayList<Int>();
    public Context		m_ctxCur;
    public android.content.Context m_objAndroidContext;   // Application context needed to read image (NEW)
    public GL10 m_objGL;   // Application context needed to read image (NEW)

    public String		m_stGameName;
    public long			m_lastFrameTick;
    public long			m_thisFrameTick;
    public int			m_nCptLoop;
    public boolean		m_isRunning= true;
    public boolean		m_isFullScreen;

    private Random      objRandom;

    public static App get(){
        return App.g_theApp;
    }

    public static void exit(){
        App.g_theApp.m_isRunning= false;
    }

    public App(String p_stName, int p_nWidth, int p_nHeight){
        m_stGameName= p_stName;
        g_theApp	= this;
        g_nWidth	= p_nWidth;
        g_nHeight	= p_nHeight;

        objRandom= new Random();
    }

    public void create(){
        objRandom.setSeed((int)System.nanoTime());
        m_lastFrameTick= System.nanoTime();
        load();
    }

    void destroy(){
    }

    public void manage(){
        m_thisFrameTick= System.nanoTime();

        float nRationTime= (m_thisFrameTick - m_lastFrameTick)/16666667.0f;

        App.g_theApp.m_ctxCur.manage(nRationTime);


        m_lastFrameTick= m_thisFrameTick;
        m_nCptLoop+= 1;
    }

    public static Context getCurContext(){
        return App.g_theApp.m_ctxCur;
    }

    public void setCurContext(Int p_nContext)	{setCurContext(p_nContext.m_nValue);}
    public void setCurContext(int p_nContext){
        if(m_ctxCur != null)
            m_ctxCur.deActivate();

        m_ctxCur= m_arObj.get(p_nContext);
        m_ctxCur.activate();
    }

    public void addContext(Context p_ctx){
        p_ctx.m_nIndex= m_arObj.size();
        m_arObj.add(p_ctx);
    }

    public abstract void load();

    public abstract String getVersion();

}
