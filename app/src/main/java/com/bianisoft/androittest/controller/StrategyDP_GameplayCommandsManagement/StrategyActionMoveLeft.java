package com.bianisoft.androittest.controller.StrategyDP_GameplayCommandsManagement;

import java.util.ArrayList;

import com.bianisoft.androittest.controller.ApplicationFacade;
import com.bianisoft.androittest.model.DomainFacade;
import com.bianisoft.androittest.model.GameCommand;
import com.bianisoft.androittest.model.PlayerShip;

    
public class StrategyActionMoveLeft implements Strategy{

    @Override
    public void execute(ArrayList<GameCommand> pArGameCommand, int pNIdxToStart){
        //Update the Domain EntityGame for ship
        PlayerShip objPlayer = (PlayerShip) DomainFacade.getFacadeObject().getWorld().getPlayerShip();
        
        objPlayer.MoveLeft();
        
        //Update the GameCommand Start Idx
        ApplicationFacade.getFacadeObject().IncrementGameCommandIdxToStart();
    }
}