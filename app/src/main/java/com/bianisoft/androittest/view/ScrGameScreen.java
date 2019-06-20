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
package com.bianisoft.androittest.view;


//Bianisoft imports

import com.bianisoft.androittest.R;
import com.bianisoft.androittest.controller.ApplicationFacade;
import com.bianisoft.engine.Screen;
import com.bianisoft.engine._3d.Sprite3D;
import com.bianisoft.engine.manager.MngrTouchScreen;
import com.bianisoft.engine.manager.MngrSensorPositionalMappingUsingGravity;


import javax.microedition.khronos.opengles.GL10;


public class ScrGameScreen extends Screen {
    private GUICountainer   objGUICountainer;
    private WorldPresenter  objWorldPresenter;
    private Sprite3D backCalibration;


    public void activate(){
        super.activate();

        addChild(objGUICountainer = new GUICountainer());
        objGUICountainer.init(this);

        addChild(objWorldPresenter = new WorldPresenter());
        objWorldPresenter.init(this);

        addChild(backCalibration = new Sprite3D(R.drawable.back_calibration));
        backCalibration.setZoom(4f);
        backCalibration.setPosZ(-1.5f);
        backCalibration.load();

        objCam.setPos(0f, 0.1f, -10f);

        MngrTouchScreen.getInstance().setMovementY(-2);
    }

    public void manage(float p_nRatioMove) {
        super.manage(p_nRatioMove);
        backCalibration.hide();

        MngrSensorPositionalMappingUsingGravity mngrSensorPosMapping = MngrSensorPositionalMappingUsingGravity.getInstance();
        MngrTouchScreen mgrTouchScreen = MngrTouchScreen.getInstance();

        float[] nValuesSensorsXY = mngrSensorPosMapping.getPos();
        int nValueZ = (mgrTouchScreen.getMovementY() / 50);

        if (nValueZ > -2) {
            nValueZ = -2;
            MngrTouchScreen.getInstance().setMovementY(nValueZ*50);
        } else if (nValueZ < -20){
            nValueZ = -20;
            MngrTouchScreen.getInstance().setMovementY(nValueZ*50);
        }


        ApplicationFacade.getFacadeObject().addRawCommand((int)nValuesSensorsXY[0], (int)nValuesSensorsXY[1], (-nValueZ));
    }

    public void draw(GL10 gl) {
        super.draw(gl);
    }
}


