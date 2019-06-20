package com.bianisoft.androittest.view;

import com.bianisoft.androittest.R;
import com.bianisoft.androittest.controller.ModMgrWorldPhysic;
import com.bianisoft.androittest.model.EntityGame;
import com.bianisoft.androittest.model.PlayerShip;
import com.bianisoft.androittest.model.SpaceShip;
import com.bianisoft.engine.Screen;
import com.bianisoft.engine.Drawable;
import com.bianisoft.engine.PhysObj;
import com.bianisoft.engine._3d.Cube3D;

import java.util.ArrayList;
import com.bianisoft.androittest.controller.ApplicationFacade;
import com.bianisoft.androittest.model.DomainFacade;
import com.bianisoft.androittest.model.WallObstacle;
import com.bianisoft.engine._3d.Pyramid3D;
import com.bianisoft.engine._3d.Tunnel3D;


public class WorldPresenter extends Drawable{
    public class CollisionResponse implements ModMgrWorldPhysic.ICollicionCallback{

        @Override
        public void onCollision(EntityGame obj1, EntityGame obj2) {
            if (obj1.isKindOf(SpaceShip.CLS_ID) && (obj2.isKindOf(WallObstacle.CLS_ID))){
                obj3DSpaceship.hide();  //Game Over dude
                obj3DSpaceship.setVelZ(0);
                obj1.setVelZ(0);

            }
        }
    }

    Tunnel3D objTunnel;
    Pyramid3D obj3DSpaceship;
    ArrayList<Cube3D>   arWalls;
   
    
    void init(Screen pCurCtx) {
        setPosZ(0);

        obj3DSpaceship = new Pyramid3D(R.drawable.pyramide_texture);
        obj3DSpaceship.setTextID("Pyramid3D");
        obj3DSpaceship.setPos(0, 0, 10);
        obj3DSpaceship.setAngle(180, 0, 0);
        obj3DSpaceship.setScaleY(0.5f);
        obj3DSpaceship.load();
        obj3DSpaceship.show();
        obj3DSpaceship.setAngleVel(0f, 0f, 0f);
        pCurCtx.addChild(obj3DSpaceship);

        arWalls= new ArrayList<>();
        DomainFacade objDomain= DomainFacade.getFacadeObject();
        for(WallObstacle objWall : objDomain.getWorld().getArrayWalls()){
            Cube3D objCube = new Cube3D(R.drawable.wall_texture);
            pCurCtx.addChild(objCube);
            objCube.load();
//            objCube.setPos(objWall.getPosX()/20, objWall.getPosY()/20, objWall.getPosZ()/20);
            objCube.setPos(-objWall.getPosX()/20, -objWall.getPosY()/20, objWall.getPosZ()/20);
            objCube.setScaleX(objWall.nWidthX/(20.0f*2));
            objCube.setScaleY(objWall.nHeighY/(20.0f*2));
            objCube.setScaleZ(objWall.nDepthZ/(20.0f*2));
            //objCube.setAngleVelX(1);
            arWalls.add(objCube);
        }

        pCurCtx.addChild(objTunnel= new Tunnel3D(R.drawable.tunnel_texture, 1000));
        objTunnel.load();
    }

    public void onCollisionDetected(EntityGame obj1, EntityGame obj2){
        if(obj1.isKindOf(PlayerShip.CLS_ID) && obj2.isKindOf(WallObstacle.CLS_ID)){

        }
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

        obj3DSpaceship.setPos(-objPhysPlayerShip.getPosX()/20.0f, -objPhysPlayerShip.getPosY()/20.0f, 0);
        objTunnel.setVelZ(-objPhysPlayerShip.getVelZ());

        for(Cube3D objCube : arWalls){
            objCube.setVelZ(-objPhysPlayerShip.getVelZ());
            System.out.printf("\nShip : %.2f, %.2f, %.2f ; \tWall : X:%.2f, Y%.2f, Z:%.2f",
                    obj3DSpaceship.getPosX(), obj3DSpaceship.getPosY(), obj3DSpaceship.getPosZ(),
                    objCube.getPosX(),
                    objCube.getPosY(),
                    objCube.getPosZ()/20f);
        }

        /*
*/

        try {
            objApplication.getMgrWorldPhysic().detectCollisions(objDomain.getWorld(), new CollisionResponse());
        }catch(Exception e){
            System.out.print("wrong reflection usage");
        }
    }
}
