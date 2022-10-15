package com.lalit.controller;


import com.lalit.model.Employee;
import com.lalit.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.lalit.ultilities.EmployeeUtilities.customSortEmployees;
import static com.lalit.ultilities.EmployeeUtilities.*;

@Controller
public class EmployeeController {



    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Autowired
    EmployeeService service;



    @RequestMapping(value = {"/","/index"})
    public String home(@RequestParam(value = "sortBy",required = false) String sortBy, @RequestParam(value = "desc", required = false) boolean desc , Model model){
//        System.out.println(sortBy);
//        System.out.println(desc);
//        System.out.println("HOME ");
        List<Employee> list = service.getAllEmployees();
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
        }else{
            Collections.reverse(list);
            System.out.println(list);
        }
        model.addAttribute("empList",list);
        return "index";
    }

    // moved to view controllers as there is no controller logic to execute
    // cannot move since there is another end point with same name but diff req method
    @RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
    public String addEmployeePage(){
        return "addEmployee";
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String addEmployee(@RequestParam("firstName") String firstname,
                              @RequestParam("lastName") String lastname,
                              @RequestParam("email") String email,
                              @RequestParam("dateOfJoining") String DOJ){

        System.out.println("inside add emp");

        Employee emp = new Employee();
        emp.setFirstName(firstname);
        emp.setLastName(lastname);
        emp.setEmail(email);
        emp.setDateOfJoining(LocalDate.parse(DOJ,formatter));
        int res = service.createEmployee(emp);
        if(res == -1){
            System.out.println("Could not add emp");
        }
        else{
            System.out.println("Emp added with id "+res);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/getEmployee/{email_id}")
    public String getEmployee(@PathVariable("email_id") String email){
        System.out.println("inside get emp");
        Employee res = service.getEmployeeByEmailId(email);
        if(res == null){
            System.out.println("Could not get emp");
        }
        else{
            System.out.println("Emp got with data "+res);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/deleteEmployeeByEmail/{email_id}")
    public String deleteEmployeeByEmail(@PathVariable("email_id") String email){
        System.out.println("Inside delete method");
        int res = service.deleteEmployee(email);
        if(res == -1){
            System.out.println("Could not delete employee");
        }
        if(res==0){
            System.out.println("deleted successfully");
        }
        return "redirect: /";
    }

    @RequestMapping(value = "/deleteEmployeeById/{id}")
    public String deleteEmployeeById(@PathVariable("id") int id){
        System.out.println("Inside delete by id method");
        Employee emp = service.getEmployeeByID(id);
        int res = service.deleteEmployee(emp.getEmail());
        if(res == -1){
            System.out.println("Could not delete employee");
        }
        if(res==0){
            System.out.println("deleted successfully");
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/getAllEmployees")
    public String getAllEmployees(){
        System.out.println("Inside all emps method");
        List<Employee> list = service.getAllEmployees();
        System.out.println(list);
        return "redirect:/";
    }

    @RequestMapping(value = "/updateEmployee/{id}")
    public String updateEmployeebyId(@PathVariable("id") int id, Model model){
        Employee emp = service.getEmployeeByID(id);
        System.out.println("Emp fetched from db is : "+emp);
        System.out.println("That was from update emp by id method");
        model.addAttribute("emp", emp);
        return "update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateEmployee(@RequestParam("id") int id,
                                 @RequestParam("firstName") String firstname,
                                 @RequestParam("lastName") String lastname,
                                 @RequestParam("email") String email,
                                 @RequestParam("dateOfJoining") String DOJ){



        System.out.println(id);
        System.out.println(firstname);
        System.out.println(lastname);
        System.out.println(email);
        System.out.println(DOJ);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(DOJ,formatter);

        System.out.println("Localdate : "+date);

        Employee emp = new Employee();
        emp.setId(id);
        emp.setFirstName(firstname);
        emp.setLastName(lastname);
        emp.setEmail(email);
        emp.setDateOfJoining(date);

        System.out.println(emp);

        Employee e = service.updateEmployee(emp);
        return "redirect:/";
    }

}
