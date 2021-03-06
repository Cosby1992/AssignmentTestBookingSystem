package dto;

import java.sql.Date;

public class EmployeeCreation {

    private final String firstname, lastname;
    private final Date birthdate;

    public EmployeeCreation(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = null;
    }

    public EmployeeCreation(String firstname, String lastname, Date birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
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
}
