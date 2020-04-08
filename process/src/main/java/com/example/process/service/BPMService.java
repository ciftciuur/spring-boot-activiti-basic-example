package com.example.process.service;

import com.example.process.model.BPMUser;
import com.example.process.repository.BPMUserRepository;
import org.activiti.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class BPMService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private BPMUserRepository bpmUserRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    public void startProcess(String assignee) {
        repositoryService.createDeployment().addClasspathResource("processes/basic-task-process-bpmn20.xml").deploy();
        BPMUser bpmUser = bpmUserRepository.findByUserName(assignee);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("bpmUser", bpmUser);
        runtimeService.startProcessInstanceByKey("basic-task-example", variables);
    }


    public void startCreditApi(String name, int requestAmount) {
        repositoryService.createDeployment().addClasspathResource("processes/demand-credit-process-bpmn20.xml").deploy();
        Map<String, Object> variables = new HashMap<>();
        variables.put("requestUser", name);
        variables.put("demandAmount", requestAmount);
        runtimeService.startProcessInstanceByKey("demand-credit-process", variables);
    }

    public void startNewProcess() {
        repositoryService.createDeployment().addClasspathResource("processes/test-diagram.bpmn20.xml").deploy();
        runtimeService.startProcessInstanceByKey("sid-C3803939-0872-457F-8336-EAE484DC4A04");
    }


}
