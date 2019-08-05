package com.bianisoft.project_inf4018.view;


import com.bianisoft.project_inf4018.R;
import com.bianisoft.engine.Screen;
import com.bianisoft.engine.Drawable;
import com.bianisoft.engine._3d.Sprite3D;
import com.bianisoft.engine._3d.Triangle3D;
import com.bianisoft.engine._2d.Label2D;

import java.util.ArrayList;
import java.util.Locale;

import com.bianisoft.project_inf4018.model.DomainFacade;
import com.bianisoft.project_inf4018.model.IDomainRawSensorInformativeObserver;
import com.bianisoft.project_inf4018.model.IDomainRawSensorObserver;
import com.bianisoft.project_inf4018.model.RawSensorsCommand;
import com.bianisoft.project_inf4018.model.RawSensorsInformative;

import javax.microedition.khronos.opengles.GL10;


public class GUIGroupPitch extends Drawable{
    private Sprite3D    sprBackGUIGroupPitch;
    private Triangle3D  sprNeedle;
    private Label2D     lblWrittenValue;
    private Label2D     lblAccelValue;
    private float       nValue= 0;
    private float       nValueAccel= 0;

    private int nDelayToDisplayGyroPitch;


    class GUIGroupPitch_ObserverRawSensor implements IDomainRawSensorObserver{
        @Override
        public void notify(ArrayList<RawSensorsCommand> pObj) {
            notifyCommand(pObj);
        }
    }

    class GUIGroupPitch_ObserverRawSensorInformative implements IDomainRawSensorInformativeObserver{

        @Override
        public void notify(ArrayList<RawSensorsInformative> pObj) {
            notifyInformative(pObj);
        }
    }

    @Override
    public void manage(float p_fTimeScaleFactor) {
        super.manage(p_fTimeScaleFactor);

        if(nDelayToDisplayGyroPitch > 0) {
            nDelayToDisplayGyroPitch--;
        }else {
            if (nValueAccel > 0.5f) {
                nValueAccel *= 0.9f;

                String buf = String.format(Locale.getDefault(), "Accel: %.0f", nValueAccel/10f);
                lblAccelValue.setText(buf);
            }else {
                nValueAccel = 0.5f;
            }
        }
    }

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
    }

    public void init(Screen pCurCtx) {
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
        
        pCurCtx.addChild(lblWrittenValue= new Label2D());
        lblWrittenValue.setText("");
        lblWrittenValue.setZoom(0.25f);
        lblWrittenValue.setPos(-3.75f, -5.5f, -1.11f);
        lblWrittenValue.load();

        pCurCtx.addChild(lblAccelValue= new Label2D());
        lblAccelValue.setText("Accel: 0.0");
        lblAccelValue.setZoom(0.25f);
        lblAccelValue.setPos(-3.75f, -5f, -1.11f);
        lblAccelValue.load();

        DomainFacade.getFacadeObject().registerObserverForRawSensorCollection(new GUIGroupPitch_ObserverRawSensor());
        DomainFacade.getFacadeObject().registerObserverForRawSensorInformativeCollection(new GUIGroupPitch_ObserverRawSensorInformative());
    }

    public void notifyCommand(ArrayList<RawSensorsCommand> pLstRawSensorCommands) {
        RawSensorsCommand obj= pLstRawSensorCommands.get(pLstRawSensorCommands.size()-1);
        String buf;

        nValue= obj.nRotationPitch;
        buf = String.format(Locale.getDefault(), "Pitch: %.0f", nValue);
        lblWrittenValue.setText(buf);
    }

    public void notifyInformative(ArrayList<RawSensorsInformative> pLstRawSensorInformative) {
        RawSensorsInformative obj= pLstRawSensorInformative.get(pLstRawSensorInformative.size()-1);
        String buf;

        if (obj.nAccelRotationPitch > nValueAccel){
            nValueAccel = obj.nAccelRotationPitch;
            buf = String.format(Locale.getDefault(), "Accel: %.0f", nValueAccel/10f);
            lblAccelValue.setText(buf);
            nDelayToDisplayGyroPitch = 60; //60 loops of manage, more or less 1sec
        }
    }
}
