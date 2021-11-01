package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;

import java.sql.SQLException;
import java.util.Collection;

public interface BookingStorage {

    int createBooking(BookingCreation booking) throws SQLException;
    Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException;
    // This method is not mentioned in the assignment, but seemed kind of necessary
    // To complete the BookingServiceImpl class
    Collection<Booking> getBookingsForEmployee(int customerId) throws SQLException;

    Collection<Booking> getBookings() throws SQLException;

    Booking getBookingWithId(int id) throws SQLException;
}
