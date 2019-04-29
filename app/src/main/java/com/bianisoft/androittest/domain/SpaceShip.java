package com.bianisoft.androittest.domain;


public class SpaceShip extends Entity{
    public int nShieldValue; 
    public boolean isControllable; 
    public float nVelZ;
    
    public SpaceShip(){
        super();
        setTextID("SpaceShip-" + Integer.toString(nIDEntity));
    }
    
    public SpaceShip(int pnPosX, int pnPosY, int pnPosZ, int pnWidthX, int pnHeighY, int pnDeptZ){
        super(pnPosX, pnPosY, pnPosZ, pnWidthX, pnHeighY, pnDeptZ);
        
        nShieldValue= 100;
        isControllable= false;
        setTextID("SpaceShip-" + Integer.toString(nIDEntity));
    }
}
