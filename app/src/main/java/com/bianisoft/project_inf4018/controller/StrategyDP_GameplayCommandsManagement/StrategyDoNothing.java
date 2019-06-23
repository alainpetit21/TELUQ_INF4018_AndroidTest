package com.bianisoft.project_inf4018.controller.StrategyDP_GameplayCommandsManagement;

import java.util.ArrayList;

import com.bianisoft.project_inf4018.controller.ApplicationFacade;
import com.bianisoft.project_inf4018.model.GameCommand;

    
public class StrategyDoNothing implements Strategy{

    @Override
    public void execute(ArrayList<GameCommand> pArGameCommand, int pNIdxToStart){
        //Update the GameCommand Start Idx
        ApplicationFacade.getFacadeObject().IncrementGameCommandIdxToStart();
    }
}
