package com.lalit.ultilities;

import com.lalit.model.Employee;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeUtilities {

    public static List<Employee> customSortEmployees(List<Employee> list, Comparator comparator, boolean desc){
        Collections.sort(list, desc==false ? comparator : Collections.reverseOrder(comparator));
        return list;
    }

    public static Comparator sortEmployeeByEmailId = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            Employee e1 = (Employee) o1;
            Employee e2 = (Employee) o2;
            return e1.getEmail().compareToIgnoreCase(e2.getEmail());
        }
    };

    public static Comparator sortEmployeeById = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            Employee e1 = (Employee) o1;
            Employee e2 = (Employee) o2;
            return e1.getId()-e2.getId();
        }
    };

    public static Comparator getSortEmployeeByDateOfJoining = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            Employee e1 = (Employee) o1;
            Employee e2 = (Employee) o2;
            return e1.getDateOfJoining().compareTo(e2.getDateOfJoining());
        }
    };

    public static Comparator sortEmployeeByFirstName = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            Employee e1 = (Employee) o1;
            Employee e2 = (Employee) o2;
            return e1.getFirstName().compareToIgnoreCase(e2.getFirstName());
        }
    };

    public static Comparator sortEmployeeByLastName = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            Employee e1 = (Employee) o1;
            Employee e2 = (Employee) o2;
            return e1.getLastName().compareToIgnoreCase(e2.getLastName());
        }
    };


}
