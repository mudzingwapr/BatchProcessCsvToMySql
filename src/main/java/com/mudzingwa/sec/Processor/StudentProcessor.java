package com.mudzingwa.sec.Processor;

import org.springframework.batch.item.ItemProcessor;

import com.mudzingwa.sec.Entity.Student;

public class StudentProcessor implements ItemProcessor<Student,Student> {


	@Override
	public Student process(Student item) throws Exception {
		double cprice= item.getCostprice();
		 item.setDiscount(cprice*0.1);
		item.setAmountpaid(cprice-cprice*0.1);
		return item;
	}

}
