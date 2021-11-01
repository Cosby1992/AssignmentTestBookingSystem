package dto;

import java.sql.Date;

public class CustomerCreation {

    public final String firstname, lastname, phone;
    public final Date birthdate;

    public CustomerCreation(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = null;
        this.birthdate = null;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public Date getBirthdate() {
        return birthdate;
    }
}
