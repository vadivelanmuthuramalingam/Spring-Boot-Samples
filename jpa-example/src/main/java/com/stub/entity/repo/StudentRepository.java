package com.stub.entity.repo;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;


import com.stub.entity.Student;

@Configuration
@Lazy
public interface StudentRepository extends JpaRepository<Student, String> {

}
