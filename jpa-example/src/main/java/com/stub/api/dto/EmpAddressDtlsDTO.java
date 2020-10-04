package com.stub.api.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmpAddressDtlsDTO extends AbstractModelDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private Long empId;
	private String address1;
	private String address2;
	private String address3;
	private Long pinCode;
	private String remarks;
	

}
