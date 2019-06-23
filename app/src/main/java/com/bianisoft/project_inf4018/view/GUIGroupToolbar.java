package com.bianisoft.project_inf4018.view;

import com.bianisoft.project_inf4018.R;
import com.bianisoft.engine.Screen;
import com.bianisoft.engine.Drawable;
import com.bianisoft.project_inf4018.controller.ApplicationFacade;
import com.bianisoft.project_inf4018.controller.ModMgrRecorderPoster;
import com.bianisoft.engine._3d.Sprite3D;

import javax.microedition.khronos.opengles.GL10;


public class GUIGroupToolbar extends Drawable{
    private Sprite3D    btRecord;
    private Sprite3D    btStop;
    private Sprite3D    btSend;
    private Sprite3D    btQuit;
    

    @Override
    public void draw(GL10 gl) {
        ModMgrRecorderPoster modRecorderPoster= ApplicationFacade.getFacadeObject().getMgrRecorderPoster();

        btRecord.show();
        btQuit.show();
        btStop.show();
        btSend.show();

        if(modRecorderPoster.getStartRawSensor() != -1)
            if(modRecorderPoster.getStopRawSensor() != -1)
                btStop.setFilterAlpha(0.25f);
            else
                btStop.setFilterAlpha(1f);
        else
            btStop.setFilterAlpha(0.25f);

        if((modRecorderPoster.getStartRawSensor() != -1) && 
            (modRecorderPoster.getStopRawSensor() != -1))
            btSend.setFilterAlpha(1f);
        else
            btSend.setFilterAlpha(0.25f);

    }

    public void init(Screen pCurCtx) {
        pCurCtx.addChild(btRecord= new Sprite3D(R.drawable.bt_record));
        btRecord.setZoom(0.3f);
        btRecord.setScaleX(3f);
        btRecord.setPos(2.9f, 3.65f, -1.1f);
        btRecord.load();

        pCurCtx.addChild(btStop= new Sprite3D(R.drawable.bt_stop));
        btStop.setZoom(0.3f);
        btStop.setScaleX(3f);
        btStop.setPos(1f, 3.65f, -1.1f);
        btStop.load();

        pCurCtx.addChild(btSend= new Sprite3D(R.drawable.bt_send));
        btSend.setZoom(0.3f);
        btSend.setScaleX(3f);
        btSend.setPos(-1f, 3.65f, -1.1f);
        btSend.load();

        pCurCtx.addChild(btQuit= new Sprite3D(R.drawable.bt_quit));
        btQuit.setZoom(0.3f);
        btQuit.setScaleX(3f);
        btQuit.setPos(-2.9f, 3.65f, -1.1f);
        btQuit.load();
    }
}
