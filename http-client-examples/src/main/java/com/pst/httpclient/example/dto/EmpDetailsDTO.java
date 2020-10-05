package com.pst.httpclient.example.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpDetailsDTO extends AbstractModelDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long empId;
	private String empName;
	private EmpAddressDtlsDTO empAddressDtlsDTO;


}
