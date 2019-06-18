package com.bianisoft.androittest.application.StrategyDP_GameplayCommandsManagement;

import java.util.ArrayList;
import com.bianisoft.androittest.domain.GameCommand;


public interface Strategy {

    public void execute(ArrayList<GameCommand> pArGameCommand, int pNIdxToStart);
}
