package com.mudzingwa.sec.Entity;

import lombok.Data;
//@Entity
@Data

public class Student {
	//@Id
	//@GeneratedValue(strategy=GenerationType.)
	private String studentId;
	private String fname;
	private Double costprice;
	
	
	private Double discount;
	private Double amountpaid;

}
