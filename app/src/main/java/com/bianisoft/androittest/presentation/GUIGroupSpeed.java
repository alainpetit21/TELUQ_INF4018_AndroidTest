package com.bianisoft.androittest.presentation;

import com.bianisoft.androittest.R;
import com.bianisoft.engine.Context;
import com.bianisoft.engine.Drawable;
import com.bianisoft.engine._2d.Label2D;
import java.util.ArrayList;
import com.bianisoft.androittest.domain.DomainFacade;
import com.bianisoft.androittest.domain.IDomainRawSensorObserver;
import com.bianisoft.androittest.domain.RawSensorsCommand;
import com.bianisoft.engine._3d.Sprite3D;
import com.bianisoft.engine._3d.Triangle3D;

import javax.microedition.khronos.opengles.GL10;


public class GUIGroupSpeed extends Drawable implements IDomainRawSensorObserver{
    private Sprite3D sprBackGUIGroupSpeed;
    private Triangle3D sprNeedle;
    private Label2D     lblWrittenValue;
    private float       nValue= 0;

    
    @Override
    public void draw(GL10 gl) {
        sprNeedle.setPos(-2.4f, -2.7f - (nValue * 0.06f), -1.2f);
    }

    public void init(Context pCurCtx) {
        pCurCtx.addChild(sprBackGUIGroupSpeed= new Sprite3D(R.drawable.back_gui_group_speed));
        sprBackGUIGroupSpeed.setZoom(3.73f/4);
        sprBackGUIGroupSpeed.setPos(-2.7f, -2.7f, -1.1f);
        sprBackGUIGroupSpeed.load();
        
        pCurCtx.addChild(sprNeedle= new Triangle3D(R.drawable.needle_texture));
        sprNeedle.setZoom(0.2f);
        sprNeedle.setScaleX(0.33f);
        sprNeedle.setAngleZ(90);
        sprNeedle.setPos(-2.4f, -2.7f, -1.2f);
        sprNeedle.load();

        DomainFacade.getFacadeObject().registerObserverForRawSensorCollection(this);
    }

    @Override
    public void notify(ArrayList<RawSensorsCommand> pLstRawSensorCommands) {
        RawSensorsCommand obj= pLstRawSensorCommands.get(pLstRawSensorCommands.size()-1);
        
        if( obj.nAcceleratingZ != 0)
            nValue= obj.nAcceleratingZ;
    }
}
