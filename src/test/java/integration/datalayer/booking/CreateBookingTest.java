package integration.datalayer.booking;

import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.BookingCreation;
import dto.CustomerCreation;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("integration")
class CreateBookingTest extends ContainerizedDbIntegrationTest {
    private BookingStorage bookingStorage;
    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;


    @BeforeAll
    public void Setup() throws SQLException {
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

    @Test
    public void mustSaveBookingInDatabaseWhenCallingCreateBooking() throws SQLException {
        // Arrange
        // Act
        bookingStorage.createBooking(new BookingCreation(1, 1, Date.valueOf("2000-02-20"), Time.valueOf("10:10:10"), Time.valueOf("20:20:20")));

        // Assert
        var bookings = bookingStorage.getBookings();
        assertTrue(
                bookings.stream().anyMatch(x ->
                        x.getCustomerId() == 1 &&
                        x.getEmployeeId() == 1 &&
                        x.getDate().equals(Date.valueOf("2000-02-20")) &&
                        x.getStart().equals(Time.valueOf("10:10:10")) &&
                        x.getEnd().equals(Time.valueOf("20:20:20"))));
    }

    @Test
    public void mustReturnLatestId() throws SQLException {
        // Arrange
        // Act
        var id1 = bookingStorage.createBooking(new BookingCreation(1, 1, Date.valueOf("1111-11-11"), Time.valueOf("11:11:11"), Time.valueOf("11:11:11")));
        var id2 = bookingStorage.createBooking(new BookingCreation(2, 2, Date.valueOf("2222-02-22"), Time.valueOf("22:22:22"), Time.valueOf("22:22:22")));

        // Assert
        assertEquals(1, id2 - id1);
    }
}
