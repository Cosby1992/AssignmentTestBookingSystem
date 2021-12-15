
import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.CustomerCreation;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import dto.BookingCreation;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateBookingStepDefinitions extends ContainerizedDbIntegrationTest {
    private BookingStorage bookingStorage;
    private BookingCreation bookingCreation;
    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;
    private int insertedId;

    @Before
    public void before() throws SQLException {
        runMigration(4);

        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());
        bookingStorage = new BookingStorageImpl(getConnectionString(), "root", getDbPassword());

        var numCustomers = customerStorage.getCustomers().size();
        if (numCustomers < 100) {
            addFakeCustomers(100 - numCustomers);
        }

        var numEmployees = employeeStorage.getEmployees().size();
        if (numEmployees < 100) {
            addFakeEmployees(100 - numEmployees);
        }

        var numBookings = bookingStorage.getBookings().size();
        if (numBookings < 100) {
            addFakeBookings(100 - numBookings);
        }
    }

    @Given("You have a valid BookingCreation object")
    public void youHaveAValidBookingCreationObject() {
        runMigration(4);
        bookingStorage = new BookingStorageImpl(getConnectionString(),"root", getDbPassword());
        bookingCreation = new BookingCreation(1,1, Date.valueOf("2020-03-24"), Time.valueOf("08:00:00"), Time.valueOf("10:00:00"));
    }

    @When("You create the booking")
    public void youCreateTheBooking() throws SQLException {
        insertedId = bookingStorage.createBooking(bookingCreation);
    }

    @Then("the inserted id should be returned")
    public void theInsertedIdShouldBeReturned() throws SQLException {
        var actual = bookingStorage.getBookingWithId(insertedId);
        assertEquals(bookingCreation.getCustomerId(), actual.getCustomerId());
        assertEquals(bookingCreation.getEmployeeId(), actual.getEmployeeId());
        assertEquals(bookingCreation.getDate(), actual.getDate());
        assertEquals(bookingCreation.getStart(), actual.getStart());
        assertEquals(bookingCreation.getEnd(), actual.getEnd());
    }

    private void addFakeCustomers(int numCustomers) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numCustomers; i++) {
            CustomerCreation c = new CustomerCreation(faker.name().firstName(), faker.name().lastName());
            customerStorage.createCustomer(c);
        }
    }

    private void addFakeEmployees(int numEmployees) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numEmployees; i++) {
            EmployeeCreation e = new EmployeeCreation(faker.name().firstName(), faker.name().lastName());
            employeeStorage.createEmployee(e);
        }
    }

    private void addFakeBookings(int numBookings) throws SQLException {
        Faker faker = new Faker();
        for (int i = 0; i < numBookings; i++) {

            Date date = new Date(faker.date().birthday().getTime());
            long time = faker.date().birthday().getTime();
            Time start = new Time(time);
            Time end = new Time(time + 10000L);

            BookingCreation b = new BookingCreation(numBookings, numBookings, date, start, end);
            bookingStorage.createBooking(b);
        }
    }
}
