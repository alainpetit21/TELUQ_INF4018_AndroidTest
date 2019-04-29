package com.bianisoft.androittest.application;

import java.util.ArrayList;
import com.bianisoft.androittest.domain.DomainFacade;
import com.bianisoft.androittest.domain.GameCommand;
import com.bianisoft.androittest.domain.PlayerShip;

    
public class StrategyActionMoveForward implements Strategy{

    @Override
    public void execute(ArrayList<GameCommand> pArGameCommand, int pNIdxToStart){
        PlayerShip objPlayer = (PlayerShip) DomainFacade.getFacadeObject().getWorld().getPlayerShip();
        
        objPlayer.MoveForward();
        
        //Update the GameCommand Start Idx
        ApplicationFacade.getFacadeObject().IncrementGameCommandIdxToStart();
    }
}
