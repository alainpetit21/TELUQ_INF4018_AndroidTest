package com.bianisoft.androittest.application;

import java.util.ArrayList;
import java.util.HashMap;
import com.bianisoft.androittest.domain.GameCommand;
import com.bianisoft.androittest.domain.IDomainGameCommandObserver;
import com.bianisoft.androittest.application.helper.DirectionToKeyConverter;
import com.bianisoft.androittest.domain.DomainFacade;


public class ObserverGameCommand implements IDomainGameCommandObserver{
    HashMap<Integer, Strategy> dicStrategyGameCommandHandler; 

    
    public ObserverGameCommand(){
        DomainFacade.getFacadeObject().registerObserverForGameCommandCollection(this);
        
        dicStrategyGameCommandHandler= new HashMap<>();
        
        dicStrategyGameCommandHandler.put((new DirectionToKeyConverter( 0, 0, 0)).getResult(), new StrategyDoNothing());
        dicStrategyGameCommandHandler.put((new DirectionToKeyConverter( 1, 0, 0)).getResult(), new StrategyActionMoveRight());
        dicStrategyGameCommandHandler.put((new DirectionToKeyConverter(-1, 0, 0)).getResult(), new StrategyActionMoveLeft());
        dicStrategyGameCommandHandler.put((new DirectionToKeyConverter(0, -1, 0)).getResult(), new StrategyActionMoveUp());
        dicStrategyGameCommandHandler.put((new DirectionToKeyConverter(0,  1, 0)).getResult(), new StrategyActionMoveDown());
        dicStrategyGameCommandHandler.put((new DirectionToKeyConverter(0, 0,  1)).getResult(), new StrategyActionMoveForward());
        dicStrategyGameCommandHandler.put((new DirectionToKeyConverter(0, 0, -1)).getResult(), new StrategyActionMoveReverse());
    }

    @Override
    public void notify(ArrayList<GameCommand> pArrayListCommands) {
        ApplicationFacade objApplicationFacade= ApplicationFacade.getFacadeObject();
        DomainFacade objDomainFacade= DomainFacade.getFacadeObject();
        int nIdxToStart= objApplicationFacade.GetCommandStartIdx();
        GameCommand objGameCommand= pArrayListCommands.get(nIdxToStart);
        
        Strategy objStrategyToUse= dicStrategyGameCommandHandler.get(
                                        (new DirectionToKeyConverter(objGameCommand.nTransformationX, 
                                                                     objGameCommand.nTransformationY, 
                                                                     objGameCommand.nTransformationZ)).getResult());

        objStrategyToUse.execute(pArrayListCommands, nIdxToStart);
    }
}
