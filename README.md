# SpringEmployeeManagement
A Basic Employee Management CRUD functionality using Spring MVC, also created REST endpoints for the same using Spring REST

EmployeeUtilities:
    Custom Methods for Sorting the Employees based on criteria given by user
    
LocalDateConverter:
    Custom class to deserialize the json date to LocalDate Automatically used in Employee class as @JsonDeserialize(using = LocalDateConverter.class)
    
EmployeeRepository:
    Extends JPA Repository to provide implementations for CRUD func
    
Controller package has EmployeeController for MVC website and EmployeeControllerApi for Rest Endpoints

static folder has css which is accessed by <mvc:resources mapping="/static/**" location="/WEB-INF/static/" />


