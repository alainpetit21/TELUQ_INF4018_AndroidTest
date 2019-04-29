package com.bianisoft.androittest.domain;


import java.util.ArrayList;


public interface IDomainGameCommandObserver {
    public void notify(ArrayList<GameCommand> pObj);
}
