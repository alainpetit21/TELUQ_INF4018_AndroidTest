package com.bianisoft.androittest.application;

import java.util.ArrayList;
import com.bianisoft.androittest.domain.DomainFacade;
import com.bianisoft.androittest.domain.GameCommand;
import com.bianisoft.androittest.domain.PlayerShip;

    
public class StrategyActionMoveReverse implements Strategy{

    @Override
    public void execute(ArrayList<GameCommand> pArGameCommand, int pNIdxToStart){
        //Update the Domain Entity for ship
        PlayerShip objPlayer = (PlayerShip) DomainFacade.getFacadeObject().getWorld().getPlayerShip();
        
        objPlayer.MoveReverse();
        
        //Update the GameCommand Start Idx
        ApplicationFacade.getFacadeObject().IncrementGameCommandIdxToStart();
    }
}
