package com.bianisoft.project_inf4018.view;

import com.bianisoft.project_inf4018.R;
import com.bianisoft.engine.Screen;
import com.bianisoft.engine.Drawable;
import java.util.ArrayList;
import java.util.Locale;

import com.bianisoft.project_inf4018.controller.ApplicationFacade;
import com.bianisoft.project_inf4018.model.DomainFacade;
import com.bianisoft.project_inf4018.model.IDomainRawSensorInformativeObserver;
import com.bianisoft.project_inf4018.model.IDomainRawSensorObserver;
import com.bianisoft.project_inf4018.model.RawSensorsCommand;
import com.bianisoft.engine._3d.Sprite3D;
import com.bianisoft.engine._3d.Triangle3D;
import com.bianisoft.engine._2d.Label2D;
import com.bianisoft.project_inf4018.model.RawSensorsInformative;

import javax.microedition.khronos.opengles.GL10;


public class GUIGroupRoll extends Drawable{
    private Sprite3D    sprBackGUIGroupRoll;
    private Triangle3D  sprNeedle;
    private Label2D     lblWrittenValue;
    private Label2D     lblAccelValue;
    private float       nValue= 0;
    private float       nValueAccel= 0;

    private int nDelayToDisplayGyroRoll;



    class GUIGroupRoll_ObserverRawSensor implements IDomainRawSensorObserver{
        @Override
        public void notify(ArrayList<RawSensorsCommand> pObj) {
            notifyCommand(pObj);
        }
    }

    class GUIGroupRoll_ObserverRawSensorInformative implements IDomainRawSensorInformativeObserver {

        @Override
        public void notify(ArrayList<RawSensorsInformative> pObj) {
            notifyInformative(pObj);
        }
    }


    @Override
    public void manage(float p_fTimeScaleFactor) {
        super.manage(p_fTimeScaleFactor);

        if(nDelayToDisplayGyroRoll > 0) {
            nDelayToDisplayGyroRoll--;
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
        sprNeedle.setAngleZ(nValue*8.5f);
    }

    public void init(Screen pCurCtx) {
        pCurCtx.addChild(sprBackGUIGroupRoll= new Sprite3D(R.drawable.back_gui_group_roll));
        sprBackGUIGroupRoll.setZoom(3.73f/4);
        sprBackGUIGroupRoll.setPos(2.5f, -3f, -1.1f);
        sprBackGUIGroupRoll.setScaleX(1.33f);
        sprBackGUIGroupRoll.load();
        
        pCurCtx.addChild(sprNeedle= new Triangle3D(R.drawable.needle_texture));
        sprNeedle.setZoom(0.5f);
        sprNeedle.setScaleX(0.33f);
        sprNeedle.setPos(2.5f, -3f, -1.2f);
        sprNeedle.load();

        pCurCtx.addChild(lblWrittenValue= new Label2D());
        lblWrittenValue.setText("");
        lblWrittenValue.setZoom(0.25f);
        lblWrittenValue.setPos(3f, -5.5f, -1.11f);
        lblWrittenValue.load();

        pCurCtx.addChild(lblAccelValue= new Label2D());
        lblAccelValue.setText("Accel: 0.0");
        lblAccelValue.setZoom(0.25f);
        lblAccelValue.setPos(3f, -5f, -1.11f);
        lblAccelValue.load();

        DomainFacade.getFacadeObject().registerObserverForRawSensorCollection(new GUIGroupRoll_ObserverRawSensor());
        DomainFacade.getFacadeObject().registerObserverForRawSensorInformativeCollection(new GUIGroupRoll_ObserverRawSensorInformative());
    }

    public void notifyCommand(ArrayList<RawSensorsCommand> pLstRawSensorCommands) {
        RawSensorsCommand obj= pLstRawSensorCommands.get(pLstRawSensorCommands.size()-1);
        String buf;

        nValue= obj.nRotationRoll;
        buf = String.format(Locale.getDefault(), "Roll: %.0f", nValue);
        lblWrittenValue.setText(buf);
    }

    public void notifyInformative(ArrayList<RawSensorsInformative> pLstRawSensorInformative) {
        RawSensorsInformative obj= pLstRawSensorInformative.get(pLstRawSensorInformative.size()-1);
        String buf;

        if (obj.nAccelRotationRoll > nValueAccel){
            nValueAccel = obj.nAccelRotationRoll;
            buf = String.format(Locale.getDefault(), "Accel: %.0f", nValueAccel/10f);
            lblAccelValue.setText(buf);
            nDelayToDisplayGyroRoll = 60; //60 loops of manage, more or less 1sec
        }
    }
}
