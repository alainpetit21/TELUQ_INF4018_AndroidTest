package com.bianisoft.project_inf4018.controller;

import java.util.ArrayList;

import com.bianisoft.project_inf4018.controller.ChainOfResponsibilityDP_RawInputManagement.ChainOfResponsibility;
import com.bianisoft.project_inf4018.controller.ChainOfResponsibilityDP_RawInputManagement.CoRDetectionMovementForwardAndReverse;
import com.bianisoft.project_inf4018.controller.ChainOfResponsibilityDP_RawInputManagement.CoRDetectionMovementXAxis;
import com.bianisoft.project_inf4018.controller.ChainOfResponsibilityDP_RawInputManagement.CoRDetectionMovementYAxis;
import com.bianisoft.project_inf4018.model.DomainFacade;
import com.bianisoft.project_inf4018.model.IDomainRawSensorObserver;
import com.bianisoft.project_inf4018.model.RawSensorsCommand;


public class ObserverRawSensorCommand implements IDomainRawSensorObserver{
    ChainOfResponsibility corRawToGameHandler;
    
    
    public ObserverRawSensorCommand(){
        DomainFacade.getFacadeObject().registerObserverForRawSensorCollection(this);

        corRawToGameHandler= new CoRDetectionMovementXAxis();
        corRawToGameHandler.AddSuccessor(new CoRDetectionMovementYAxis());
        corRawToGameHandler.AddSuccessor(new CoRDetectionMovementForwardAndReverse());
    }
            
    @Override
    public void notify(ArrayList<RawSensorsCommand> pArrayListCommands) {
        ApplicationFacade objApplicationFacade= ApplicationFacade.getFacadeObject();
        int nIdxToStart= objApplicationFacade.GetRawStartIdx();

        corRawToGameHandler.Run(pArrayListCommands, nIdxToStart);
    }
}
