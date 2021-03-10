package com.example.process.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.process.service.BPMService;
import com.example.process.subprocess.SubProcessApiService;

@RestController
public class BPMRestController {

	private final BPMService bpmService;
	private final SubProcessApiService subProcessApiService;

	public BPMRestController(BPMService bpmService, SubProcessApiService subProcessApiService) {
		super();
		this.bpmService = bpmService;
		this.subProcessApiService = subProcessApiService;
	}

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
	public void bpmTest(@RequestParam String exId, @RequestParam String prId, HttpServletResponse response)
			throws IOException {
	}

	@RequestMapping(value = "/api/simple", method = RequestMethod.POST)
	public void startSimpleProcess() {
		bpmService.startCreditApi("Ahmet", 1000);
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
