package com.bianisoft.project_inf4018.controller;

import com.bianisoft.project_inf4018.model.EntityGame;
import com.bianisoft.project_inf4018.model.WallObstacle;
import com.bianisoft.project_inf4018.model.World;


public class ModMgrWorldPhysic {

    public interface ICollicionCallback{
        void onCollision(EntityGame obj1, EntityGame obj2);
    }

    public void detectCollisions(World pobjWorld, ICollicionCallback objCallback) {
        //Look at all entities and process their physics
        for(EntityGame objShip : pobjWorld.arShips){
            float nShipX=  objShip.getPosX();
            float nShipY= objShip.getPosY();
            float shipZ=  objShip.getPosZ();

            for(WallObstacle objWall : pobjWorld.arWalls){
                float nWallX1= (objWall.getPosX() - objWall.nWidthX/2f);
                float nWallY1= (objWall.getPosY() - objWall.nHeighY/2f);
                float nWallZ1=  (objWall.getPosZ() - objWall.nDepthZ/2f)/20f;

                float nWallX2=  (objWall.getPosX() + objWall.nWidthX/2f);
                float nWallY2=  (objWall.getPosY() + objWall.nHeighY/2f);
                float nWallZ2=  (objWall.getPosZ() + objWall.nDepthZ/2f)/20f;
/*
                System.out.printf("\nCore Ship : %.2f, %.2f, %.2f ; \tWall : X:%.2fto%.2f, Y%.2fto%.2f, Z:%.2fto%.2f",
                        nShipX, nShipY, shipZ,
                        nWallX1, nWallX2,
                        nWallY1, nWallY2,
                        nWallZ1, nWallZ2);
*/
                if((nShipX >= nWallX1) && (nShipX <= nWallX2)) {
                    if((nShipY >= nWallY1) && (nShipY <= nWallY2)) {
                        if ((shipZ >= nWallZ1) && (shipZ <= nWallZ2)) {
                            objCallback.onCollision(objShip, objWall);
                        }
                    }
                }
            }
        }
    }
    
    public void executePhysicOnWorld(World pobjWorld){
        //Look at all entities and process their physics
        for(EntityGame objEntity : pobjWorld.arShips){
            objEntity.manage(1f);
            objEntity.ApplyGenericFriction(0.9f);
            objEntity.setAccel(0f, 0f, objEntity.getAccelZ());
/*
            System.out.printf("EntityGame : %s Pos:%f,%f,%f Vel:%f,%f,%f Accel:%f,%f,%f\n",
                    objEntity.getTextID(),
                    objEntity.getPosX(), objEntity.getPosY(), objEntity.getPosZ(), 
                    objEntity.getVelX(), objEntity.getVelY(), objEntity.getVelZ(), 
                    objEntity.getAccelX(), objEntity.getAccelY(), objEntity.getAccelZ());*/
        }
    }
}
