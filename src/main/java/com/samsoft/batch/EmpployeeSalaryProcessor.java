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
	public Employee process(Employee employee) throws Exception {

		/*if (employee.getId() % 31 == 0) {
			throw new RuntimeException("Throwing exception at employee id " + employee.getId());
		}*/

		float percent = RandomUtils.nextFloat(1.01f, 1.99f);
		employee.setSalary((int) (employee.getSalary() * percent));
		log.debug("Incremented Salary of employee " + employee.getId());
		employee.setIncrement(employee.getIncrement() + percent);
		return employee;
	}

}
