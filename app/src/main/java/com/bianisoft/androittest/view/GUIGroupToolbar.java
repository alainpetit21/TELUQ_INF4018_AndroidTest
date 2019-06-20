package com.bianisoft.androittest.view;

import com.bianisoft.androittest.R;
import com.bianisoft.engine.Screen;
import com.bianisoft.engine.Drawable;
import com.bianisoft.androittest.controller.ApplicationFacade;
import com.bianisoft.androittest.controller.ModMgrRecorderPoster;
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

        if(modRecorderPoster.getStartRawSensor() != -1)
            if(modRecorderPoster.getStopRawSensor() != -1)
                btStop.show(false);
            else
                btStop.show();
        else
            btStop.show(false);

        if((modRecorderPoster.getStartRawSensor() != -1) && 
            (modRecorderPoster.getStopRawSensor() != -1))
            btSend.show(true);
        else
            btSend.show(false);

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
