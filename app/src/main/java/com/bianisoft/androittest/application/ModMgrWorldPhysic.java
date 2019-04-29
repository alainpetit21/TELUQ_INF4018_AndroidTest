package com.bianisoft.androittest.application;

import com.bianisoft.androittest.domain.Entity;
import com.bianisoft.androittest.domain.World;


public class ModMgrWorldPhysic {
    
    
    public void executePhysicOnWorld(World pobjWorld){
        //Look at all entities and process their physics
        for(Entity objEntity : pobjWorld.arShips){
            objEntity.manage(1f);
            objEntity.ApplyGenericFriction(0.9f);
//            objEntity.setAccel(0f, 0f, objEntity.getAccelZ());
            objEntity.setAccel(0f, 0f, objEntity.getAccelZ());

            System.out.printf("Entity : %s Pos:%f,%f,%f Vel:%f,%f,%f Accel:%f,%f,%f\n",
                    objEntity.getTextID(),
                    objEntity.getPosX(), objEntity.getPosY(), objEntity.getPosZ(), 
                    objEntity.getVelX(), objEntity.getVelY(), objEntity.getVelZ(), 
                    objEntity.getAccelX(), objEntity.getAccelY(), objEntity.getAccelZ());
        }
    }
}
