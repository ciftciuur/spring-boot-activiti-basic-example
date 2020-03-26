package com.example.process.calculation;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalFinanceCreditService implements JavaDelegate {
    @Autowired
    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Personal finance credit service run");
        //get active execution(process) variable

        Object requestAmount = runtimeService.getVariable(execution.getId(), "demandAmount");
        Integer resultAmount = (Integer) requestAmount;
        //interest calculation(krediye göre faiz hesaplama)
        double calcInterest = resultAmount * 0.25;

        runtimeService.setVariable(execution.getId(), "demandAmount", requestAmount);
    }
}
