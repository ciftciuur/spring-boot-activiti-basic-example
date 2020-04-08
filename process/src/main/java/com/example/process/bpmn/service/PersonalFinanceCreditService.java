package com.example.process.bpmn.service;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalFinanceCreditService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Personal finance credit service run");
        //get active execution(process) variable


        Object requestAmount = execution.getVariable("demandAmount");
        Integer resultAmount = (Integer) requestAmount;
        //interest calculation(krediye göre faiz hesaplama)
        double calcInterest = resultAmount * 0.25;
        double resultCalcInterest = calcInterest + resultAmount.intValue();

        execution.setVariable("demandAmount", resultCalcInterest);
    }
}
