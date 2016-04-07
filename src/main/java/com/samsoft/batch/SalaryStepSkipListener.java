/**
 * 
 */
package com.samsoft.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

/**
 * @author sambhav.jain
 *
 */
public class SalaryStepSkipListener implements SkipListener<Employee, Employee> {

	private static Logger LOG = LoggerFactory.getLogger(SalaryStepSkipListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.SkipListener#onSkipInRead(java.lang.
	 * Throwable)
	 */
	@Override
	public void onSkipInRead(Throwable t) {
		LOG.warn("Item Skipped during read.", t);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.SkipListener#onSkipInWrite(java.lang.
	 * Object, java.lang.Throwable)
	 */
	@Override
	public void onSkipInWrite(Employee item, Throwable t) {
		LOG.warn("Item Skipped during write.", t);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.core.SkipListener#onSkipInProcess(java.lang.
	 * Object, java.lang.Throwable)
	 */
	@Override
	public void onSkipInProcess(Employee item, Throwable t) {
		LOG.warn("Item Skipped during process.", t);
		LOG.error("Employee {},{} skipped for processing.", item.getName(), item.getId());

	}

}
