/**
 * 
 */
package com.samsoft.batch;

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

		if (employee.getId() % 19 == 0) {
			throw new IllegalArgumentException("Throwing exception at employee id " + employee.getId());
		}

		employee.setSalary((int) (employee.getSalary() * 1.1));
		log.debug("Incremented Salary of employee " + employee.getId());
		employee.setIncrement(10f);
		return employee;
	}

}
