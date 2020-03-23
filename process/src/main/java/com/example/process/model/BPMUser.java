package com.example.process.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class BPMUser {
    @Id
    @GeneratedValue
    private Long bpmUserId;

    private String userName;

    private String firstName;

    private String lastName;

    private Date birthDate;

    public BPMUser() {
    }

    public BPMUser(String username, String firstName, String lastName, Date birthDate) {
        this.userName = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }


    public Long getBpmUserId() {
        return bpmUserId;
    }

    public void setBpmUserId(Long bpmUserId) {
        this.bpmUserId = bpmUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
