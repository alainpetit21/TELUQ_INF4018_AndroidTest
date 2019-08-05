package com.bianisoft.project_inf4018.model;


import java.util.ArrayList;


public interface IDomainGameCommandObserver {
    void notify(ArrayList<GameCommand> pObj);
}
