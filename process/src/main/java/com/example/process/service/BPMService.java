package com.example.process.service;

import com.example.process.model.BPMUser;
import com.example.process.repository.BPMUserRepository;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BPMService {

    @Autowired
    private BPMUserRepository bpmUserRepository;

    @Autowired
    private RuntimeService runtimeService;

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

    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    public void createBpmUser() {
        if (bpmUserRepository.findAll().size() == 0) {
            bpmUserRepository.save(new BPMUser("demo", "demoname", "demolastname", new Date()));
        }
    }


}
