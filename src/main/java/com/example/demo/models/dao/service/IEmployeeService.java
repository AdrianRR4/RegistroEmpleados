package com.example.demo.models.dao.service;

import java.util.List;

import com.example.demo.models.entity.Employee;

public interface IEmployeeService {
	
	public List<Employee> findAll();
	
	public void save(Employee employee); 
	
	public Employee findOne(Long id);
	
	public void delete(Long id );
 
}
