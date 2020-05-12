package com.example.process.service;

import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class BPMTimerService {

    @Autowired
    private ManagementService managementService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;


    public void waitForTimerToBeExecuted(String processInstanceId) {
        List<Job> timers = managementService.createJobQuery().timers().list();
    }

    public void startProcess(String processKey) {

        repositoryService.createDeployment().addClasspathResource("processes/timer/basic-timer-example-bpmn20.xml").deploy();

        runtimeService.startProcessInstanceByKey(processKey);
    }
}
