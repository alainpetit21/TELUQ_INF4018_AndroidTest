package com.bianisoft.androittest.domain;


public class EnnemyShip extends SpaceShip{
   
   
    public EnnemyShip(){
        super();
        isControllable= false;
        setTextID("EnnemyShip-" + Integer.toString(nIDEntity));
    }
    
    public EnnemyShip(int pnPosX, int pnPosY, int pnPosZ, int pnWidthX, int pnHeighY, int pnDeptZ){
        super(pnPosX, pnPosY, pnPosZ, pnWidthX, pnHeighY, pnDeptZ);
        isControllable= false;
        setTextID("EnnemyShip-" + Integer.toString(nIDEntity));
    }


    //Probably some AI function to find the player
    //Or some function to follow a movement Path
}
