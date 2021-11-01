package integration.servicelayer.booking;

import com.github.javafaker.Faker;
import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorage;
import datalayer.customer.CustomerStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.Booking;
import dto.BookingCreation;
import dto.CustomerCreation;
import dto.EmployeeCreation;
import integration.ContainerizedDbIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SvcGetBookingsForCustomerTest extends ContainerizedDbIntegrationTest {

    private BookingService svc;
    private BookingStorage storage;
    private CustomerStorage customerStorage;
    private EmployeeStorage employeeStorage;


    @BeforeAll
    public void Setup() throws SQLException {
        runMigration(4);

        customerStorage = new CustomerStorageImpl(getConnectionString(), "root", getDbPassword());
        employeeStorage = new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword());
        storage = new BookingStorageImpl(getConnectionString(), "root", getDbPassword());
        svc = new BookingServiceImpl(storage);

        var numCustomers = customerStorage.getCustomers().size();
        if (numCustomers < 100) {
            addFakeCustomers(100 - numCustomers);
        }

        var numEmployees = employeeStorage.getEmployees().size();
        if (numEmployees < 100) {
            addFakeEmployees(100 - numEmployees);
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

    @Test
    public void getBookingsForCustomerMustReturnCorrectBookings() throws SQLException, BookingServiceException {

        final int customerId = 1;

        ArrayList<BookingCreation> creations = new ArrayList<>();
        creations.add(new BookingCreation(customerId, 1, Date.valueOf("2000-01-01"), Time.valueOf("01:00:00"), Time.valueOf("11:00:00")));
        creations.add(new BookingCreation(customerId, 2, Date.valueOf("2001-01-01"), Time.valueOf("02:00:00"), Time.valueOf("12:00:00")));
        creations.add(new BookingCreation(customerId, 3, Date.valueOf("2002-01-01"), Time.valueOf("03:00:00"), Time.valueOf("13:00:00")));
        creations.add(new BookingCreation(customerId, 4, Date.valueOf("2003-01-01"), Time.valueOf("04:00:00"), Time.valueOf("14:00:00")));

        for (BookingCreation b : creations) {
            svc.createBooking(b.getCustomerId(), b.getEmployeeId(), b.getDate(), b.getStart(), b.getEnd());
        }

        Collection<Booking> bookingFromDb = svc.getBookingsForCustomer(customerId);

        for (BookingCreation b : creations) {
            assertTrue(bookingFromDb.stream().anyMatch(booking -> booking.getCustomerId() == b.getCustomerId() &&
                                                                    booking.getEmployeeId() == b.getEmployeeId() &&
                                                                    booking.getDate().equals(b.getDate()) &&
                                                                    booking.getStart().equals(b.getStart()) &&
                                                                    booking.getEnd().equals(b.getEnd())));
        }

    }
}


