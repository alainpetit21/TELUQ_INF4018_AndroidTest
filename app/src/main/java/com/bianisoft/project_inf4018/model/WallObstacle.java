package com.bianisoft.project_inf4018.model;


import com.bianisoft.engine.Obj;

public class WallObstacle extends EntityGame {
    public static final int CLS_ID = Obj.IDCLASS_GAME | 0x09;      //00001001

    
    public WallObstacle(){
        super();
        setTextID("Wall-" + nIDEntity);
        setClassID(WallObstacle.CLS_ID);
    }

    public WallObstacle(int pnPosX, int pnPosY, int pnPosZ, int pnWidthX, int pnHeighY, int pnDeptZ){
        super(pnPosX*20, pnPosY*20, pnPosZ*20*20, pnWidthX*20, pnHeighY*20, pnDeptZ*20);
        setTextID("Wall-" + nIDEntity);
        setClassID(WallObstacle.CLS_ID);
    }
}
