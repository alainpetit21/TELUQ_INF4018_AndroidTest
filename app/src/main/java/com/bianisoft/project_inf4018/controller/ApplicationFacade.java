package com.bianisoft.project_inf4018.controller;


import com.bianisoft.project_inf4018.model.*;


public class ApplicationFacade {
    private static ApplicationFacade mSingletonInstance= null;
    private static final int MAX_XY = 10;

    private int nIndexToAnalysisInRawCommands = 0;
    private int nIndexToAnalysisInGameCommands = 0;
    
    private ObserverRawSensorCommand objObserverRawSensorCommand;
    private ObserverGameCommand objObserverGameCommand;
    private ModMgrWorldPhysic modWorldPhysicManager;
    private ModMgrRecorderPoster modRecordingPostingManager;
    
    public boolean isRunning= false;
    

    public static ApplicationFacade getFacadeObject(){
        if(mSingletonInstance != null)
            return mSingletonInstance;

        return mSingletonInstance= new ApplicationFacade();
    }

    public static void forceSingletonDestruction() {
        mSingletonInstance= null;
    }

    public ApplicationFacade(){
        objObserverRawSensorCommand = new ObserverRawSensorCommand();
        objObserverGameCommand = new ObserverGameCommand();
        modWorldPhysicManager = new ModMgrWorldPhysic();
        modRecordingPostingManager = new ModMgrRecorderPoster();
    }
    
    public int GetRawStartIdx(){
        return nIndexToAnalysisInRawCommands;
    }
    
    public int GetCommandStartIdx(){
        return nIndexToAnalysisInGameCommands;
    }

    public ModMgrWorldPhysic getMgrWorldPhysic(){
        return modWorldPhysicManager;
    }

    public ModMgrRecorderPoster getMgrRecorderPoster(){
        return modRecordingPostingManager;
    }

    public void IncrementRawSensorCommandIdxToStart(){
        ++nIndexToAnalysisInRawCommands;
    }
    
    public void IncrementGameCommandIdxToStart(){
        ++nIndexToAnalysisInGameCommands;
    }

    public void addRawCommand(int p_nRoll, int p_nPitch, int p_nAccelZ){
        DomainFacade objDomain= DomainFacade.getFacadeObject();

        objDomain.addRawSensorCommand(p_nRoll, p_nPitch, p_nAccelZ);
    }

    public void addRawGyroCommand(int p_nAccelRoll, int p_nAccelPitch){
        DomainFacade objDomain= DomainFacade.getFacadeObject();

        objDomain.addRawSensorInformative(p_nAccelRoll, p_nAccelPitch);
    }
}
