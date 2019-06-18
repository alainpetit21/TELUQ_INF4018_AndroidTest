package com.bianisoft.androittest.application.StrategyDP_GameplayCommandsManagement;

import java.util.ArrayList;

import com.bianisoft.androittest.application.ApplicationFacade;
import com.bianisoft.androittest.domain.DomainFacade;
import com.bianisoft.androittest.domain.GameCommand;
import com.bianisoft.androittest.domain.PlayerShip;

    
public class StrategyActionMoveDown implements Strategy{

    @Override
    public void execute(ArrayList<GameCommand> pArGameCommand, int pNIdxToStart){
        //Update the Domain Entity for ship
        PlayerShip objPlayer;
        
        objPlayer = (PlayerShip) DomainFacade.getFacadeObject().getWorld().getPlayerShip();
        objPlayer.MoveDown();
        
        //Update the GameCommand Start Idx
        ApplicationFacade.getFacadeObject().IncrementGameCommandIdxToStart();
    }
}
