package com.bianisoft.androittest.presentation;

import com.bianisoft.androittest.R;
import com.bianisoft.engine.Context;
import com.bianisoft.engine.Drawable;
import com.bianisoft.engine._3d.Sprite3D;


public class GUICountainer extends Drawable{
    private Sprite3D    objGUIBackground;

    private GUIGroupPitch objGroupPitch;
    private GUIGroupRoll objGroupRoll;
    private GUIGroupSpeed objGroupSpeed;
    private GUIGroupToolbar objGroupToolbar;
    
   
    void init(Context pCurCtx) {
        setPosZ(-1);
        
        pCurCtx.addChild(objGUIBackground= new Sprite3D(R.drawable.back_gui));
        objGUIBackground.setZoom(3.73f);
        objGUIBackground.setPosZ(-1);
        objGUIBackground.load();
        
        pCurCtx.addChild(objGroupPitch= new GUIGroupPitch());
        objGroupPitch.init(pCurCtx);

        pCurCtx.addChild(objGroupRoll= new GUIGroupRoll());
        objGroupRoll.init(pCurCtx);

        pCurCtx.addChild(objGroupSpeed= new GUIGroupSpeed());
        objGroupSpeed.init(pCurCtx);

        pCurCtx.addChild(objGroupToolbar= new GUIGroupToolbar());
        objGroupToolbar.init(pCurCtx);
    }
}
