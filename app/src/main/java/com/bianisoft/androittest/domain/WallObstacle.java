package com.bianisoft.androittest.domain;


public class WallObstacle extends Entity{
    
    
    public WallObstacle(){
        super();
        setTextID("Wall-" + Integer.toString(nIDEntity));
    }

    public WallObstacle(int pnPosX, int pnPosY, int pnPosZ, int pnWidthX, int pnHeighY, int pnDeptZ){
        super(pnPosX/20, pnPosY/20, pnPosZ/20, pnWidthX/20, pnHeighY/20, pnDeptZ/20);
        setTextID("Wall-" + Integer.toString(nIDEntity));
    }
}
