package com.stub.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.stub.api.dto.EmpDetailsDTO;
import com.stub.entity.AbstractModel;
import com.stub.entity.Address;
import com.stub.entity.EmployeeAddress;
import com.stub.entity.EmployeeDetails;
import com.stub.entity.EmployeeScoreCard;
import com.stub.entity.Student;
import com.stub.entity.repo.EmployeeAddressRepository;
import com.stub.entity.repo.EmployeeDetailsRepository;
import com.stub.entity.repo.StudentRepository;

import org.dozer.DozerBeanMapper;
import org.hibernate.Session;
import org.hibernate.Transaction;



@Service
public class EmployeeService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	
	@Autowired
	private EmployeeDetailsRepository empDetailsRepository;

	@Autowired
	private EmployeeAddressRepository empAddressDtlsRepository;
	
	public Optional<?> getEmployee(Long empId)
	{
		return empDetailsRepository.findById(empId);
	}
	
	public List<EmployeeDetails> getAllEmployee()
	{
		return empDetailsRepository.findAll();
	}
	
	@Transactional
	public Optional<EmployeeDetails> addEmployee(EmpDetailsDTO empDetailsDTO)
	{
		
		System.out.print("Enter in Service class");
		
		Student student = new Student("Sam","Disilva","Maths");
        //Address address = new Address("10 Silver street","NYC","USA");
        
        //student.setAddress(address);
        //address.setStudent(student);
        
        studentRepository.save(student);
        
        List<Student> lst = studentRepository.findAll();
        
        
        EmployeeDetails employeeDetails = new EmployeeDetails();
        EmployeeAddress employeeAddress = new EmployeeAddress();
        
        
        employeeDetails.setEmpName("Vadivealn");
        employeeAddress.setEmpAddress1("Add");

        employeeDetails.setEmployeeAddress(employeeAddress);
        employeeAddress.setEmployeeDetails(employeeDetails);
        
        List<EmployeeScoreCard> lstEmployeeScoreCard = new ArrayList<>();
        EmployeeScoreCard employeeScoreCard1 = new EmployeeScoreCard();
        employeeScoreCard1.setScore(new BigDecimal(98.4));
        employeeScoreCard1.setEmployeeDetails(employeeDetails);
        
        EmployeeScoreCard employeeScoreCard2 = new EmployeeScoreCard();
        employeeScoreCard2.setScore(new BigDecimal(95));
        employeeScoreCard2.setEmployeeDetails(employeeDetails);
        
        lstEmployeeScoreCard.add(employeeScoreCard1);
        lstEmployeeScoreCard.add(employeeScoreCard2);
        
        employeeDetails.setEmployeeScoreCard(lstEmployeeScoreCard);
        
        EmployeeDetails empdtl = empDetailsRepository.save(employeeDetails);
		
        EmployeeDetails test = deleteOneRecordFromScoreTable(empdtl.getEmpId(), empdtl);
		
        return  empDetailsRepository.findById(employeeDetails.getEmpId());
		
        /*
		if(empdtl2.isPresent())
			return empdtl2.get();
		
		
		return null;
		*/
	}

	private EmployeeDetails deleteOneRecordFromScoreTable(final Long empId, final EmployeeDetails empDtls)
	{
        EmployeeDetails employeeDetails = new EmployeeDetails();
        EmployeeAddress employeeAddress = new EmployeeAddress();
        
        employeeDetails.setEmpId(empId);
        employeeDetails.setEmpName("Vadivealn");
        
        employeeAddress.setEmpAddress1("Add");
        employeeAddress.setEmpId(empId);
        
        employeeDetails.setEmployeeAddress(employeeAddress);
        employeeAddress.setEmployeeDetails(employeeDetails);
        
        List<EmployeeScoreCard> lstEmployeeScoreCard = new ArrayList<>();
        EmployeeScoreCard employeeScoreCard1 = new EmployeeScoreCard();
        employeeScoreCard1.setScore(empDtls.getEmployeeScoreCard().get(0).getScore());
        employeeScoreCard1.setEmployeeDetails(empDtls.getEmployeeScoreCard().get(0).getEmployeeDetails());
        employeeScoreCard1.setId(empDtls.getEmployeeScoreCard().get(0).getId());
        
        //EmployeeScoreCard employeeScoreCard2 = new EmployeeScoreCard();
        //employeeScoreCard2.setScore(new BigDecimal(95));
        //employeeScoreCard2.setEmployeeDetails(employeeDetails);
        
        lstEmployeeScoreCard.add(employeeScoreCard1);
        //lstEmployeeScoreCard.add(employeeScoreCard2);
        
        employeeDetails.setEmployeeScoreCard(lstEmployeeScoreCard);
        
        EmployeeDetails empdtl = empDetailsRepository.save(employeeDetails);
		
        return empdtl;
		
	}
	private EmployeeDetails getDomainObject(EmpDetailsDTO empDetailsDTO)
	{
		DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(Arrays.asList("dozer\\emplyee-mapper.xml"));
        EmployeeDetails employeeDetails = dozerBeanMapper.map(empDetailsDTO, EmployeeDetails.class);
        
        return employeeDetails;
	}
	
	public EmployeeAddress addEmployeeAddress(EmployeeAddress empAddressDtls) {
		return  empAddressDtlsRepository.save(empAddressDtls);
	}
	
}
