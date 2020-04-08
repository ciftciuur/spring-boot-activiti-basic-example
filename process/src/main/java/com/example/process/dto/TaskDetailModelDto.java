package com.example.process.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Map;


public class TaskDetailModelDto {
    @JsonProperty(value = "taskId")
    private String taskId;

    @JsonProperty(value = "taskName")
    private String taskName;

    @JsonProperty(value = "taskOwner")
    private String taskOwner;

    @JsonProperty(value = "createTime")
    private Date createTime;

    @JsonProperty(value = "variables")
    private Map<String, Object> variables;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(String taskOwner) {
        this.taskOwner = taskOwner;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
