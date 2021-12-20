package com.mudzingwa.sec.Runner;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class MyJobRunner implements CommandLineRunner {
	@Autowired
	private JobLauncher joblauncher;
	@Autowired
	private Job job;
	@Override
	public void run(String... args) throws Exception {
		JobParameters jobparameters = new JobParametersBuilder()
				.addLong("Current time",System.currentTimeMillis())
				.toJobParameters();
		joblauncher.run(job, jobparameters);
        log.info("Job Excecution in Process");
	}

}
