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

	private static short count = 50;

	private static Fairy FAIRY = Fairy.create();

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job employeeJob;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Before
	public void init() {
		List<Employee> list = new ArrayList<>(count);

		for (int i = 0; i < count; i++) {
			Employee e = new Employee();
			Person person = FAIRY.person();
			e.setName(person.fullName());
			e.setSalary(RandomUtils.nextInt(300000, 2000000));
			list.add(e);
		}
		employeeRepository.save(list);
		LOG.debug("Created {} fake employeees", count);
	}

	@Test
	public void employeeJobTest() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		JobParametersBuilder jobParamBuilder = new JobParametersBuilder();

		Date jobDateParam = DateUtils.round(new Date(), Calendar.HOUR_OF_DAY);
		JobParameters params = jobParamBuilder.addDate("theJobDate", jobDateParam, false).toJobParameters();
		LOG.debug("Starting the job with params {}", params);
		JobExecution execution = jobLauncher.run(employeeJob, params);
		LOG.debug(" Job execution : {}", execution);
	}

}
