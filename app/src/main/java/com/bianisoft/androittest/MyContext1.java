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
package com.bianisoft.androittest;


//Bianisoft imports
import com.bianisoft.engine.Context;
import com.bianisoft.engine._3d.Cube3D;
import com.bianisoft.engine._3d.Triangle2D;
import com.bianisoft.engine.manager.MngrSensorInterpretedLinearAcceleration;
import com.bianisoft.engine.manager.MngrSensorPositionalMappingUsingGravity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;


import javax.microedition.khronos.opengles.GL10;


public class MyContext1 extends Context{
    private Cube3D objCube;
    private Triangle2D objTriangle;
    private List<Triangle2D> arStars;

    public void activate(){
        super.activate();

        //Generate a bunch of "start", usefull to "pretend" that we are moving forward
        Random objRandom= new Random(0);
        arStars = new ArrayList<>();

        for(int i= 0; i < 1000; ++i){
            Triangle2D temp= new Triangle2D(0);

            temp.setTextID("MiniStars");
            temp.setPos(objRandom.nextInt(20) - 10f, objRandom.nextInt(20) - 10f, objRandom.nextInt(100));
            temp.load();
            temp.show();
            temp.setAngleVelZ(40);
            temp.setZoom(0.2f);
            temp.setFilterColor(0f, 0f, 0f, 1f);
            addChild(temp);
        }

        objCube= new Cube3D(R.drawable.boxcrate);
        objCube.setTextID("Cube");
        objCube.setPos(0, 0, 10);
        objCube.setFilterColor(0f, 1f, 0f, 0.5f);
        objCube.load();
        objCube.show();
        objCube.setAngleVel(0f, 0f, 0f);
        addChild(objCube);

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

    public void manage(float p_nRatioMove){
        super.manage(p_nRatioMove);

        MngrSensorPositionalMappingUsingGravity mngrSensorPosMapping = MngrSensorPositionalMappingUsingGravity.getInstance();
        MngrSensorInterpretedLinearAcceleration mngrSensorLinearInt = MngrSensorInterpretedLinearAcceleration.getInstance();

        float[] nPosToMoveTo= mngrSensorPosMapping.getPos();

        if(nPosToMoveTo[0] < -10)   nPosToMoveTo[0]= -10;
        if(nPosToMoveTo[1] < -10)   nPosToMoveTo[1]= -10;
        if(nPosToMoveTo[0] > 10)   nPosToMoveTo[0]= 10;
        if(nPosToMoveTo[1] > 10)   nPosToMoveTo[1]= 10;

        objCube.OverrideNextMoveTo(nPosToMoveTo[0], nPosToMoveTo[1], 400);  //Do Some sort of bitfield to only manage certain field of the MoveTO

        //Managed Indirect Movement Management PREVENTS Direct Movement Management, thus we overload it here
        objCube.setVelZ(mngrSensorLinearInt.getValue()[1]/250);
        objCube.setPosZ(objCube.getPosZ()+objCube.getVelZ());
    }

    public void draw(GL10 gl) {
        super.draw(gl);
    }
}


