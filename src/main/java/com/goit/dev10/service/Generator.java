package com.goit.dev10.service;

import com.goit.dev10.configs.JdbcPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

import static com.goit.dev10.utils.UtilsDb.*;

public class Generator extends Thread {
    Connection connection;
    JdbcPool jdbcPool;

    public void setConnection(JdbcPool pool) throws InterruptedException {
        this.connection = pool.getConnectionFromPool();
        this.jdbcPool = pool;
    }

    private static final Logger logger = LoggerFactory.getLogger(Generator.class);

    @Override
    public void run() {
        int i = ThreadLocalRandom.current().nextInt(100,5000);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO worker (name, birthday, level, salary)\n" +
                    "VALUES\n" +
                    "    (?, ?, ?, ?)");
            for(int j=0;j<i;j++){
                preparedStatement.setString(1, getRandomString(255));
                preparedStatement.setTimestamp(2, getRandomTimestamp());
                preparedStatement.setString(3, getRandomLevel());
                preparedStatement.setInt(4, getRandomInt());
//                logger.info("sql {}", preparedStatement);
                preparedStatement.execute();
            }
            jdbcPool.freeConnection(connection);
        } catch (SQLException e) {
            logger.error("error ",e);
        }

    }
}
