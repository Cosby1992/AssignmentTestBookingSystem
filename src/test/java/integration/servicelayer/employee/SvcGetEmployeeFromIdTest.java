package integration.servicelayer.employee;

import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.employee.EmployeeService;
import servicelayer.employee.EmployeeServiceException;
import servicelayer.employee.EmployeeServiceImpl;

import java.sql.SQLException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SvcGetEmployeeFromIdTest extends ContainerizedDbIntegrationTest {

    private EmployeeService svc;
    private EmployeeStorage storage;

    @BeforeAll
    public void setup() {
        runMigration(3);
        storage = new EmployeeStorageImpl(getConnectionString(),"root", getDbPassword());
        svc = new EmployeeServiceImpl(storage);
    }

    @Test
    public void getEmployeeFromIdMustReturnCorrectEmployee() throws EmployeeServiceException, SQLException {
        // Arrange
        var firstName = "John";
        var lastName = "Johnson";
        var birthday = Date.valueOf("1960-04-05");

        EmployeeCreation emp = new EmployeeCreation(firstName, lastName, birthday);

        int id = svc.createEmployee(firstName, lastName, birthday);

        // Act
        var createdEmployee = svc.getEmployeeById(id);

        // Assert
        assertTrue(emp.getFirstname().equals(createdEmployee.getFirstname()) &&
                                emp.getLastname().equals(createdEmployee.getLastname()) &&
                                emp.getBirthdate().equals(createdEmployee.getBirthdate()));
    }
}

