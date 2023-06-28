package com.goit.dev10.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.goit.dev10.configs.ConnectionConfig.*;
import org.postgresql.Driver;

public class ConnectionSql {
    	private static final Logger logger = LoggerFactory.getLogger(ConnectionSql.class);

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e1) {
            logger.error("Don't have a driver for db!",e1);
        }
        return DriverManager.getConnection(URL_JDBC, USER, PASSWORD);
}
}
