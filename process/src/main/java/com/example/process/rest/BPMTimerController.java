package com.example.process.rest;

import com.example.process.service.BPMTimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class BPMTimerController {
    @Autowired
    private BPMTimerService bpmTimerService;

    @RequestMapping(value = "/api/timer", method = RequestMethod.POST)
    public void startProcessInstance(@RequestParam String processId) {

        bpmTimerService.startProcess(processId);
    }
}
