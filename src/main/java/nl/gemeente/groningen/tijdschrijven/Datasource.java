package nl.gemeente.groningen.tijdschrijven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Datasource {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/tijd?useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin";
    private static final String USER = "tijdschrijven";
    private static final String PSW = "T1jd5chr1jven";

    public Connection makeConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(JDBC_URL, USER, PSW);
        }   catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }
}