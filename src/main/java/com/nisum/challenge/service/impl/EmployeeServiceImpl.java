package com.nisum.challenge.service.impl;

import com.nisum.challenge.model.Employee;
import com.nisum.challenge.repository.EmployeeRepository;
import com.nisum.challenge.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Override
	public Employee create(Employee employee) {
		return repository.save(employee);
	}

	@Override
	public List<Employee> read() {
		return repository.findAll();
	}

	@Override
	public Employee update(Employee employee) {
		return repository.save(employee);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
