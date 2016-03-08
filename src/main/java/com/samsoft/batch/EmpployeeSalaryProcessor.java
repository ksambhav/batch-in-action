/**
 * 
 */
package com.samsoft.batch;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author sambhav.jain
 *
 */
public class EmpployeeSalaryProcessor implements ItemProcessor<Employee, Employee> {

	private static final Logger log = LoggerFactory.getLogger(EmpployeeSalaryProcessor.class);

	@Override
	public Employee process(Employee item) throws Exception {
		float percent = RandomUtils.nextFloat(1.01f, 1.99f);
		log.debug("Processing Employee {} with {} percent hike", item.getId(), percent);
		item.setSalary((int) (item.getSalary() * percent));
		System.out.println("Incremented Salary of employee " + item.getId());
		return item;
	}

}
