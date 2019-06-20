package com.bianisoft.androittest.model;

import java.util.ArrayList;


public interface IDomainRawSensorObserver {
    public void notify(ArrayList<RawSensorsCommand> pObj);
}
