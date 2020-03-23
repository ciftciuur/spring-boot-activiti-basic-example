package com.example.process.rest;

import com.example.process.service.BPMService;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class BPMRestController {

    @Autowired
    private BPMService bpmService;

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public ResponseEntity<String> startProcessInstance(@RequestParam String assignee) {
        if (assignee != null) {
            bpmService.startProcess(assignee);
            return new ResponseEntity<String>("Success !", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Username not null !", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public List<Task> returnActiveUserTaskList(@RequestParam String assignee) {
        if (assignee != null) {
            return bpmService.getTasks(assignee);
        } else
            return new ArrayList<Task>();
    }
}
