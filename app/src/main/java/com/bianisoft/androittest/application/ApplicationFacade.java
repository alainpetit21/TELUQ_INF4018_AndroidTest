package com.bianisoft.androittest.application;


import com.bianisoft.androittest.domain.*;


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

    public void addRawCommand_Roll(int p_nValue){
        DomainFacade objDomain= DomainFacade.getFacadeObject();

        objDomain.addRawSensorCommand(p_nValue, 0, 0);
    }

    public void addRawCommand_Pitch(int p_nValue){
        DomainFacade objDomain= DomainFacade.getFacadeObject();

        objDomain.addRawSensorCommand(0, p_nValue, 0);
    }


    public void addRawCommand_AccelerationZ(int p_nValue){
        DomainFacade objDomain= DomainFacade.getFacadeObject();

        objDomain.addRawSensorCommand(0, 0, p_nValue);
    }
}
