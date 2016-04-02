/**
 * 
 */
package com.samsoft.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author Kumar Sambhav Jain
 *
 */
public class EmployeeNotificationProcessor implements ItemProcessor<Employee, Employee> {

	private static final Logger LOG = LoggerFactory.getLogger(EmployeeNotificationProcessor.class);

	@Override
	public Employee process(Employee item) throws Exception {
		LOG.debug("Sending Mail to Employee " + item.getId());
		return item;
	}
}