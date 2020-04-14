package com.example.process.service;

import com.example.process.bpmn.deployment.DeploymentBpmnFile;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
public class BPMProcessService {
    @Autowired
    SpringProcessEngineConfiguration springProcessEngineConfiguration;

    public void deployProcess() throws IOException {
        springProcessEngineConfiguration.setDeploymentResources(DeploymentBpmnFile.getBpmnFiles());
    }
}
