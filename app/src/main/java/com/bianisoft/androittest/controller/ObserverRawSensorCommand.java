package com.bianisoft.androittest.controller;

import java.util.ArrayList;

import com.bianisoft.androittest.controller.ChainOfResponsibilityDP_RawInputManagement.ChainOfResponsibility;
import com.bianisoft.androittest.controller.ChainOfResponsibilityDP_RawInputManagement.CoRDetectionMovementForwardAndReverse;
import com.bianisoft.androittest.controller.ChainOfResponsibilityDP_RawInputManagement.CoRDetectionMovementXAxis;
import com.bianisoft.androittest.controller.ChainOfResponsibilityDP_RawInputManagement.CoRDetectionMovementYAxis;
import com.bianisoft.androittest.model.DomainFacade;
import com.bianisoft.androittest.model.IDomainRawSensorObserver;
import com.bianisoft.androittest.model.RawSensorsCommand;


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
        DomainFacade objDomainFacade= DomainFacade.getFacadeObject();
        int nIdxToStart= objApplicationFacade.GetRawStartIdx();

        corRawToGameHandler.Run(pArrayListCommands, nIdxToStart);
    }
}
