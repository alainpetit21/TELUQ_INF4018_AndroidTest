package com.bianisoft.project_inf4018.model;

import java.util.ArrayList;


public interface IDomainRawSensorObserver {
    void notify(ArrayList<RawSensorsCommand> pObj);
}
