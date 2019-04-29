package com.bianisoft.androittest.application.helper;


public class DirectionToKeyConverter {
    private int nKeyResult= 0;
    
    //Generate a unique integer key for specific movement. Basically do a bunch 
    //of bitwise operation and the result is as such
    //    111111
    //    ^^^^^^
    //    ||||||
    //    |||||\_ Any Positive Movement in X Axis 
    //    ||||\__ Any Negative Movement in X Axis 
    //    |||\___ Any Positive Movement in Y Axis 
    //    ||\____ Any Negative Movement in Y Axis 
    //    |\_____ Any Positive Movement in Z Axis 
    //    \______ Any Negative Movement in Z Axis 
    public DirectionToKeyConverter(int p_movementXAxis, int p_movementYAxis, int p_movementZAxis){
        nKeyResult |= (p_movementXAxis>0)?(1<<0):0;
        nKeyResult |= (p_movementXAxis<0)?(1<<1):0;
        
        nKeyResult |= (p_movementYAxis>0)?(1<<2):0;
        nKeyResult |= (p_movementYAxis<0)?(1<<3):0;
        
        nKeyResult |= (p_movementZAxis>0)?(1<<4):0;
        nKeyResult |= (p_movementZAxis<0)?(1<<5):0;
    }
    
    public int getResult(){
        return nKeyResult;
    }
}
