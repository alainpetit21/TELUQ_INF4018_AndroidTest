package com.bianisoft.androittest.application;

import java.util.ArrayList;

import com.bianisoft.androittest.domain.GameCommand;

    
public class StrategyDoNothing implements Strategy{

    @Override
    public void execute(ArrayList<GameCommand> pArGameCommand, int pNIdxToStart){
        //Update the GameCommand Start Idx
        ApplicationFacade.getFacadeObject().IncrementGameCommandIdxToStart();
    }
}
