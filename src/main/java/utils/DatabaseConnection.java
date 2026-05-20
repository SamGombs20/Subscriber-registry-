package utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;

public class DatabaseConnection {

    public static Connection getConnection() throws Exception {
        Context context = new InitialContext();
        DataSource dataSource=(DataSource) context.lookup("java:comp/env/jdbc/SubscriberDB");
        return dataSource.getConnection();
    }
}
