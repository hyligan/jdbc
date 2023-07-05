package com.goit.dev10;

import com.goit.dev10.configs.Database;
import com.goit.dev10.configs.JdbcPool;
import com.goit.dev10.repo.WorkerRepo;
import com.goit.dev10.service.Generator;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

import static com.goit.dev10.configs.ConnectionConfig.CREATE_TABLE;
import static com.goit.dev10.configs.ConnectionConfig.TABLE_NAME;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        BasicConfigurator.configure();
        WorkerRepo workerRepo = new WorkerRepo();
        logger.info("worker {}",workerRepo.findById(413L).get());
        try(Connection connection = JdbcPool.POOL.getConnectionFromPool();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT table_name\n" +
                    "FROM INFORMATION_SCHEMA.TABLES\n" +
                    "WHERE table_type = 'BASE TABLE'");
            Set<String> tables = new LinkedHashSet<>();
            while (resultSet.next()) {
              tables.add(resultSet.getString("table_name"));
            }

            if(!tables.contains(TABLE_NAME)) {
                logger.info("creating table");
                logger.info("status: {}",statement.execute(CREATE_TABLE));
            }

            List<Generator> generators = new LinkedList<>();
            while (generators.size()<2){
                Generator generator = new Generator();
                generators.add(generator);
                generator.start();
            }
        }catch (org.postgresql.util.PSQLException e){
            logger.error("error: ", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Map<String,String>> resultSetToHashMap(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        List<Map<String,String>> resp = new LinkedList<>();
        while (rs.next()) {
            Map<String,String> row = new LinkedHashMap<>();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                row.put(md.getColumnName(i), rs.getString(i));
            }
            resp.add(row);
        }
        return resp;
    }
}
