package com.bianisoft.androittest.domain;

import com.bianisoft.engine.PhysObj;


public class Entity extends PhysObj{
    public static int nIDEntity= 0;
    public int nWidthX;
    public int nHeighY;
    public int nDepthZ; 


    public Entity(){
        setPos(0f, 0f, 0f);
        nWidthX= 1;
        nHeighY= 1;
        nDepthZ= 1;
        setTextID("GenericEntity-" + Integer.toString(++nIDEntity));
    }

    public Entity(float pnPosX, float pnPosY, float pnPosZ, int pnWidthX, int pnHeighY, int pnDepthZ){
        setPos(pnPosX, pnPosY, pnPosZ);
        nWidthX= pnWidthX;
        nHeighY= pnHeighY;
        nDepthZ= pnDepthZ;
        setTextID("GenericEntity-" + Integer.toString(++nIDEntity));
    }
}
