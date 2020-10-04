package com.stub.entity.repo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;

import com.stub.entity.EmployeeDetails;

@Configuration
@Lazy
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {

}
