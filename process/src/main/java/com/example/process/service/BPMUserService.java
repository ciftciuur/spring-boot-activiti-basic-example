package com.example.process.service;

import com.example.process.dto.UserDto;
import com.example.process.model.BPMUser;
import com.example.process.repository.BPMUserRepository;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BPMUserService {

    @Autowired
    IdentityService identityService;

    @Autowired
    private BPMUserRepository bpmUserRepository;

    public void createBpmUser() {
        if (bpmUserRepository.findAll().size() == 0) {
            bpmUserRepository.save(new BPMUser("demo", "demoname", "demolastname", new Date()));
        }
    }

    public void createUser(UserDto userDto) {
        if (userDto != null) {
            //create new user
            User saveUser = identityService.newUser(userDto.getUserName());

            //set user property
            saveUser.setId(userDto.getUserName());
            saveUser.setPassword(userDto.getPassword());
            saveUser.setLastName(userDto.getSurName());
            saveUser.setFirstName(userDto.getName());
            saveUser.setEmail(userDto.getMail());
            identityService.saveUser(saveUser);

            //create group for userid
            createGroup(saveUser.getId());

            //create membership table add user and group
            createMemberShip(saveUser.getId(), saveUser.getId());
        }
    }

    private void createGroup(String groupId) {
        //userId default groupId
        Group saveGroup = identityService.newGroup(groupId);
        //groupId set group name
        saveGroup.setName(groupId);

        identityService.saveGroup(saveGroup);

    }


    private void createMemberShip(String userId, String groupId) {
        identityService.createMembership(userId, groupId);
    }
}
