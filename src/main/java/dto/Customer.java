package dto;

import java.sql.Date;

public class Customer {
    private final int id;
    private final String firstname, lastname, phone;
    private final Date birthdate;

    public Customer(int id, String firstname, String lastname, Date birthdate, String phone) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getPhone() {
        return phone;
    }
}
