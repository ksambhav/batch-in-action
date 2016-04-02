package com.samsoft.batch;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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


	@Autowired
	protected JobBuilderFactory jobBuilderFactory;

	@Autowired
	protected StepBuilderFactory stepBuilderFactory;

	public static void main(String[] args) {
		SpringApplication.run(BatchInActionApplication.class, args);
	}

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
	public Job employeeAppraisalJob(Step salaryIncrementStep, Step sendNotificationStep) {
		//@formatter:off
		return jobBuilderFactory
				.get("appraisalJob")
				.start(salaryIncrementStep)
				.next(sendNotificationStep)
				.build();
		//@formatter:on
	}

	@Bean
	public ItemProcessor<Employee, Employee> empployeeSalaryProcessor() {
		return new EmpployeeSalaryProcessor();
	}

	@Bean
	public Step salaryIncrementStep(JpaPagingItemReader<Employee> jpaPagedItemReader,
			ItemProcessor<Employee, Employee> empployeeSalaryProcessor, JpaItemWriter<Employee> employeeItemWriter) {
		//@formatter:off
		return stepBuilderFactory
				.get("salaryIncrementStep")
				.<Employee,Employee> chunk(5)
				.reader(jpaPagedItemReader)
				.processor(empployeeSalaryProcessor)
				.writer(employeeItemWriter)
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
				.<Employee,Employee> chunk(10)
				.reader(jpaPagedItemReader)
				.processor(salaryNotificatoinProcessor)
				//.taskExecutor(new SimpleAsyncTaskExecutor("sendNotificationStepExecutor"))
				.build();
		//@formatter:on
	}
}
