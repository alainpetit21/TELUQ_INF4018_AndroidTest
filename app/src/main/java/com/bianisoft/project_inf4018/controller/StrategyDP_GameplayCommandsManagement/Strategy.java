package com.bianisoft.project_inf4018.controller.StrategyDP_GameplayCommandsManagement;

import java.util.ArrayList;
import com.bianisoft.project_inf4018.model.GameCommand;


public interface Strategy {

    public void execute(ArrayList<GameCommand> pArGameCommand, int pNIdxToStart);
}
