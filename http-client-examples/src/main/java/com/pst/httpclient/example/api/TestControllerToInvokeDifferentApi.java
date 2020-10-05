package com.pst.httpclient.example.api;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pst.httpclient.example.dto.EmpDetailsDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "EmployeeServiceController", description = "Used to maintain the resaturent details and their menu items" )
public class TestControllerToInvokeDifferentApi {

	@CrossOrigin
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ApiOperation(value = "Add New Employee - Pass Employee name and dont input address details. Call- EmpDetails Entity")
    public ResponseEntity<?> createNewEmploye() {
		
		EmpDetailsDTO empDetailsDTO = new EmpDetailsDTO();
		empDetailsDTO.setEmpId(100L);
		empDetailsDTO.setEmpName("EmpName Test");
		
		return new ResponseEntity<Object>(empDetailsDTO, HttpStatus.OK);
		
        /*Optional<EmployeeDetails> result = employeeService.addEmployee(empDetailsDTO);
		
		if(result.isPresent() )
    		return new ResponseEntity<Object>(result.get(), HttpStatus.OK);
    	
    	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    	*/
    }


}
