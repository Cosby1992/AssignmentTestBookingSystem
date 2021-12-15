package datalayer.booking;

import dto.Booking;
import dto.BookingCreation;
import dto.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class BookingStorageImpl implements BookingStorage {

    private final String connectionString;
    private final String username, password;

    public BookingStorageImpl(String connectionString, String username, String password) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    //TODO: Create method that can check weather the booking is valid (work-time for employee, not a sunday, not overlapping with other booking)

    @Override
    public int createBooking(BookingCreation booking) throws SQLException {

        var sql = "insert into Bookings(customerId, employeeId, date, start, end) values (?, ?, ?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getEmployeeId());
            stmt.setDate(3, booking.getDate());
            stmt.setTime(4, booking.getStart());
            stmt.setTime(5, booking.getEnd());

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }
    }

    @Override
    public Collection<Booking> getBookingsForCustomer(int customerId) throws SQLException{

        var sql = "select * from Bookings where customerId = " + customerId;

        return handleDBQuery(sql);
    }

    @Override
    public Collection<Booking> getBookingsForEmployee(int employeeId) throws SQLException {
        var sql = "select * from Bookings where employeeId = " + employeeId;

        return handleDBQuery(sql);

    }

    @Override
    public Collection<Booking> getBookings() throws SQLException {
        var sql = "select * from Bookings";

        return handleDBQuery(sql);
    }

    @Override
    public Booking getBookingWithId(int id) throws SQLException {
        var sql = "select * from Bookings where id = " + id;

        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.execute();

                try (var resultSet = stmt.getResultSet()) {


                    if(resultSet.next()) {
                        return new Booking(
                                resultSet.getInt("id"),
                                resultSet.getInt("customerId"),
                                resultSet.getInt("employeeId"),
                                resultSet.getDate("date"),
                                resultSet.getTime("start"),
                                resultSet.getTime("end")
                        );
                    }


                }

            }

            return null;
        }

    private Collection<Booking> handleDBQuery(String sql) throws SQLException {
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.execute();

            try (var resultSet = stmt.getResultSet()) {

                Collection<Booking> bookings = new ArrayList<>();

                while (resultSet.next()) {

                    bookings.add(new Booking(
                            resultSet.getInt("id"),
                            resultSet.getInt("customerId"),
                            resultSet.getInt("employeeId"),
                            resultSet.getDate("date"),
                            resultSet.getTime("start"),
                            resultSet.getTime("end")
                    ));

                }

                return bookings;

            }
        }
    }
}
