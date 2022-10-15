package com.lalit.controller;

import com.lalit.model.Employee;
import com.lalit.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.lalit.ultilities.EmployeeUtilities.*;

@RestController
@RequestMapping(value = "/api")
public class EmployeeControllerApi {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/getAllEmployee", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getallEmps(@RequestParam(value = "sortBy",required = false) String sortBy, @RequestParam(value = "desc",required = false) boolean desc){
//        System.out.println("Inside api controllers getallEmps method ---- sortby : "+ sortBy +" and desc ? "+desc);
        List<Employee> list = employeeService.getAllEmployees();
        if(sortBy!=null){
            if(sortBy.equalsIgnoreCase("firstName")){
                customSortEmployees(list, sortEmployeeByFirstName,desc);
            }else if(sortBy.equalsIgnoreCase("lastName")){
                customSortEmployees(list, sortEmployeeByLastName,desc);
            }else if(sortBy.equalsIgnoreCase("email")){
                customSortEmployees(list, sortEmployeeByEmailId,desc);
            }else if(sortBy.equalsIgnoreCase("dateOfJoining")){
                customSortEmployees(list, getSortEmployeeByDateOfJoining,desc);
            }else{
                customSortEmployees(list, sortEmployeeById,desc);
            }
        }
        else if(desc){
            Collections.reverse(list);
        }
//        System.out.println(list);
        return new ResponseEntity<List<Employee>>(list,HttpStatus.OK);
    }

    @RequestMapping(value = "/employeeData/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> addEmployeePage(@PathVariable(value = "email") String emailId){
//        System.out.println("Email fetched from path var : "+emailId);
        Employee resEmp = employeeService.getEmployeeByEmailId(emailId);
        if(resEmp==null){
            return new ResponseEntity<Employee>(resEmp, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(resEmp,HttpStatus.OK);
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addEmployee(@RequestBody Employee emp){
//        System.out.println(emp);
        int res = employeeService.createEmployee(emp);
        if(res == -1){
            return new ResponseEntity<String>("Employee could not be added",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Employee created successfully",HttpStatus.OK);
    }

    @RequestMapping(value = "/updateEmployee",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateEmployeeData(@RequestBody Employee emp){
//        System.out.println("Update method : "+emp);

        Employee employee = employeeService.getEmployeeByID(emp.getId());
        if(employee==null){
            return new ResponseEntity<String>("Employee data could dont be updated",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        employee.setFirstName(emp.getFirstName());
        employee.setLastName(emp.getLastName());
        Employee resEmp = employeeService.updateEmployee(employee);
        if(resEmp==null){
            return new ResponseEntity<String>("Employee data could dont be updated",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>("Employee updated successfully",HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteEmployee/{emailId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteEmployeeData(@PathVariable(value = "emailId",required = true) String email){
//        System.out.println("email fetched from url is "+email);
        int res = employeeService.deleteEmployee(email);
        if(res==-1){
            return new ResponseEntity<String>("No Employee with email "+email+" exists",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("Employee with email "+email+" successfully deleted",HttpStatus.OK);
    }
}

