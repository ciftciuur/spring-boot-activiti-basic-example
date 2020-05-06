package com.example.process.service;

import com.example.process.model.BPMUser;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        repositoryService.createDeployment().addClasspathResource("processes/timer-event-example-bpmn20.xml").deploy();

        HashMap<String, Object> variables = new HashMap<>();
        variables.put("duration", "short");
        runtimeService.startProcessInstanceByKey(processKey, variables);
    }
}
