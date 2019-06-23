package com.bianisoft.project_inf4018.model;

import com.bianisoft.engine.Obj;
import com.bianisoft.engine.PhysObj;


public class EntityGame extends PhysObj{
    public static final int CLS_ID = Obj.IDCLASS_GAME | 0x01;

    public static int nIDEntity= 0;
    public int nWidthX;
    public int nHeighY;
    public int nDepthZ; 


    public EntityGame(){
        setPos(0f, 0f, 0f);
        nWidthX= 1;
        nHeighY= 1;
        nDepthZ= 1;
        setTextID("GenericEntity-" + Integer.toString(++nIDEntity));
        setClassID(EntityGame.CLS_ID);
    }

    public EntityGame(float pnPosX, float pnPosY, float pnPosZ, int pnWidthX, int pnHeighY, int pnDepthZ){
        setPos(pnPosX, pnPosY, pnPosZ);
        nWidthX= pnWidthX;
        nHeighY= pnHeighY;
        nDepthZ= pnDepthZ;
        setTextID("GenericEntity-" + Integer.toString(++nIDEntity));
        setClassID(EntityGame.CLS_ID);
    }

    public void ApplyGenericFriction(float p_fRatio){
        setAngleVelX(getAngleVelX()*p_fRatio);
        setAngleVelY(getAngleVelY()*p_fRatio);
        setAngleVelZ(getAngleVelZ()*p_fRatio);

        setVelX(getVelX()*p_fRatio);
        setVelY(getVelY()*p_fRatio);
        setVelZ(getVelZ()*1f);
    }

}
