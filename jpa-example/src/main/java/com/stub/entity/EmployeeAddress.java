package com.stub.entity;

import java.io.Serializable;

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
@Table(name ="EMPADDRESS")
public class EmployeeAddress   implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EMP_ID")
	@GeneratedValue(generator = "gen")
	@GenericGenerator(name = "gen", strategy = "foreign", parameters = @Parameter(name = "property", value = "employeeDetails"))
	private Long empId;
	
	@Column(name = "ADDRESS1")
	private String empAddress1;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	@JsonBackReference
	private EmployeeDetails employeeDetails;
/*
	public EmployeeAddress(String empAddress1) {
		super();
		this.empAddress1 = empAddress1;
	}

	public EmployeeAddress() {
		super();
		// TODO Auto-generated constructor stub
	}
*/	
	
	
}
