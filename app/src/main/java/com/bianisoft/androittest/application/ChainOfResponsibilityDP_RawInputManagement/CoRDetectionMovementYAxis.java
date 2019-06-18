package com.bianisoft.androittest.application.ChainOfResponsibilityDP_RawInputManagement;

import java.util.ArrayList;
import com.bianisoft.androittest.domain.DomainFacade;
import com.bianisoft.androittest.domain.RawSensorsCommand;


public class CoRDetectionMovementYAxis extends ChainOfResponsibility{
    
    @Override
    public boolean Handle(ArrayList<RawSensorsCommand> pArRawSensorCommand, int pNIdxToStart){
        DomainFacade objDomain= DomainFacade.getFacadeObject();
        boolean ret= true;
        
        for(int i= pNIdxToStart; i < pArRawSensorCommand.size(); ++i){
            RawSensorsCommand objSensorRead2= pArRawSensorCommand.get(i);
            int nValuePitch = objSensorRead2.nRotationPitch;

            for(int j= 0; j < Math.abs(nValuePitch); ++j)  
                objDomain.addGameCommand(0, nValuePitch / Math.abs(nValuePitch), 0);
        }

        //In usual Chain of Responsibility, only one "handler" will take care of 
        //a situation. However in this usage, this class can handle X and Y axis 
        // detection and the successor can detect the Z axis (which typicaly use
        // another technique and other variables, and another successor to 
        //handle no movement at all. Therefore, it would always be true in this
        // application
        return super.Handle(pArRawSensorCommand, pNIdxToStart) || ret;
    }
}
