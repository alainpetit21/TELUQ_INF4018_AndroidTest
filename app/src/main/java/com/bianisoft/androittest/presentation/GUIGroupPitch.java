package com.bianisoft.androittest.presentation;


import com.bianisoft.androittest.R;
import com.bianisoft.engine.Context;
import com.bianisoft.engine.Drawable;
import com.bianisoft.engine._3d.Sprite3D;
import com.bianisoft.engine._3d.Triangle3D;
import com.bianisoft.engine._2d.Label2D;

import java.util.ArrayList;
import com.bianisoft.androittest.domain.DomainFacade;
import com.bianisoft.androittest.domain.IDomainRawSensorObserver;
import com.bianisoft.androittest.domain.RawSensorsCommand;

import javax.microedition.khronos.opengles.GL10;


public class GUIGroupPitch extends Drawable implements IDomainRawSensorObserver{
    private Sprite3D    sprBackGUIGroupPitch;
    private Triangle3D  sprNeedle;
    private Label2D     lblWrittenValue;
    private float       nValue= 0;

    
    @Override
    public void draw(GL10 gl) {
        if(nValue > 0 ){
            //This is the code for 0
            sprNeedle.setPos(-1.5f, -3.1f + (nValue/20), -1.2f);
            sprNeedle.setAngleZ(180);
        }else{
            //This is the code for 10
            sprNeedle.setPos(-1.5f, -2.85f + (nValue/20), -1.2f);
            sprNeedle.setAngleZ(0);
        }

        //Slowly return nValue to 0
        nValue*= 0.9f;
    }

    public void init(Context pCurCtx) {
        pCurCtx.addChild(sprBackGUIGroupPitch= new Sprite3D(R.drawable.back_gui_group_pitch));
        sprBackGUIGroupPitch.setZoom(3.73f/4);
        sprBackGUIGroupPitch.setScaleX(1.33f);
        sprBackGUIGroupPitch.setPos(-1.5f, -3f, -1.11f);
        sprBackGUIGroupPitch.load();
        
        pCurCtx.addChild(sprNeedle= new Triangle3D(R.drawable.needle_texture));
        sprNeedle.setZoom(0.5f);
        sprNeedle.setScaleX(1.5f);
        sprNeedle.setScaleY(0.25f);
        sprNeedle.load();
        
        DomainFacade.getFacadeObject().registerObserverForRawSensorCollection(this);
    }

    @Override
    public void notify(ArrayList<RawSensorsCommand> pLstRawSensorCommands) {
        RawSensorsCommand obj= pLstRawSensorCommands.get(pLstRawSensorCommands.size()-1);
        
        nValue= obj.nRotationPitch;
    }
}
