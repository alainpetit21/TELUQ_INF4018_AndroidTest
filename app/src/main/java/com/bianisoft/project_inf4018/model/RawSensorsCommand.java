package com.bianisoft.project_inf4018.model;


public class RawSensorsCommand {
    public int nRotationRoll;
    public int nRotationPitch;

    public int nAcceleratingZ;


    public RawSensorsCommand(int p_nRotationRoll, int p_nRotationPitch, int p_nAcceleratingZ){
        
        nRotationRoll= p_nRotationRoll;
        nRotationPitch= p_nRotationPitch;

        nAcceleratingZ= p_nAcceleratingZ;
    }

    public int getRotationalSensorByDirectionalIdx(int p_nDirectionalIdx){
        int arDirectionalIdx[]= {nRotationRoll, nRotationPitch, -nRotationRoll, -nRotationPitch};
        
        return arDirectionalIdx[p_nDirectionalIdx];
    }
}
