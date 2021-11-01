package servicelayer.booking;

import dto.Booking;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;

public interface BookingService {

    int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException;
    Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException;
    Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException;
    Booking getBookingWithId(int id) throws SQLException;

}
