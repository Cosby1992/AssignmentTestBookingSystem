package datalayer.employee;

import dto.Employee;
import dto.EmployeeCreation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class EmployeeStorageImpl implements EmployeeStorage {

    private final String connectionString;
    private final String username, password;

    public EmployeeStorageImpl(String connectionString, String username, String password) {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public int createEmployee(EmployeeCreation employee) throws SQLException {
        var sql = "insert into Employees(firstname, lastname, birthdate) values (?, ?, ?)";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, employee.getFirstname());
            stmt.setString(2, employee.getLastname());
            stmt.setDate(3, employee.getBirthdate());

            stmt.executeUpdate();

            // get the newly created id
            try (var resultSet = stmt.getGeneratedKeys()) {
                resultSet.next();
                int newId = resultSet.getInt(1);
                return newId;
            }
        }
    }

    @Override
    public Employee getEmployeeWithId(int employeeId) throws SQLException {

        var sql = "select * from Employees where id = " + employeeId;
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.execute();

            // get the newly created id
            try (var resultSet = stmt.getResultSet()) {

                if (resultSet.next()) {
                    return new Employee(
                            resultSet.getInt("id"),
                            resultSet.getString("firstname"),
                            resultSet.getString("lastname"),
                            resultSet.getDate("birthdate"));
                }

                return null;

            }
        }
    }

    @Override
    public Collection<Employee> getEmployees() throws SQLException {

        var sql = "select * from Employees";
        try (var con = getConnection();
             var stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.execute();

            Collection<Employee> employees = new ArrayList<>();

            var resultSet = stmt.getResultSet();

            while (resultSet.next()) {
                employees.add(new Employee(
                        resultSet.getInt("id"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getDate("birthdate")
                ));
            }

            return employees;
        }
    }

}
