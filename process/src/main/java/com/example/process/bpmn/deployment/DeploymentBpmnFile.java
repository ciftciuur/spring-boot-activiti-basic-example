package com.example.process.bpmn.deployment;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

public class DeploymentBpmnFile {

    public static String BPMN_PATH = "processes/";


    public static Resource[] getBpmnFiles() throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        return resourcePatternResolver.getResources("classpath*:" + BPMN_PATH + "**/*.bpmn");
    }
}
