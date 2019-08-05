package com.bianisoft.project_inf4018.controller.StrategyDP_GameplayCommandsManagement;

import java.util.ArrayList;

import com.bianisoft.project_inf4018.controller.ApplicationFacade;
import com.bianisoft.project_inf4018.model.DomainFacade;
import com.bianisoft.project_inf4018.model.GameCommand;
import com.bianisoft.project_inf4018.model.PlayerShip;

    
public class StrategyActionMoveUp implements Strategy{

    @Override
    public void execute(ArrayList<GameCommand> pArGameCommand, int pNIdxToStart){
        //Update the Domain EntityGame for ship
        PlayerShip objPlayer = DomainFacade.getFacadeObject().getWorld().getPlayerShip();
        
        objPlayer.MoveUp();
        
        //Update the GameCommand Start Idx
        ApplicationFacade.getFacadeObject().IncrementGameCommandIdxToStart();
    }
}
