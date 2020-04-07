package com.example.process.service;

import com.example.process.dto.TaskDetailModel;
import com.example.process.repository.BPMUserRepository;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
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
    private BPMUserRepository bpmUserRepository;

    /*
        active all user task list
     */
    public List<TaskDetailModel> returnActiveAllTaskList() {
        List<TaskDetailModel> taskDetailModels;
        List<Task> taskList = taskService.createTaskQuery().active().list();
        if (taskList.size() != 0) {
            taskDetailModels = new ArrayList<>();
            TaskDetailModel detailModel = new TaskDetailModel();
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


    public List<TaskDetailModel> returnTaskListForGroupName(String groupName) {
        List<TaskDetailModel> taskDetailModels;
        List<Task> taskListOwner = taskService.createTaskQuery().taskCandidateGroup(groupName).list();
        if (taskListOwner.size() != 0) {
            taskDetailModels = new ArrayList<>();
            TaskDetailModel detailModel = new TaskDetailModel();
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
