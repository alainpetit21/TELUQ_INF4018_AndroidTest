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

import javax.microedition.khronos.opengles.GL10;


public class MyContext1 extends Context{
    private Cube3D objCube;
    private Triangle2D objTriangle;


    public void activate(){
        super.activate();


        objTriangle= new Triangle2D(0);
        objTriangle.setTextID("Triangle2D");
        objTriangle.setPos(0, 3f, 0);
        objTriangle.load();
        objTriangle.show();
        objTriangle.setAngleVelZ(4);
        objTriangle.setFilterColor(0f, 0f, 0f, 1f);
        addChild(objTriangle);

        objCube= new Cube3D(R.drawable.boxcrate);
        objCube.setTextID("Cube");
        objCube.setPos(0, 0, 0);
        objCube.setFilterColor(0f, 1f, 0f, 0.5f);
        objCube.load();
        objCube.show();
        objCube.setAngleVel(1f, 1f, 1f);
        addChild(objCube);
    }

    public void manage(float p_nRatioMove){
        super.manage(p_nRatioMove);
    }

    public void draw(GL10 gl) {
        super.draw(gl);
    }
}


