package com.bianisoft.androittest.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;



public class DomainFacade {
    private static DomainFacade mSingletonInstance= null;
    
    private ArrayList<GameCommand> arGameCommands= new ArrayList<>();
    private ArrayList<RawSensorsCommand> arRawCommands= new ArrayList<>();
    private ArrayList<IDomainGameCommandObserver> arGameCommandObservers= new ArrayList<>();
    private ArrayList<IDomainRawSensorObserver> arRawSensorsCommandsObservers= new ArrayList<>();

    public World objGameWorld = new World();

    
    public static DomainFacade getFacadeObject(){
            if(mSingletonInstance != null)
                    return mSingletonInstance;

            return mSingletonInstance= new DomainFacade();
    }

    public void addRawSensorCommand(int p_nRotationRoll, int p_nRotationPitch, int p_nAcceleratingZ){
        if((p_nRotationRoll == 0) && (p_nRotationPitch == 0) && (p_nAcceleratingZ == 0))
            return;
        
        arRawCommands.add(new RawSensorsCommand(p_nRotationRoll, p_nRotationPitch, p_nAcceleratingZ));
        
        for(IDomainRawSensorObserver objRawSensorObserver : arRawSensorsCommandsObservers){
            objRawSensorObserver.notify(arRawCommands);
        }
    }
    
    public void addGameCommand(int p_nTranslationX, int p_nTranslationY, int p_nTranslationZ){
        arGameCommands.add(new GameCommand(p_nTranslationX, p_nTranslationY, p_nTranslationZ));

        for(IDomainGameCommandObserver objGameCommandObservers : arGameCommandObservers){
            objGameCommandObservers.notify(arGameCommands);
        }
    }

    public void clearRaw(){
        arRawCommands.clear();
    }
    public void clearGame(){
        arGameCommands.clear();
    }
    
    public void registerObserverForRawSensorCollection(IDomainRawSensorObserver pObjObserver){
        arRawSensorsCommandsObservers.add(pObjObserver);
    }

    public ArrayList<RawSensorsCommand> getRawSensorCommands(){
        return arRawCommands;
    }

    public ArrayList<GameCommand> getGameCommands(){
        return arGameCommands;
    }
    
    public List<RawSensorsCommand> getRawSensorCommandsFromTo(int pnFrom, int pnTo){
        if((pnFrom == -1) || (pnTo == -1)){
             return new ArrayList<>();
        }
        
        return arRawCommands.subList(pnFrom, pnTo);
    }

    public List<GameCommand> getGameCommandsFromTo(int pnFrom, int pnTo){
        if((pnFrom == -1) || (pnTo == -1)){
            return new ArrayList<>();
        }
        return arGameCommands.subList(pnFrom, pnTo);
    }


    public void registerObserverForGameCommandCollection(IDomainGameCommandObserver pObjObserver){
        arGameCommandObservers.add(pObjObserver);
    }
    
    public World getWorld(){
        return objGameWorld;
    }
}
