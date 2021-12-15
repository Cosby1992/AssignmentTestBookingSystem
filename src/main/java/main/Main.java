package main;

import datalayer.booking.BookingStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.*;
import datalayer.customer.CustomerStorageImpl;

import java.sql.SQLException;

public class Main {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/DemoApplication";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public static void main(String[] args) throws SQLException {
        CustomerStorageImpl customerStorage = new CustomerStorageImpl(CONNECTION_STRING, DB_USER, DB_PASS);

        System.out.println("Got customers: ");
        for(Customer c : customerStorage.getCustomers()) {
            System.out.println(toString(c));
        }


        BookingStorageImpl bookingStorage = new BookingStorageImpl(CONNECTION_STRING, DB_USER, DB_PASS);

        System.out.println("Got bookings for customer with id = 1: ");
        for (Booking booking : bookingStorage.getBookingsForCustomer(1)){
            System.out.println(toString(booking));
        }


        EmployeeStorage employeeStorage = new EmployeeStorageImpl(CONNECTION_STRING, DB_USER, DB_PASS);

        System.out.println("Got employee with id = 1: ");
        for (Employee employee : employeeStorage.getEmployees()){
            System.out.println(toString(employee));
        }

        System.out.println("The end.");
    }

    public static String toString(Customer c) {
        return "{" + c.getId() + ", " + c.getFirstname() + ", " + c.getLastname() + "}";
    }

    public static String toString(Booking booking) {
        return "{" + booking.getId() + ", " +
                booking.getCustomerId() + ", " +
                booking.getEmployeeId() + ", " +
                booking.getDate() + ", " +
                booking.getStart() + ", " +
                booking.getEnd() + "}";
    }

    public static String toString(Employee employee) {
        return "{" + employee.getId() + ", " +
                employee.getFirstname() + ", " +
                employee.getLastname() + ", " +
                employee.getBirthdate() + "}";
    }
}
