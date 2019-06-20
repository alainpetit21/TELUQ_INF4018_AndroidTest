package com.bianisoft.androittest.controller.StrategyDP_GameplayCommandsManagement;

import java.util.ArrayList;
import com.bianisoft.androittest.model.GameCommand;


public interface Strategy {

    public void execute(ArrayList<GameCommand> pArGameCommand, int pNIdxToStart);
}
