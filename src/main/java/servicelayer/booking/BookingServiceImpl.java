package servicelayer.booking;

import datalayer.booking.BookingStorage;
import dto.Booking;
import dto.BookingCreation;
import dto.SmsMessage;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Collection;

public class BookingServiceImpl implements BookingService {

    private BookingStorage storage;

    public BookingServiceImpl(BookingStorage storage) {
        this.storage = storage;
    }

    @Override
    public int createBooking(int customerId, int employeeId, Date date, Time start, Time end) throws BookingServiceException {
        try {
            //TODO: try to send an SMS on completion
            return storage.createBooking(new BookingCreation(customerId, employeeId, date, start, end));
        } catch (SQLException e) {
            throw new BookingServiceException(e.getMessage());
        }
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException {
        return storage.getBookingsForCustomer(customerId);
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException {
        return storage.getBookingsForEmployee(employeeId);
    }

    @Override
    public Booking getBookingWithId(int id) throws SQLException {
        return storage.getBookingWithId(id);
    }
}
