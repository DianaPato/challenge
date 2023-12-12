package com.nisum.challenge.service;

import com.nisum.challenge.model.Employee;

import java.util.List;

public interface EmployeeService {

	Employee create(Employee employee);

	List<Employee> read();

	Employee update(Employee employee);

	void delete(Long id);

}
