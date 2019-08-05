package com.bianisoft.project_inf4018.model;


import com.bianisoft.engine.Obj;

public class SpaceShip extends EntityGame {
    public static final int CLS_ID = Obj.IDCLASS_GAME | 0x03;      //00000011

    public int nShieldValue;
    public boolean isControllable; 
    public float nVelZ;
    
    public SpaceShip(){
        super();
        setTextID("SpaceShip-" + nIDEntity);
        setClassID(SpaceShip.CLS_ID);
    }
    
    public SpaceShip(int pnPosX, int pnPosY, int pnPosZ, int pnWidthX, int pnHeighY, int pnDeptZ){
        super(pnPosX, pnPosY, pnPosZ, pnWidthX, pnHeighY, pnDeptZ);
        
        nShieldValue= 100;
        isControllable= false;
        setTextID("SpaceShip-" + nIDEntity);
        setClassID(SpaceShip.CLS_ID);
    }
}
