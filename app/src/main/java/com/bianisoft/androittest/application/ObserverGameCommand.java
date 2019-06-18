package com.bianisoft.androittest.application;

import java.util.ArrayList;
import java.util.HashMap;

import com.bianisoft.androittest.application.StrategyDP_GameplayCommandsManagement.Strategy;
import com.bianisoft.androittest.application.StrategyDP_GameplayCommandsManagement.StrategyActionMoveDown;
import com.bianisoft.androittest.application.StrategyDP_GameplayCommandsManagement.StrategyActionMoveForward;
import com.bianisoft.androittest.application.StrategyDP_GameplayCommandsManagement.StrategyActionMoveLeft;
import com.bianisoft.androittest.application.StrategyDP_GameplayCommandsManagement.StrategyActionMoveReverse;
import com.bianisoft.androittest.application.StrategyDP_GameplayCommandsManagement.StrategyActionMoveRight;
import com.bianisoft.androittest.application.StrategyDP_GameplayCommandsManagement.StrategyActionMoveUp;
import com.bianisoft.androittest.application.StrategyDP_GameplayCommandsManagement.StrategyDoNothing;
import com.bianisoft.androittest.domain.GameCommand;
import com.bianisoft.androittest.domain.IDomainGameCommandObserver;
import com.bianisoft.androittest.domain.DomainFacade;


public class ObserverGameCommand implements IDomainGameCommandObserver{
    HashMap<Integer, Strategy> dicStrategyGameCommandHandler;

    
    public ObserverGameCommand(){
        DomainFacade.getFacadeObject().registerObserverForGameCommandCollection(this);
        
        dicStrategyGameCommandHandler= new HashMap<>();
        
        dicStrategyGameCommandHandler.put((getKeyFromDirection( 0, 0, 0)), new StrategyDoNothing());
        dicStrategyGameCommandHandler.put((getKeyFromDirection( 1, 0, 0)), new StrategyActionMoveRight());
        dicStrategyGameCommandHandler.put((getKeyFromDirection(-1, 0, 0)), new StrategyActionMoveLeft());
        dicStrategyGameCommandHandler.put((getKeyFromDirection(0, -1, 0)), new StrategyActionMoveUp());
        dicStrategyGameCommandHandler.put((getKeyFromDirection(0,  1, 0)), new StrategyActionMoveDown());
        dicStrategyGameCommandHandler.put((getKeyFromDirection(0, 0,  1)), new StrategyActionMoveForward());
        dicStrategyGameCommandHandler.put((getKeyFromDirection(0, 0, -1)), new StrategyActionMoveReverse());
    }

    public int getKeyFromDirection(int p_movementXAxis, int p_movementYAxis, int p_movementZAxis){
        int nKeyResult = 0;

        nKeyResult |= (p_movementXAxis>0)?(1<<0):0;
        nKeyResult |= (p_movementXAxis<0)?(1<<1):0;

        nKeyResult |= (p_movementYAxis>0)?(1<<2):0;
        nKeyResult |= (p_movementYAxis<0)?(1<<3):0;

        nKeyResult |= (p_movementZAxis>0)?(1<<4):0;
        nKeyResult |= (p_movementZAxis<0)?(1<<5):0;

        return nKeyResult;
    }

    @Override
    public void notify(ArrayList<GameCommand> pArrayListCommands) {
        ApplicationFacade objApplicationFacade= ApplicationFacade.getFacadeObject();
        DomainFacade objDomainFacade= DomainFacade.getFacadeObject();
        int nIdxToStart= objApplicationFacade.GetCommandStartIdx();
        GameCommand objGameCommand= pArrayListCommands.get(nIdxToStart);
        
        Strategy objStrategyToUse= dicStrategyGameCommandHandler.get(
                                        (getKeyFromDirection(objGameCommand.nTransformationX,
                                                objGameCommand.nTransformationY,
                                                objGameCommand.nTransformationZ)));

        objStrategyToUse.execute(pArrayListCommands, nIdxToStart);
    }
}
