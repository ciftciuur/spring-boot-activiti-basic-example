package com.example.process.service;

import com.example.process.repository.BPMUserRepository;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
@Service
@Transactional
public class BPMDiagramService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    public InputStream getDiagram(String exid, String prid) {
        List<String> str = runtimeService.getActiveActivityIds(exid);
        BpmnModel model = repositoryService.getBpmnModel(prid);
        ProcessDiagramGenerator generator = processEngineConfiguration.getProcessDiagramGenerator();
        InputStream imageStream = generator.generateDiagram(model, "png", str);
        return imageStream;
    }

    public InputStream getProcessDiagram(String processInstanceId, String key) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).processDefinitionKey(key).singleResult();

        // null check
        if (processInstance != null) {
            // get process model
            BpmnModel model = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());

            if (model != null && model.getLocationMap().size() > 0) {
                ProcessDiagramGenerator generator = processEngineConfiguration.getProcessDiagramGenerator();
                return generator.generateDiagram(model, "png",
                        runtimeService.getActiveActivityIds(processInstanceId));
            }
        }
        return null;
    }
}
