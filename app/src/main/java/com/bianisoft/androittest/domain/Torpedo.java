package com.bianisoft.androittest.domain;


public class Torpedo extends Entity{
    public int nPower; 
    
    public Torpedo(){
        super(0, 0, 0, 1, 1, 1);
        
        nPower= 10;
    }
}
