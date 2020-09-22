package com.example.process.rest;

import com.example.process.dto.TaskDetailModelDto;
import com.example.process.dto.UserDto;
import com.example.process.service.BPMDiagramService;
import com.example.process.service.BPMService;

import com.example.process.service.BPMTaskService;
import com.example.process.service.BPMUserService;
import com.example.process.subprocess.SubProcessApiService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BPMRestController {

    @Autowired
    private BPMService bpmService;
    @Autowired
    private BPMDiagramService bpmDiagramService;
    @Autowired
    private SubProcessApiService subProcessApiService;

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public ResponseEntity<String> startProcessInstance(@RequestParam String assignee) {
        if (assignee != null) {
            bpmService.startProcess(assignee);
            return new ResponseEntity<String>("Success !", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Username not null !", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/api/credit", method = RequestMethod.POST)
    public void startProcessInstance(@RequestParam String name, Integer requestAmount) {
        subProcessApiService.startCreditSubProcessApi(name, requestAmount);
    }

    @RequestMapping(value = "/diagram", method = RequestMethod.POST)
    public void bpmTest(@RequestParam String exId, @RequestParam String prId, HttpServletResponse response) throws IOException {
        byte[] b = new byte[1024];
        int len;
       /* while ((len = bpmDiagramService.getDiagram(exId, prId).read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }*/
    }

    @RequestMapping(value = "/api/simple", method = RequestMethod.POST)
    public void startSimpleProcess() {
        bpmService.startCreditApi("Ahmet",1000);
    }

    @RequestMapping(value = "/api/simple/timer", method = RequestMethod.POST)
    public HttpStatus startTimerProcess() {
        bpmService.startTimerBasicApi();
        System.out.println("process started");
        return HttpStatus.OK;
    }


    @RequestMapping(value = "/api/suspend", method = RequestMethod.POST)
    public void suspendProcess(@RequestParam String suspendId) {
        bpmService.suspendProcess(suspendId);
    }

    @RequestMapping(value = "/api/activate", method = RequestMethod.POST)
    public void activateProcess(@RequestParam String suspendId) {
        bpmService.activateProcess(suspendId);
    }

}



