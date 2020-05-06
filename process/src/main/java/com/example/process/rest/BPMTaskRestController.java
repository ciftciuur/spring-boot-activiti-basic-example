package com.example.process.rest;

import com.example.process.dto.TaskDetailModelDto;
import com.example.process.service.BPMTaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BPMTaskRestController {
    @Autowired
    private BPMTaskService bpmTaskService;

    @RequestMapping(value = "/api/tasks", method = RequestMethod.GET)
    public @ResponseBody
    List<TaskDetailModelDto> returnActiveAllTask(@RequestParam String groupName) {
        if (groupName != null) {
            return bpmTaskService.returnTaskListForGroupName(groupName);
        } else {
            return bpmTaskService.returnActiveAllTaskList();
        }
    }

    @RequestMapping(value = "/user/tasks", method = RequestMethod.GET)
    public List<Task> returnActiveUserTaskList(@RequestParam String assignee) {
        if (assignee != null) {
            return bpmTaskService.returnActiveUserTaskList(assignee);
        } else
            return new ArrayList<Task>();
    }

    @RequestMapping(value = "/api/task/complete", method = RequestMethod.POST)
    public void completeTaskWithIdAndVariable(String taskId, int requestAmount) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("demandAmount", requestAmount);
        bpmTaskService.completeTask(taskId, variables);
    }

    @RequestMapping(value = "/api/task", method = RequestMethod.POST)
    public void completeTaskWithId(@RequestParam String taskId) {
        bpmTaskService.completeTaskWithId(taskId);
    }

}
