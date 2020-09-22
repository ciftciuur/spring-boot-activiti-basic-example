package com.example.process.service;

import com.example.process.dto.TaskDetailModelDto;
import com.example.process.repository.BPMUserRepository;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BPMTaskService {

    @Autowired
    private TaskService taskService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private BPMUserRepository bpmUserRepository;

    /*
        active all user task list
     */
    public List<TaskDetailModelDto> returnActiveAllTaskList() {
        List<TaskDetailModelDto> taskDetailModels;
        List<Task> taskList = taskService.createTaskQuery().active().list();
        if (taskList.size() != 0) {
            taskDetailModels = new ArrayList<>();
            TaskDetailModelDto detailModel = new TaskDetailModelDto();
            for (Task task : taskList) {
                detailModel.setCreateTime(task.getCreateTime());
                detailModel.setTaskId(task.getId());
                detailModel.setTaskName(task.getName());
                detailModel.setTaskOwner(task.getOwner());
                detailModel.setVariables(task.getProcessVariables());
                taskDetailModels.add(detailModel);
            }
            return taskDetailModels;
        } else {
            return taskDetailModels = new ArrayList<>();
        }
    }

    public void completeTaskWithId(String taskId) {
        taskService.complete(taskId);
    }
    public List<UserTask> getAllTaskInProcess(String processInstanceId){
       ProcessInstance instance= runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        ProcessDefinition def= repositoryService.createProcessDefinitionQuery().processDefinitionId(instance.getProcessDefinitionId()).singleResult();

        BpmnModel model = repositoryService.getBpmnModel(def.getId());

        List<Process> processes = model.getProcesses();

        List<UserTask> userTasks = new ArrayList<UserTask>();

        for( Process p : processes ) {
            userTasks.addAll(p.findFlowElementsOfType(UserTask.class));
        }
        return userTasks;
    }

    public List<TaskDetailModelDto> returnTaskListForGroupName(String groupName) {
        ProcessDefinition definition;
        ProcessInstance instance;
        List<TaskDetailModelDto> taskDetailModels;
        List<Task> taskListOwner = taskService.createTaskQuery().taskCandidateGroup(groupName).list();
        if (taskListOwner.size() != 0) {
            taskDetailModels = new ArrayList<>();
            TaskDetailModelDto detailModel = new TaskDetailModelDto();
            for (Task task : taskListOwner) {
                detailModel.setCreateTime(task.getCreateTime());
                detailModel.setTaskId(task.getId());
                detailModel.setTaskName(task.getName());
                detailModel.setTaskOwner(task.getOwner());
                detailModel.setVariables(task.getProcessVariables());
                taskDetailModels.add(detailModel);
            }
            return taskDetailModels;
        } else {
            return taskDetailModels = new ArrayList<>();
        }

    }

    /*
        active user task list
        @param active user name
     */
    public List<Task> returnActiveUserTaskList(String userName) {
        if (bpmUserRepository.findByUserName(userName) != null)
            return taskService.createTaskQuery().taskCandidateUser(userName).list();
        else return new ArrayList<Task>();
    }

    public void completeTask(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }

}
