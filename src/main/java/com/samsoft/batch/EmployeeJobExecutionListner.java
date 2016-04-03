/**
 * 
 */
package com.samsoft.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @author Kumar Sambhav Jain
 *
 */
public class EmployeeJobExecutionListner implements JobExecutionListener {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeJobExecutionListner.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.JobExecutionListener#beforeJob(org.
	 * springframework.batch.core.JobExecution)
	 */
	@Override
	public void beforeJob(JobExecution jobExecution) {
		LOG.debug("Before JobExecution " + jobExecution);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.JobExecutionListener#afterJob(org.
	 * springframework.batch.core.JobExecution)
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		LOG.debug("After JobExecution " + jobExecution);

	}

}
