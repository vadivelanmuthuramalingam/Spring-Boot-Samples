package com.stub.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.stub.dto.RequestMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/test")
@Api(value = "StudentServiceController", description = "Used to maintain the resaturent details and their menu items" )

public class StudentServiceController {

	@CrossOrigin
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(value = "Bulk upload - MenuItem/Catalogue for Particular Restaurent")
	//@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")

    public String get() {
		
		System.out.print("test");
		return "Success";
		
	}
	
	
	
	@CrossOrigin
    @RequestMapping(value = "/getallstudents", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Bulk upload - MenuItem/Catalogue for Particular Restaurent")
	@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")

    public ResponseEntity<?> getallstudents(HttpServletRequest request) {
		
		String userAgent2 = request.getHeader("user-agent");
		return new ResponseEntity<Object>(userAgent2, HttpStatus.OK);
		
	}
	
	
	
	@CrossOrigin
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Bulk upload - MenuItem/Catalogue for Particular Restaurent")
	@ApiImplicitParams(value = {
			  @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
			  @ApiImplicitParam(name = "X-Custom-Header", value = "A Custom Header", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "my header example")
			})
    public ResponseEntity<?> addStudent(HttpServletRequest request, @RequestBody RequestMessage requestMessage) {
		
		return new ResponseEntity<Object>(requestMessage, HttpStatus.OK);
		
		/*
		Object obj = requestMapper.convert(requestMessage);
		
		if(obj != null)
    		return new ResponseEntity<Object>(obj, HttpStatus.OK);
    	
    	return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    	*/
    	
    }
}
