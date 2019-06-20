package com.bianisoft.androittest.view;

import com.bianisoft.androittest.R;
import com.bianisoft.engine.Screen;
import com.bianisoft.engine.Drawable;
import com.bianisoft.engine._2d.Label2D;
import java.util.ArrayList;
import java.util.Locale;

import com.bianisoft.androittest.model.DomainFacade;
import com.bianisoft.androittest.model.IDomainRawSensorObserver;
import com.bianisoft.androittest.model.RawSensorsCommand;
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
        sprNeedle.setPos(-3.1f, 2.f - (nValue * 0.06f)*2.5f, -1.2f);
    }

    public void init(Screen pCurCtx) {
        pCurCtx.addChild(sprBackGUIGroupSpeed= new Sprite3D(R.drawable.back_gui_group_speed));
        sprBackGUIGroupSpeed.setZoom(3.73f/4);
        sprBackGUIGroupSpeed.setScaleY(2.5f);
        sprBackGUIGroupSpeed.setPos(-3.6f, 0.5f, -1.1f);
        sprBackGUIGroupSpeed.load();
        
        pCurCtx.addChild(sprNeedle= new Triangle3D(R.drawable.needle_texture));
        sprNeedle.setZoom(0.2f);
        sprNeedle.setScaleX(0.33f);
        sprNeedle.setAngleZ(90);
        sprNeedle.setPos(-3.2f, 0.5f, -1.2f);
        sprNeedle.load();

        pCurCtx.addChild(lblWrittenValue= new Label2D());
        lblWrittenValue.setText("");
        lblWrittenValue.setZoom(0.25f);
        lblWrittenValue.setPos(-5.5f, 4f, -1.11f);
        lblWrittenValue.load();

        DomainFacade.getFacadeObject().registerObserverForRawSensorCollection(this);
    }

    @Override
    public void notify(ArrayList<RawSensorsCommand> pLstRawSensorCommands) {
        RawSensorsCommand obj= pLstRawSensorCommands.get(pLstRawSensorCommands.size()-1);
        
        if( obj.nAcceleratingZ != 0)
            nValue= obj.nAcceleratingZ;

        String buf = String.format(Locale.getDefault(), "%.0f", nValue/2);
        lblWrittenValue.setText(buf);
    }
}
