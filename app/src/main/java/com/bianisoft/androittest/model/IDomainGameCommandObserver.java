package com.bianisoft.androittest.model;


import java.util.ArrayList;


public interface IDomainGameCommandObserver {
    public void notify(ArrayList<GameCommand> pObj);
}
