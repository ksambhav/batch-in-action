package com.samsoft.batch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jfairy.Fairy;
import org.jfairy.producer.person.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BatchInActionApplication.class)
public class BatchInActionApplicationTests {

	private static Logger LOG = LoggerFactory.getLogger(BatchInActionApplicationTests.class);

	private static final short COUNT = 20;

	private static final Fairy FAIRY = Fairy.create();

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job employeeJob;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Before
	public void init() {

		employeeRepository.deleteAll();

		List<Employee> list = new ArrayList<>(COUNT);

		for (int i = 0; i < COUNT; i++) {
			Employee e = new Employee();
			Person person = FAIRY.person();
			e.setName(person.fullName());
			e.setSalary(100);
			list.add(e);
		}
		employeeRepository.save(list);
		LOG.debug("Created {} fake employeees", COUNT);
	}

	@Test
	public void employeeJobTest() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		JobParametersBuilder jobParamBuilder = new JobParametersBuilder();

		//@formatter:off
		JobParameters params = 
				jobParamBuilder
				.addDate("theJobDate", DateUtils.round(new Date(), Calendar.HOUR_OF_DAY), true)
				.addLong("randomLong", RandomUtils.nextLong(0l, 999999l), true)
				.toJobParameters();
		//@formatter:on
		LOG.debug("Starting the job with params {}", params);
		JobExecution execution = jobLauncher.run(employeeJob, params);
		LOG.debug(" Job execution : {}", execution);
	}

}
