package com.bianisoft.androittest.presentation;

import com.bianisoft.androittest.R;
import com.bianisoft.engine.Context;
import com.bianisoft.engine.Drawable;
import com.bianisoft.engine.PhysObj;
import com.bianisoft.engine._3d.Cube3D;
import com.bianisoft.engine._3d.ObjMD2;
import java.util.ArrayList;
import com.bianisoft.androittest.application.ApplicationFacade;
import com.bianisoft.androittest.domain.DomainFacade;
import com.bianisoft.androittest.domain.WallObstacle;
import com.bianisoft.engine._3d.Pyramid3D;
import com.bianisoft.engine._3d.Tunnel3D;


class WorldPresenter extends Drawable{
    Tunnel3D objTunnel;
    Pyramid3D objMD2Spaceship;
    ArrayList<Cube3D>   arWalls;
   
    
    void init(Context pCurCtx) {
        setPosZ(0);
        
        //pCurCtx.addChild(objMD2Spaceship= new ObjMD2("3d/Officer.md2", "3d/OfficerPearl.png"));
        //objMD2Spaceship.setScalingFactor(0.20f*3);
        //objMD2Spaceship.load();
        //objMD2Spaceship.setPos(0, 0, 20);
        //objMD2Spaceship.setAngleX(-180);

        objMD2Spaceship = new Pyramid3D(R.drawable.pyramide_texture);
        objMD2Spaceship.setTextID("Pyramid3D");
        objMD2Spaceship.setPos(0, 0, 5);
        objMD2Spaceship.setAngle(180, 0, 0);
        objMD2Spaceship.setScaleY(0.5f);
        objMD2Spaceship.load();
        objMD2Spaceship.show();
        objMD2Spaceship.setAngleVel(0f, 0f, 0f);
        pCurCtx.addChild(objMD2Spaceship);

        arWalls= new ArrayList<>();
        DomainFacade objDomain= DomainFacade.getFacadeObject();
        for(WallObstacle objWall : objDomain.getWorld().getArrayWalls()){
            Cube3D objCube = new Cube3D(R.drawable.wall_texture);
            pCurCtx.addChild(objCube);
            objCube.load();
            objCube.setPos(objWall.getPosX(), objWall.getPosY(), objWall.getPosZ());
            objCube.setScaleX(objWall.nWidthX/2);
            objCube.setScaleY(objWall.nHeighY/2);
            objCube.setScaleZ(objWall.nDepthZ/2);
            //objCube.setAngleVelX(1);
            arWalls.add(objCube);
        }

        pCurCtx.addChild(objTunnel= new Tunnel3D(R.drawable.tunnel_texture, 1000));
        objTunnel.load();
    }
    
    @Override
    public void manage(float pMovingScale) {
        super.manage(pMovingScale);

        DomainFacade objDomain= DomainFacade.getFacadeObject();
        ApplicationFacade objApplication= ApplicationFacade.getFacadeObject();

        objApplication.getMgrWorldPhysic().executePhysicOnWorld(objDomain.getWorld());

        PhysObj objPhysPlayerShip = objDomain.getWorld().getPlayerShip();

        if(objPhysPlayerShip.getPosX() > 80)
            objPhysPlayerShip.setPosX(80);
        if(objPhysPlayerShip.getPosX() < -80)
            objPhysPlayerShip.setPosX(-80);

        if(objPhysPlayerShip.getPosY() > 80)
            objPhysPlayerShip.setPosY(80);
        if(objPhysPlayerShip.getPosY() < -80)
            objPhysPlayerShip.setPosY(-80);

        objMD2Spaceship.setPos(-objPhysPlayerShip.getPosX()/20.0f, -objPhysPlayerShip.getPosY()/20.0f, 0);
        objTunnel.setVelZ(-objPhysPlayerShip.getVelZ()/2);

        for(Cube3D objCube : arWalls){
            objCube.setVelZ(-objPhysPlayerShip.getVelZ()/2);
        }
    }
}
