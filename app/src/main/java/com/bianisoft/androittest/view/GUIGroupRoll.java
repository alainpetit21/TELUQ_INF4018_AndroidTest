package com.bianisoft.androittest.view;

import com.bianisoft.androittest.R;
import com.bianisoft.engine.Screen;
import com.bianisoft.engine.Drawable;
import java.util.ArrayList;
import com.bianisoft.androittest.model.DomainFacade;
import com.bianisoft.androittest.model.IDomainRawSensorObserver;
import com.bianisoft.androittest.model.RawSensorsCommand;
import com.bianisoft.engine._3d.Sprite3D;
import com.bianisoft.engine._3d.Triangle3D;
import com.bianisoft.engine._2d.Label2D;

import javax.microedition.khronos.opengles.GL10;


public class GUIGroupRoll extends Drawable implements IDomainRawSensorObserver{
    private Sprite3D sprBackGUIGroupRoll;
    private Triangle3D sprNeedle;
    private Label2D     lblWrittenValue;
    private float       nValue= 0;
            

    @Override
    public void draw(GL10 gl) {
        sprNeedle.setAngleZ(nValue*8.5f);

        //Slowly return nValue to 0
        nValue*= 0.9f;
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

        DomainFacade.getFacadeObject().registerObserverForRawSensorCollection(this);
    }

    @Override
    public void notify(ArrayList<RawSensorsCommand> pLstRawSensorCommands) {
        RawSensorsCommand obj= pLstRawSensorCommands.get(pLstRawSensorCommands.size()-1);
        
        nValue= obj.nRotationRoll;
    }
}
