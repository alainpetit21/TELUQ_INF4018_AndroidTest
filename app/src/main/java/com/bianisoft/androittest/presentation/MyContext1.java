/* This file is part of the Bianisoft game library.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *----------------------------------------------------------------------
 * Copyright (C) Alain Petit - alainpetit21@hotmail.com
 *
 * 18/12/10			0.1.0 First beta initial Version.
 * 12/09/11			0.1.2 Moved everything to a com.bianisoft
 *
 *-----------------------------------------------------------------------
 */
package com.bianisoft.androittest.presentation;


//Bianisoft imports
import android.app.Application;

import com.bianisoft.androittest.R;
import com.bianisoft.androittest.application.ApplicationFacade;
import com.bianisoft.engine.Context;
import com.bianisoft.engine._2d.Label2D;
import com.bianisoft.engine._3d.ObjMD2;
import com.bianisoft.engine._3d.Pyramid3D;
import com.bianisoft.engine._3d.Sprite3D;
import com.bianisoft.engine._3d.Triangle2D;
import com.bianisoft.engine._3d.Triangle3D;
import com.bianisoft.engine._3d.Tunnel3D;
import com.bianisoft.engine.manager.MngrSensorInterpretedLinearAcceleration;
import com.bianisoft.engine.manager.MngrSensorPositionalMappingUsingGravity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import javax.microedition.khronos.opengles.GL10;


public class MyContext1 extends Context{
    GUICountainer   objGUICountainer;
    WorldPresenter  objWorldPresenter;

/*    private Label2D objLabel;
    private Pyramid3D objPyramid;
    private Triangle2D objTriangle;
    private Tunnel3D objTunnel;
    private Triangle3D objTriangle3;
    private Sprite3D objSprite;
    private List<Triangle2D> arStars;
    private ObjMD2 objMD2;
*/
    public void activate(){
        super.activate();

        //Generate a bunch of "start", usefull to "pretend" that we are moving forward
/*        Random objRandom= new Random(0);
        arStars = new ArrayList<>();

        for(int i= 0; i < 250; ++i){
            Triangle2D temp= new Triangle2D(0);

            temp.setTextID("MiniStars" + String.valueOf(i));
            temp.setPos(objRandom.nextInt(20) - 10f, objRandom.nextInt(20) - 10f, -objRandom.nextInt(50));
            temp.load();
            temp.show();
            temp.setAngleVelZ(40);
            temp.setZoom(0.2f);
            temp.setFilterColor(0f, 0f, 0f, 1f);

            arStars.add(temp);
            addChild(temp);
        }
*/
        addChild(objGUICountainer = new GUICountainer());
        objGUICountainer.init(this);

        addChild(objWorldPresenter = new WorldPresenter());
        objWorldPresenter.init(this);
/*
        objTunnel= new Tunnel3D(R.drawable.tunnel_texture, 1000);
        objTunnel.load();
        addChild(objTunnel);

        objTriangle3= new Triangle3D(R.drawable.needle_texture);
        objTriangle3.setTextID("Triangle3D");
        objTriangle3.setPos(0, 1f, -0);
        objTriangle3.load();
        objTriangle3.show();
        objTriangle3.setAngleVelZ(4);
        addChild(objTriangle3);

        objSprite= new Sprite3D(R.drawable.back_gui);
        objSprite.setTextID("Sprite3D");
        objSprite.setPos(3f, 3f, 0);
        objSprite.load();
        objSprite.show();
        objSprite.setAngleVelZ(-4);
        addChild(objSprite);

        objLabel= new Label2D();
        objLabel.setTextID("Sprite3D");
        objLabel.setPos(0f, 0f, 0);
        objLabel.load();
        objLabel.show();
        addChild(objLabel);
/*
        objMD2= new ObjMD2();
        objMD2.setTextID("objMD2");
        objMD2.setPos(0f, 0f, 0);
        objMD2.load();
        objMD2.show();
        addChild(objMD2);
*//*
        objPyramid = new Pyramid3D(R.drawable.pyramide_texture);
        objPyramid.setTextID("Pyramid3D");
        objPyramid.setPos(0, 0, 5);
        objPyramid.setAngle(180, 0, 0);
        objPyramid.setScaleY(0.5f);
        objPyramid.load();
        objPyramid.show();
        objPyramid.setAngleVel(0f, 0f, 0f);
        addChild(objPyramid);
*/
        objCam.setPos(0f, 0.1f, -10f);
/*
        objTriangle= new Triangle2D(0);
        objTriangle.setTextID("Triangle2D");
        objTriangle.setPos(0, 3f, 0);
        objTriangle.load();
        objTriangle.show();
        objTriangle.setAngleVelZ(4);
        objTriangle.setFilterColor(0f, 0f, 0f, 1f);
        addChild(objTriangle);*/


    }

    public void manage(float p_nRatioMove) {
        super.manage(p_nRatioMove);

        MngrSensorPositionalMappingUsingGravity mngrSensorPosMapping = MngrSensorPositionalMappingUsingGravity.getInstance();
        MngrSensorInterpretedLinearAcceleration mngrSensorLinearInt = MngrSensorInterpretedLinearAcceleration.getInstance();

        float[] nValuesSensorsXY = mngrSensorPosMapping.getPos();
        float[] nValuesSensorsZ = mngrSensorLinearInt.getValue();

        ApplicationFacade.getFacadeObject().addRawCommand((int)nValuesSensorsXY[0], (int)nValuesSensorsXY[1], (int)(nValuesSensorsZ[1]*1));
        mngrSensorLinearInt.resetAxis(1);

        /*

        if (nPosToMoveTo[0] < -10) nPosToMoveTo[0] = -10;
        if (nPosToMoveTo[1] < -10) nPosToMoveTo[1] = -10;
        if (nPosToMoveTo[0] > 10) nPosToMoveTo[0] = 10;
        if (nPosToMoveTo[1] > 10) nPosToMoveTo[1] = 10;
        */
        //objPyramid.OverrideNextMoveTo(nPosToMoveTo[0], nPosToMoveTo[1], 400);  //Do Some sort of bitfield to only manage certain field of the MoveTO

        //Managed Indirect Movement Management PREVENTS Direct Movement Management, thus we overload it here
        //objPyramid.setVelZ(mngrSensorLinearInt.getValue()[1] / 250);
        //objPyramid.setPosZ(objPyramid.getPosZ() + objPyramid.getVelZ());

/*
        for (Triangle2D objStar : arStars) {
            if (objStar.getPosZ() < (objPyramid.getPosZ() - 25)) {
                objStar.setPosZ(objPyramid.getPosZ() + 50);
            }
            else if (objStar.getPosZ() > (objPyramid.getPosZ() + 50)) {
                objStar.setPosZ(objPyramid.getPosZ() - 25);
            }
        }*/
    }

    public void draw(GL10 gl) {
        super.draw(gl);
    }
}


