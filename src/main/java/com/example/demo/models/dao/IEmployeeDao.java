		package com.example.demo.models.dao;
		

import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.entity.Employee;

public interface IEmployeeDao extends CrudRepository<Employee, Long> {
	
	

}
