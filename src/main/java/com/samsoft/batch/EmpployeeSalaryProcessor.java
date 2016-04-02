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
		item.setSalary((int) (item.getSalary() * percent));
		log.debug("Incremented Salary of employee " + item.getId());
		item.setIncrement((percent - 1) * 100);
		return item;
	}

}
