package servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Employee;
import dto.EmployeeCreation;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeStorage storage;

    public EmployeeServiceImpl(EmployeeStorage storage) {
        this.storage = storage;
    }

    @Override
    public int createEmployee(String firstname, String lastname, Date birthdate) throws EmployeeServiceException {
        try {
            return storage.createEmployee(new EmployeeCreation(firstname, lastname, birthdate));
        } catch (SQLException e) {
            throw new EmployeeServiceException(e.getMessage());
        }
    }

    @Override
    public Employee getEmployeeById(int id) throws SQLException {

        // It makes no sense that "storage.getEmployeeWithId(int id)" returns a Collection<Employee>
        // But unfortunately this is how it was defined in the assignment
        return storage.getEmployeeWithId(id);
    }

    @Override
    public Collection<Employee> getEmployees() throws SQLException {
        return storage.getEmployees();
    }
}
