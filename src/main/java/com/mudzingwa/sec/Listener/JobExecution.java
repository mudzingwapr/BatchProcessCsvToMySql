package com.mudzingwa.sec.Listener;

import java.util.Date;

import org.springframework.batch.core.JobExecutionListener;

public class JobExecution implements JobExecutionListener {

	@Override
	public void beforeJob(org.springframework.batch.core.JobExecution jobE) {
		System.out.println("start date and time of execution"+ new Date());

	}

	@Override
	public void afterJob(org.springframework.batch.core.JobExecution jobE) {
		// TODO Auto-generated method stub
		System.out.println("End date and time of execution"+ new Date());
		System.out.println("Execution status at the End"+ jobE.getStatus());


	}

}
