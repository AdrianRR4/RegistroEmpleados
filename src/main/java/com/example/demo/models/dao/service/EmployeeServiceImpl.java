package com.example.demo.models.dao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.dao.IEmployeeDao;
import com.example.demo.models.entity.Employee;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private IEmployeeDao employeeDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Employee> findAll() {
	
		return (List<Employee>) employeeDao.findAll();
	}

	@Transactional
	@Override
	public void save(Employee employee) {
				employeeDao.save(employee);	
	}

	@Transactional(readOnly=true)
	@Override
	public Employee findOne(Long id) {
		
		return employeeDao.findById(id).orElse(null);
	}
	@Transactional
	@Override	
	public void delete(Long id) {
		
		employeeDao.deleteById(id);
	}

	
	
	
}
