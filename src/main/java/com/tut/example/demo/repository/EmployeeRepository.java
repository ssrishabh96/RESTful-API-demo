package com.tut.example.demo.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tut.example.demo.entity.Employee;

@Configuration
public interface EmployeeRepository extends JpaRepository<Employee, Long> 
{

}
