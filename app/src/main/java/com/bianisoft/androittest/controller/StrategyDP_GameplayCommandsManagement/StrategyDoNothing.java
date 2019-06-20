package com.bianisoft.androittest.controller.StrategyDP_GameplayCommandsManagement;

import java.util.ArrayList;

import com.bianisoft.androittest.controller.ApplicationFacade;
import com.bianisoft.androittest.model.GameCommand;

    
public class StrategyDoNothing implements Strategy{

    @Override
    public void execute(ArrayList<GameCommand> pArGameCommand, int pNIdxToStart){
        //Update the GameCommand Start Idx
        ApplicationFacade.getFacadeObject().IncrementGameCommandIdxToStart();
    }
}
