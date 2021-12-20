package com.mudzingwa.sec;

import java.util.Date;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.mudzingwa.sec.Entity.Student;
import com.mudzingwa.sec.Processor.StudentProcessor;


@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Bean
	public FlatFileItemReader<Student> reader(){
		FlatFileItemReader<Student> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("student.csv"));
		
		reader.setLineMapper(new DefaultLineMapper() {{
			
		         setLineTokenizer(new DelimitedLineTokenizer() {{
				 setDelimiter(DELIMITER_COMMA);	
		         setNames("studentId","fname","costprice");
		           }});	
		setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
			setTargetType(Student.class);
			
		        }});
		
		}});
		return reader;		
	}
	@Autowired
	private DataSource datasource;
	@Bean
	public JdbcBatchItemWriter<Student> writer(){
		JdbcBatchItemWriter<Student> writer = new JdbcBatchItemWriter<>();
		writer.setDataSource(datasource);
		writer.setSql("INSERT INTO STUDENT(studentId,fname,costprice,discount,amountpaid)VALUES (:studentId,:fname,:costprice,:discount,:amountpaid)");
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
		return writer;
	}
	@Bean
	public ItemProcessor <Student,Student> stude(){
		return new StudentProcessor();
	}
	@Bean
	public JobExecutionListener joblistener() {
		//return new JobExecution();
		
		return new JobExecutionListener() {
			
			@Override
			public void beforeJob(JobExecution jobExecution) {
				System.out.println("start date and time of execution"+ new Date());
				
			}
			
			@Override
			public void afterJob(JobExecution jobExecution) {
				System.out.println("End date and time of execution"+ new Date());
				System.out.println("Execution status at the End"+ jobExecution.getStatus());
				
			}
			
		};
		
		
	}
	@Autowired
	private StepBuilderFactory stepbuilder;
	@Bean
	public Step step() {
		
		return stepbuilder.get("step")//step name
				.<Student,Student>chunk(4)//<I,O>chunk
				.reader(reader())//Reader Obj
				.processor(stude())//processor obj
				.writer(writer())// write object
				.build();
	}
	
	@Autowired
	private JobBuilderFactory jobbuilder;
	@Bean
	public Job job() {
		return jobbuilder.get("job")
				.incrementer(new RunIdIncrementer())
				.listener(joblistener())
				.start(step())
				//.stepB
				//.stepC
				.build();
	}
}
