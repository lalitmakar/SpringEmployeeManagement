package com.lalit.service;

import com.lalit.model.Employee;

import java.util.List;

public interface EmployeeService {

    int createEmployee(Employee emp);

    Employee getEmployeeByEmailId(String email_id);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee emp);

    int deleteEmployee(String email_id);

    Employee getEmployeeByID(int id);

}
