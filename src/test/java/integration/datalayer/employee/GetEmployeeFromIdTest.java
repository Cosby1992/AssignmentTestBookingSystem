package integration.datalayer.employee;

import com.github.javafaker.Faker;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.CustomerCreation;
import dto.Employee;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class GetEmployeeFromIdTest extends ContainerizedDbIntegrationTest {
    private EmployeeStorage employeeStorage;

    /* changed code */

    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(4);

        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());

        var numEmployees = employeeStorage.getEmployees().size();
        if (numEmployees < 100) {
            addFakeEmployees(100 - numEmployees);
        }
    }

    private void addFakeEmployees(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            EmployeeCreation c = new EmployeeCreation(faker.name().firstName(), faker.name().lastName());
            employeeStorage.createEmployee(c);
        }
    }

    @Test
    public void getEmployeeWithIdMustReturnCorrectEmployee() throws SQLException {

        EmployeeCreation ec = new EmployeeCreation("John","Carlssonn", Date.valueOf("1992-03-28"));

        int insertedId = employeeStorage.createEmployee(ec);

        // Assert
        Employee employee = employeeStorage.getEmployeeWithId(insertedId);
        assertTrue(employee.getFirstname().equals(ec.getFirstname()) &&
                    employee.getLastname().equals(ec.getLastname()) &&
                    employee.getBirthdate().equals(ec.getBirthdate()));
    }

}


