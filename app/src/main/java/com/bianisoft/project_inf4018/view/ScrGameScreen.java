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
package com.bianisoft.project_inf4018.view;


//Bianisoft imports

import com.bianisoft.engine.PresentationApp;
import com.bianisoft.engine.manager.MngrSensorGyroscopicRotationAcceleration;
import com.bianisoft.project_inf4018.R;
import com.bianisoft.project_inf4018.controller.ApplicationFacade;
import com.bianisoft.project_inf4018.controller.ModMgrRecorder;
import com.bianisoft.engine.Screen;
import com.bianisoft.engine._3d.Sprite3D;
import com.bianisoft.engine.manager.MngrTouchScreen;
import com.bianisoft.engine.manager.MngrSensorPositionalMappingUsingGravity;


import javax.microedition.khronos.opengles.GL10;


public class ScrGameScreen extends Screen {
    private GUICountainer   objGUICountainer;
    private WorldPresenter  objWorldPresenter;
    private Sprite3D backCalibration;
    private Sprite3D backUpload;
    public boolean hasRequestedServerUploading;

    public void activate(){
        addChild(objGUICountainer = new GUICountainer());
        objGUICountainer.init(this);

        addChild(objWorldPresenter = new WorldPresenter());
        objWorldPresenter.init(this);

        addChild(backCalibration = new Sprite3D(R.drawable.back_calibration));
        backCalibration.setZoom(4f);
        backCalibration.setPosZ(-1.5f);
        backCalibration.load();

        addChild(backUpload = new Sprite3D(R.drawable.back_upload));
        backUpload.setZoom(4f);
        backUpload.setPosZ(-1.5f);
        backUpload.load();
        backUpload.hide();

        objCam.setPos(0f, 0.1f, -10f);

        MngrTouchScreen.getInstance().setMovementY(-2);

        hasRequestedServerUploading = false;

        //We do this at the end IOT set the special flags for "finished Loading"
        super.activate();
    }

    public void manage(float p_nRatioMove) {
        super.manage(p_nRatioMove);
        backCalibration.hide();

        MngrSensorPositionalMappingUsingGravity mngrSensorPosMapping = MngrSensorPositionalMappingUsingGravity.getInstance();
        MngrSensorGyroscopicRotationAcceleration mngrSensorGyros = MngrSensorGyroscopicRotationAcceleration.getInstance();
        MngrTouchScreen mgrTouchScreen = MngrTouchScreen.getInstance();

        float[] nValuesSensorsXY = mngrSensorPosMapping.getPos();
        int nValueZ = (mgrTouchScreen.getMovementY() / 50);

        if (nValueZ > -2) {
            nValueZ = -2;
            mgrTouchScreen.setMovementY(nValueZ * 50);
        } else if (nValueZ < -20) {
            nValueZ = -20;
            mgrTouchScreen.setMovementY(nValueZ * 50);
        }

        float[] data = mngrSensorGyros.getGyroData();

        if (Math.abs(data[0]) > 0.1f)
            ApplicationFacade.getFacadeObject().addRawGyroCommand((int)(data[0]*10), 0);

        if (Math.abs(data[1]) > 0.1f)
            ApplicationFacade.getFacadeObject().addRawGyroCommand(0, (int)(data[1]*10));

        ApplicationFacade.getFacadeObject().addRawCommand((int)nValuesSensorsXY[0], (int)nValuesSensorsXY[1], (-nValueZ));

        ModMgrRecorder mgrRecorderPoster= ApplicationFacade.getFacadeObject().getMgrRecorder();
        int nPosClickX = mgrTouchScreen.getClickX();
        int nPosClickY = mgrTouchScreen.getClickY();

        if(nPosClickY > 1550){
            if((nPosClickX > 50) && (nPosClickX < 250)) {
                System.out.printf("\nRecord - Click at Pos: %d, %d", nPosClickX, nPosClickY);
                mgrRecorderPoster.start();

            }else if((nPosClickX > 300) && (nPosClickX < 500)) {
                System.out.printf("\nRecord - Click at Pos: %d, %d", nPosClickX, nPosClickY);
                mgrRecorderPoster.stop();

            }else if((nPosClickX > 600) && (nPosClickX < 750)) {
                System.out.printf("\nSend - Click at Pos: %d, %d", nPosClickX, nPosClickY);
                hasRequestedServerUploading= true;
                backUpload.show();

            }else if((nPosClickX > 850) && (nPosClickX < 1050)) {
                System.out.printf("\nQuit - Click at Pos: %d, %d", nPosClickX, nPosClickY);
                PresentationApp.exit();
            }else {
                System.out.printf("\nClick at Pos: %d, %d", nPosClickX, nPosClickY);
            }
            mgrTouchScreen.resetClicksystem();
        }
    }
}


