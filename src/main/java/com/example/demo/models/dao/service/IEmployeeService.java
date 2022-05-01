package com.example.demo.models.dao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.models.entity.Employee;

public interface IEmployeeService {
	
	public List<Employee> findAll();
	public Page<Employee> findAll(Pageable pageable);
	
	public void save(Employee employee); 
	
	public Employee findOne(Long id);
	
	public void delete(Long id );
 
}
