
package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.util.Properties;

public class DBConnection {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     DBConnection.class.getClassLoader()
                             .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                        "db.properties not found");
            }

            properties.load(input);

        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password")
        );
    }
}
