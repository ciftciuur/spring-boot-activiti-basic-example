package com.example.process;

import com.example.process.repository.BPMUserRepository;
import com.example.process.service.BPMService;
import com.example.process.service.BPMUserService;
import org.activiti.engine.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class ProcessApplication {
    @Autowired
    BPMUserService bpmUserService;


    public static void main(String[] args) {
        SpringApplication.run(ProcessApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(final BPMService bpmService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                bpmUserService.createBpmUser();
            }
        };
    }

    /*
        first project created processEngineConfiguration function changed -
        config.setDatabaseSchemaUpdate("true"); ->
        created db schema and default changed
         config.setDatabaseSchemaUpdate("none");
     */
    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration() {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
        config.setDataSource(databaseConnection());
        config.setTransactionManager(transactionManager());
        config.setDatabaseSchemaUpdate("none");
        config.setHistory("audit");
        config.setAsyncExecutorActivate(true);
        return config;
    }


    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(databaseConnection());
    }

    @Bean
    public ProcessEngineFactoryBean processEngine() {
        ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
        factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
        return factoryBean;
    }

    @Bean
    public DataSource databaseConnection() {
        return DataSourceBuilder.create().url("jdbc:postgresql://mypostgres:5432/process-db").username("postgres")
                .password("postgres").driverClassName("org.postgresql.Driver").build();
    }

    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }
}
