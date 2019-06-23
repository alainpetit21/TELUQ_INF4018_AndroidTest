package com.bianisoft.project_inf4018.controller.ChainOfResponsibilityDP_RawInputManagement;

import java.util.ArrayList;

import com.bianisoft.project_inf4018.controller.ApplicationFacade;
import com.bianisoft.project_inf4018.model.RawSensorsCommand;


public class ChainOfResponsibility {
    private ChainOfResponsibility _successor;
    
    ChainOfResponsibility(){
        _successor= null;
    }
    
    public void AddSuccessor(ChainOfResponsibility pObjSuccessor){
        if(_successor != null)
            _successor.AddSuccessor(pObjSuccessor);
        else
            _successor= pObjSuccessor;
    }

    public boolean Handle(ArrayList<RawSensorsCommand> pArRawSensorCommand, int pNIdxToStart){
        if (_successor == null)
            return false;
        
        return _successor.Handle(pArRawSensorCommand, pNIdxToStart);
    }

    public void Run(ArrayList<RawSensorsCommand> pArRawSensorCommand, int pNIdxToStart){
        if(!Handle(pArRawSensorCommand, pNIdxToStart))
            System.out.print("-===================UNHANDLED CHAIN OF RESPONSIBILITY=============-\nClient of this ChainOfResponsible System is not handling all cases!!!\n");
        
        if(pNIdxToStart < pArRawSensorCommand.size())
            ApplicationFacade.getFacadeObject().IncrementRawSensorCommandIdxToStart();
    }
}
