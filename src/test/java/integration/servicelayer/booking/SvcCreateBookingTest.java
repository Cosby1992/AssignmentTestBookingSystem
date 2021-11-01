package integration.servicelayer.booking;

import datalayer.booking.BookingStorage;
import datalayer.booking.BookingStorageImpl;
import datalayer.customer.CustomerStorageImpl;
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

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SvcCreateBookingTest extends ContainerizedDbIntegrationTest {

    private BookingService svc;
    private BookingStorage storage;

    @BeforeAll
    public void setup() throws SQLException {
        runMigration(3);
        storage = new BookingStorageImpl(getConnectionString(),"root", getDbPassword());
        svc = new BookingServiceImpl(storage);

        new CustomerStorageImpl(getConnectionString(), "root", getDbPassword()).createCustomer(new CustomerCreation("John", "Doe"));
        new EmployeeStorageImpl(getConnectionString(), "root", getDbPassword()).createEmployee(new EmployeeCreation("Worker John", "Worker Joe"));

    }

    @Test
    public void mustSaveBookingToDatabaseWhenCallingCreateEmployee() throws SQLException, BookingServiceException {
        // Arrange
        int customerId = 1;
        int employeeId = 1;
        Date date = Date.valueOf("2000-01-01");
        Time start = Time.valueOf("08:00:00");
        Time end = Time.valueOf("10:00:00");

        BookingCreation booking = new BookingCreation(customerId, employeeId, date, start, end);

        // Act
        int createdId = svc.createBooking(booking.getCustomerId(), booking.getEmployeeId(), booking.getDate(), booking.getStart(), booking.getEnd());

        Booking bookingFromDb = svc.getBookingWithId(createdId);

        // Assert
        assertEquals(customerId, bookingFromDb.getCustomerId());
        assertEquals(employeeId, bookingFromDb.getEmployeeId());
        assertEquals(date, bookingFromDb.getDate());
        assertEquals(start, bookingFromDb.getStart());
        assertEquals(end, bookingFromDb.getEnd());

    }
}

