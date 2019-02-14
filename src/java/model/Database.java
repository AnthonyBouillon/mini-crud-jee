package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_ee", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.getMessage();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

}