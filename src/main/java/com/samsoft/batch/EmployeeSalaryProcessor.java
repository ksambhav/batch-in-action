/**
 * 
 */
package com.samsoft.batch;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * 
 * Step 1 processor.
 * 
 * @author sambhav.jain
 *
 */
public class EmployeeSalaryProcessor implements ItemProcessor<Employee, Employee> {

	private static final Logger log = LoggerFactory.getLogger(EmployeeSalaryProcessor.class);

	@Override
	public Employee process(Employee employee) throws Exception {

		if (employee.getId() % 31 == 0) {
			throw new IllegalArgumentException("Throwing exception at employee id " + employee.getId());
		}

		float percent = RandomUtils.nextFloat(1.01f, 1.99f);
		employee.setSalary((int) (employee.getSalary() * percent));
		log.debug("Incremented Salary of employee " + employee.getId());
		employee.setIncrement(employee.getIncrement() + percent);
		return employee;
	}

}
