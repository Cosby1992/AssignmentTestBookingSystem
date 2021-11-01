package unit.servicelayer.booking;

import datalayer.booking.BookingStorage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servicelayer.booking.BookingService;
import servicelayer.booking.BookingServiceException;
import servicelayer.booking.BookingServiceImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("unit")
public class CreateBookingTest {


    // SUT (System Under Test)
    private BookingService bookingService;

    // DOC (Depended-on Component)
    private BookingStorage storageMock;


    @BeforeAll
    public void beforeAll(){
        storageMock = mock(BookingStorage.class);
        bookingService = new BookingServiceImpl(storageMock);
    }

    @Test
    public void mustCallStorageWhenCreatingEmployee() throws BookingServiceException, SQLException {
        // Arrange
        // Act
        var customerId = 1;
        var employeeId = 2;
        var date = Date.valueOf("2021-10-29");
        var start = Time.valueOf("08:00:00");
        var end = Time.valueOf("15:00:00");

        bookingService.createBooking(customerId, employeeId, date, start, end);

        // Assert
        // Can be read like: verify that storageMock was called 1 time on the method
        //   'createCustomer' with an argument whose 'firstname' == firstName and
        //   whose 'lastname' == lastName
        verify(storageMock, times(1))
                .createBooking(
                        argThat(x -> x.getCustomerId() == customerId &&
                                x.getEmployeeId() == employeeId &&
                                x.getDate().equals(date) &&
                                x.getStart().equals(start) &&
                                x.getEnd().equals(end)));
    }
}
