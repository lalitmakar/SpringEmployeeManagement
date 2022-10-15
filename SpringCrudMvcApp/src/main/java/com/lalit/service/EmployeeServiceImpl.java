package com.lalit.service;

import com.lalit.entity.EmployeeEntity;
import com.lalit.model.Employee;
import com.lalit.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(value = "txManager")
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository repo;


    @Override
    public int createEmployee(Employee emp) {

        System.out.println("txn active ? "+TransactionSynchronizationManager.isActualTransactionActive());

        if(emp != null){
            EmployeeEntity entity = convertBeanToEntity(emp);
            EmployeeEntity res = repo.save(entity);
            return res.getId();
        }
        return -1;
    }

    @Override
    public Employee getEmployeeByEmailId(String email_id) {
        EmployeeEntity entity = repo.findByEmail(email_id);
        if(entity != null){
            return convertEntityToBean(entity);
        }
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<EmployeeEntity> listOfEntity = repo.findAll();
        List<Employee> listOfEmployee = new ArrayList<>();
        for(EmployeeEntity entity : listOfEntity){
            listOfEmployee.add(convertEntityToBean(entity));
        }
        return listOfEmployee;
    }

    @Override
    public Employee updateEmployee(Employee emp) {
        if(emp != null){
            EmployeeEntity entity = convertBeanToEntity(emp);
            entity = repo.save(entity);
            return convertEntityToBean(entity);
        }
        return null;
    }

    @Override
    public int deleteEmployee(String email_id) {
        Employee emp = getEmployeeByEmailId(email_id);
        if(emp != null){
            repo.delete(convertBeanToEntity(emp));
            return 0;
        }
        return -1;
    }

    @Override
    public Employee getEmployeeByID(int id) {
        Optional<EmployeeEntity> entity = repo.findById(id);
        if(entity.isPresent()){
            Employee emp = convertEntityToBean(entity.get());
            return emp;
        }
        return null;
    }

    public Employee convertEntityToBean(EmployeeEntity entity){
        Employee emp = new Employee();
        BeanUtils.copyProperties(entity, emp);
        return emp;
    }

    public EmployeeEntity convertBeanToEntity(Employee emp){
        EmployeeEntity entity = new EmployeeEntity();
        BeanUtils.copyProperties(emp, entity);
        return entity;
    }

}
