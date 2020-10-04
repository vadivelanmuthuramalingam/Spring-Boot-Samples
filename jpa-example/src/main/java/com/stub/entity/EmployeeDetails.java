package com.stub.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Generated;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "EMPLOYEE")
public class EmployeeDetails   implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue //(strategy=GenerationType.IDENTITY)
	@Column(name = "EMP_ID")
	private Long empId;
	
	@Column(name = "NAME")
	private String empName;
	
	@JsonManagedReference
	@OneToOne(mappedBy = "employeeDetails", cascade = CascadeType.ALL)
	private EmployeeAddress employeeAddress;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "employeeDetails" ,cascade = CascadeType.ALL, 
            fetch = FetchType.LAZY, orphanRemoval = true)
	private List<EmployeeScoreCard> employeeScoreCard = new ArrayList<>();
	

	
}
