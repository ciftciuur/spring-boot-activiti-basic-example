package com.example.process.subprocess;

import com.example.process.repository.BPMUserRepository;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class SubProcessApiService {


    @Autowired
    private RuntimeService runtimeService;


    @Autowired
    private RepositoryService repositoryService;


    public void startCreditSubProcessApi(String name, int requestAmount) {
        repositoryService.createDeployment().addClasspathResource("processes/subprocess/demand-credit-subprocess-bpmn20.xml").deploy();
        Map<String, Object> variables = new HashMap<>();
        variables.put("requestUser", name);
        variables.put("demandAmount", requestAmount);
        runtimeService.startProcessInstanceByKey("demand-credit-subprocess", variables);

    }

}
