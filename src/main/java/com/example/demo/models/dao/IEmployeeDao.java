		package com.example.demo.models.dao;
		

import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.models.entity.Employee;

public interface IEmployeeDao extends PagingAndSortingRepository<Employee, Long> {
	
	

}
