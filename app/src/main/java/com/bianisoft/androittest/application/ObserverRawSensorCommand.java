package com.bianisoft.androittest.application;

import java.util.ArrayList;

import com.bianisoft.androittest.application.ChainOfResponsibilityDP_RawInputManagement.ChainOfResponsibility;
import com.bianisoft.androittest.application.ChainOfResponsibilityDP_RawInputManagement.CoRDetectionMovementForwardAndReverse;
import com.bianisoft.androittest.application.ChainOfResponsibilityDP_RawInputManagement.CoRDetectionMovementXAxis;
import com.bianisoft.androittest.application.ChainOfResponsibilityDP_RawInputManagement.CoRDetectionMovementYAxis;
import com.bianisoft.androittest.domain.DomainFacade;
import com.bianisoft.androittest.domain.IDomainRawSensorObserver;
import com.bianisoft.androittest.domain.RawSensorsCommand;


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
