package com.example.process.rest;

import com.example.process.dto.UserDto;
import com.example.process.service.BPMUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BPMUserRestController {

    @Autowired
    private BPMUserService bpmUserService;


    @RequestMapping(value = "/api/create/user", method = RequestMethod.POST)
    public ResponseEntity<String> createNewUserForActiviti(@RequestBody UserDto userDto) {
        if (userDto != null) {
            bpmUserService.createUser(userDto);
            return new ResponseEntity<String>("Success !", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("User not null !", HttpStatus.BAD_REQUEST);
        }
    }
}
