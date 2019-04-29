package com.bianisoft.androittest.domain;

import java.util.ArrayList;


public interface IDomainRawSensorObserver {
    public void notify(ArrayList<RawSensorsCommand> pObj);
}
