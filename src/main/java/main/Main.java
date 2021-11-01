package main;

import datalayer.booking.BookingStorageImpl;
import datalayer.employee.EmployeeStorage;
import datalayer.employee.EmployeeStorageImpl;
import dto.*;
import datalayer.customer.CustomerStorageImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

public class Main {

    private static final String conStr = "jdbc:mysql://localhost:3306/DemoApplication";
    private static final String user = "root";
    private static final String pass = "";

    public static void main(String[] args) throws SQLException {
        CustomerStorageImpl customerStorage = new CustomerStorageImpl(conStr, user, pass);

        System.out.println("Got customers: ");
        for(Customer c : customerStorage.getCustomers()) {
            System.out.println(toString(c));
        }


        BookingStorageImpl bookingStorage = new BookingStorageImpl(conStr, user, pass);

        System.out.println("Got bookings for customer with id = 1: ");
        for (Booking booking : bookingStorage.getBookingsForCustomer(1)){
            System.out.println(toString(booking));
        }


        EmployeeStorage employeeStorage = new EmployeeStorageImpl(conStr, user, pass);

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
