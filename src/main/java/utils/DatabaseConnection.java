package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL="jdbc:mariadb://localhost:3306/mspace";

    public static Connection getConnection() throws SQLException {
        try{
            Class.forName("org.mariadb.jdbc.Driver");
        }catch (ClassNotFoundException e){
            throw new SQLException("JDBC Driver not found",e);

        }
        return DriverManager.getConnection(URL, "josh", "JSG@18cs");
    }
}
