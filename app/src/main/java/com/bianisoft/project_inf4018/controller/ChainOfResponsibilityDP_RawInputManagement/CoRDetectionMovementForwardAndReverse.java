package com.bianisoft.project_inf4018.controller.ChainOfResponsibilityDP_RawInputManagement;

import java.util.ArrayList;
import com.bianisoft.project_inf4018.model.DomainFacade;
import com.bianisoft.project_inf4018.model.RawSensorsCommand;


public class CoRDetectionMovementForwardAndReverse extends ChainOfResponsibility{
    
    @Override
    public boolean Handle(ArrayList<RawSensorsCommand> pArRawSensorCommand, int pNIdxToStart){
        DomainFacade objDomain= DomainFacade.getFacadeObject();
        boolean ret= true;
        
        for(int i= pNIdxToStart; i < pArRawSensorCommand.size(); ++i){
            RawSensorsCommand objSensorRead2= pArRawSensorCommand.get(i);
            int nValueAccel = objSensorRead2.nAcceleratingZ;

            objDomain.addGameCommand(0, 0, nValueAccel);
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
