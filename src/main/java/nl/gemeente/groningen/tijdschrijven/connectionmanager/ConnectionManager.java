package nl.gemeente.groningen.tijdschrijven.connectionmanager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConnectionManager {
    private static Connection conn;
    private static final Logger logger = Logger.getLogger(ConnectionManager.class);

    public static Connection getConnection() throws SQLException {
	if (conn == null) {
	    Properties properties = new Properties();
	    try {
		properties.load(ConnectionManager.class.getResourceAsStream("/application.properties"));
	    } catch (IOException e) {
		logger.error(e.getMessage());
	    }
	    String host = properties.getProperty("spring.datasource.url");
	    String user = properties.getProperty("spring.datasource.username");
	    String pass = properties.getProperty("spring.datasource.password");

	    conn = DriverManager.getConnection(host, user, pass);
	}
	return conn;
    }

    private ConnectionManager() {
    }
}
