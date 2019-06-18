package com.bianisoft.androittest.domain;


import java.util.Random;


public class PlayerShip extends SpaceShip{
    public static final float ACCEL_MAX = 0.01f;
    public String stNamePlayer; 

   
    public PlayerShip(){
        super();
        isControllable= true;
        setTextID("PlayerShip-" + Integer.toString(nIDEntity));
        
        Random objRand= new Random();
        stNamePlayer= "PlayerName-" + Integer.toString(objRand.nextInt(10000));
    }
    
    public PlayerShip(int pnPosX, int pnPosY, int pnPosZ, int pnWidthX, int pnHeighY, int pnDeptZ){
        super(pnPosX, pnPosY, pnPosZ, pnWidthX, pnHeighY, pnDeptZ);
        isControllable= true;
        setTextID("PlayerShip-" + Integer.toString(nIDEntity));
    }

    //Abstracted out Movement Functions
    public void MoveUp(){
        setAccelY(getAccelY() - ACCEL_MAX);
    }
    
    public void MoveDown(){
        setAccelY(getAccelY() + ACCEL_MAX);
    }
    
    public void MoveLeft() { setAccelX(getAccelX() - ACCEL_MAX); }
    
    public void MoveRight() { setAccelX(getAccelX() + ACCEL_MAX); }
    
    public void MoveZ(float p_nSpeed) {
        //System.out.print ("PlayerShip SetVel : " + p_nSpeed + "\n");
        setVelZ(p_nSpeed /10.0f);

    }

    public String GetPlayerName(){
        return stNamePlayer;
    }
}
