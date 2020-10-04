package com.stub.api;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stub.api.dto.EmpDetailsDTO;
import com.stub.entity.AbstractModel;
import com.stub.entity.EmployeeAddress;
import com.stub.entity.EmployeeDetails;
import com.stub.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/employee")
@Api(value = "EmployeeServiceController", description = "Used to maintain the resaturent details and their menu items" )
public class EmployeeServiceController {

	@Autowired
	private EmployeeService employeeService;
	
	@SuppressWarnings("deprecation")
	@CrossOrigin
    @RequestMapping(value = "/getModelStructure", method = RequestMethod.GET)
    @ApiOperation(value = "Add Employee Detail- Pass Employee Details with employee address.")
    public ResponseEntity<?> get() {
		
		EmployeeDetails employeeDetails = new EmployeeDetails();
		
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = df.parse("1982-09-10");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 EmployeeAddress employeeAddress = new EmployeeAddress();
		//empDetails.setEmpAddressDtls(empAddressDtls);
		AbstractModel result = null;
		
		if(result != null)
    		return new ResponseEntity<Object>(result, HttpStatus.OK);
    	
    	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

	
	@CrossOrigin
    @RequestMapping(value = "/getemployee/{empId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get employee details based on ID")
    public ResponseEntity<?> getEmployeeDetails(@PathVariable Long empId) {
		//empDetails.setEmpId(0L);
		Optional<?> result = employeeService.getEmployee(empId);
		
		if(result != null)
    		return new ResponseEntity<Object>(result.get(), HttpStatus.OK);
    	
    	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

	
	
	
	@CrossOrigin
    @RequestMapping(value = "/createNewEmploye", method = RequestMethod.POST)
    @ApiOperation(value = "Add New Employee - Pass Employee name and dont input address details. Call- EmpDetails Entity")
    public ResponseEntity<?> createNewEmploye(@RequestBody EmpDetailsDTO empDetailsDTO) {

		
        Optional<EmployeeDetails> result = employeeService.addEmployee(empDetailsDTO);
		
		if(result.isPresent() )
    		return new ResponseEntity<Object>(result.get(), HttpStatus.OK);
    	
    	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

	@CrossOrigin
    @RequestMapping(value = "/addEmployeWithAddress", method = RequestMethod.POST)
    @ApiOperation(value = "Add New Employee Detail- Pass Employee name and address details.  Call- EmpDetails Entity ")
    public ResponseEntity<?> addEmployeWithAddress(@RequestBody EmpDetailsDTO empDetailsDTO) {
		
		/*
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(Arrays.asList("dozer\\emplyee-mapper.xml"));
        EmpDetails empDetails = dozerBeanMapper.map(empDetailsDTO, EmpDetails.class);
        */
		
		
		Optional<EmployeeDetails> result = employeeService.addEmployee(null);
		
		if(result.isPresent() )
    		return new ResponseEntity<Object>(result.get(), HttpStatus.OK);
    	
    	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

	@CrossOrigin
    @RequestMapping(value = "/addEmployeAddress", method = RequestMethod.POST)
    @ApiOperation(value = "Add Employee Addres Detail and the Employee Basic Details is not available."
    		+ " Expected error message."
    		+ " Call- EmpAddress Details Entity")
    public ResponseEntity<?> addEmployeAddressWithOutEmpID(@RequestBody EmployeeAddress employeeAddress) {
		
		//empAddressDtls.setEmpId(0L);
		EmployeeAddress result = employeeService.addEmployeeAddress(employeeAddress);
		
		if(result != null)
    		return new ResponseEntity<Object>(result, HttpStatus.OK);
    	
    	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

	
}
