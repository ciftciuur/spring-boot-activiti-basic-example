package com.example.process.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;

public class DemandCreditListener implements ActivitiEventListener {
    @Override
    public void onEvent(ActivitiEvent event) {

    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
