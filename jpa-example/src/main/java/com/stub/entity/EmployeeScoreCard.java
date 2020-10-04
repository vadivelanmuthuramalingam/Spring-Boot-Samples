package com.stub.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "EMPSCORECARD")
public class EmployeeScoreCard  implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	/*
	@Column(name = "EMP_ID")
	@JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID")
	@GeneratedValue(generator = "genEmpId")
	@GenericGenerator(name = "genEmpId", strategy = "foreign", parameters = @Parameter(name = "property", value = "employeeDetails"))
	private Long empId;
	*/
	
	//@Temporal(TemporalType.DATE)
	//@Column(name = "ENTRYDT")
	//private Date entryDt;
	
	@Column(name = "SCORE")
	private BigDecimal score;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMP_ID", referencedColumnName = "EMP_ID")
	@GeneratedValue(generator = "genEmpId")
	@GenericGenerator(name = "genEmpId", strategy = "foreign", parameters = @Parameter(name = "property", value = "employeeDetails"))
	private EmployeeDetails employeeDetails;
}
