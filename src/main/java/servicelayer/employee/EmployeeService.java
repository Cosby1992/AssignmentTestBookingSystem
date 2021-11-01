package servicelayer.employee;

import dto.Employee;
import dto.EmployeeCreation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;

public interface EmployeeService {

    int createEmployee(String firstname, String lastname, Date birthdate) throws EmployeeServiceException;
    Employee getEmployeeById(int id) throws SQLException;
    Collection<Employee> getEmployees() throws SQLException;

}
