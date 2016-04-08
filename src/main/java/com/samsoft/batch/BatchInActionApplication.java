package com.samsoft.batch;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing
public class BatchInActionApplication {

	private static final int CHUNK_SIZE = 10;

	@Autowired
	protected JobBuilderFactory jobBuilderFactory;

	@Autowired
	protected StepBuilderFactory stepBuilderFactory;

	@Autowired
	protected JobExplorer jobExplorer;

	public static void main(String[] args) {
		SpringApplication.run(BatchInActionApplication.class, args);
	}

	/////// EMPLOYEE JOB START ////////////

	@Bean
	public JpaPagingItemReader<Employee> jpaPagedItemReader(EntityManagerFactory emf) {
		JpaPagingItemReader<Employee> reader = new JpaPagingItemReader<>();
		reader.setEntityManagerFactory(emf);
		reader.setPageSize(10);
		reader.setQueryString("select e from Employee e order by e.id");
		return reader;
	}

	@Bean
	public JpaItemWriter<Employee> employeeItemWriter(EntityManagerFactory emf) {

		JpaItemWriter<Employee> writer = new JpaItemWriter<>();
		writer.setEntityManagerFactory(emf);
		return writer;
	}

	@Bean
	public JobExecutionListener employeeJobExecutionListner() {
		return new EmployeeJobExecutionListner();
	}

	@Bean
	public Job employeeAppraisalJob(Step salaryIncrementStep, Step sendNotificationStep,
			JobExecutionListener employeeJobExecutionListner) {
		//@formatter:off
		return jobBuilderFactory
				.get("appraisalJob")
				.start(salaryIncrementStep)
				.next(sendNotificationStep)
				.listener(employeeJobExecutionListner)
				.build();
		//@formatter:on
	}

	@Bean
	public ItemProcessor<Employee, Employee> employeeSalaryProcessor() {
		return new EmployeeSalaryProcessor();
	}

	@Bean
	public ChunkListener salaryStepChunkListner() {
		return new SalaryStepChunkListener();
	}

	@Bean
	public SkipListener<Employee, Employee> salaryProcessorSkipListener() {
		return new SalaryStepSkipListener();
	}

	@Bean
	public Step salaryIncrementStep(JpaPagingItemReader<Employee> jpaPagedItemReader,
			ItemProcessor<Employee, Employee> employeeSalaryProcessor, JpaItemWriter<Employee> employeeItemWriter,
			ChunkListener salaryStepChunkListner, SkipListener<Employee, Employee> salaryStepSkipListener) {
		//@formatter:off
		return stepBuilderFactory
				.get("salaryIncrementStep")
				.<Employee,Employee> chunk(CHUNK_SIZE)
				.reader(jpaPagedItemReader)
				.processor(employeeSalaryProcessor)
					.faultTolerant()
					.skip(IllegalArgumentException.class)
					.skipLimit(0)
				.writer(employeeItemWriter)
				.listener(salaryStepSkipListener) // skip listener
				.listener(salaryStepChunkListner) // chunk listener
				.build();
		//@formatter:on
	}

	@Bean
	public ItemProcessor<Employee, Employee> salaryNotificatoinProcessor() {
		return new EmployeeNotificationProcessor();
	}

	@Bean
	public Step sendNotificationStep(JpaPagingItemReader<Employee> jpaPagedItemReader,
			ItemProcessor<Employee, Employee> salaryNotificatoinProcessor) {

		//@formatter:off
		return stepBuilderFactory
				.get("sendNotificationStep")
				.<Employee,Employee> chunk(CHUNK_SIZE)
				.reader(jpaPagedItemReader)
				.processor(salaryNotificatoinProcessor)
				.build();
		//@formatter:on
	}

	/////// EMPLOYEE JOB END ////////////
}
