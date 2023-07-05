package com.goit.dev10;

import com.goit.dev10.configs.Database;
import com.goit.dev10.configs.JdbcPool;
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


//                Statement st = connection.createStatement();


//                logger.info("status: {}",st.execute("CREATE TABLE worker\n" +
//                        "(\n" +
//                        "    worker_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,\n" +
//                        "    name TEXT NOT NULL,\n" +
//                        "    birthday DATE,\n" +
//                        "    level TEXT, " +
//                        "    salary NUMERIC NOT NULL)"
//                        ));
//                logger.info("status: {}",st.execute("CREATE TABLE client\n" +
//                        "(\n" +
//                        "    client_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,\n" +
//                        "    name TEXT NOT NULL\n" +
//                        "        CONSTRAINT client_name_length CHECK (length(name) >= 2 AND length(name) <= 1000)\n" +
//                        ");"));
//
//
//
//                logger.info("status: {}",st.execute("INSERT INTO worker (name, birthday, level, salary)\n" +
//                        "VALUES\n" +
//                        "    ('Yan', '1996-02-13', 'Junior', 500),\n" +
//                        "    ('Oleg', '1999-11-24', 'Junior', 700),\n" +
//                        "    ('Masha', '1989-07-03', 'Middle', 1500),\n" +
//                        "    ('Lina', '1992-04-15', 'Middle', 2000),\n" +
//                        "    ('Nikolay', '2001-01-05', 'Middle', 200),\n" +
//                        "    ('Sergey', '2003-12-30', 'Trainee', 250),\n" +
//                        "    ('Anna', '1999-06-26', 'Senior', 5600),\n" +
//                        "    ('Evgeniy', '1994-08-29', 'Junior', 750),\n" +
//                        "    ('Georgiy', '1998-09-12', 'Middle', 2500),\n" +
//                        "    ('Viktoriya', '1996-04-28', 'Senior', 6000);"));
                List<Generator> generators = new LinkedList<>();
            JdbcPool pool = JdbcPool.POOL;
            while (generators.size()<1000){
                Generator generator = new Generator();
                generator.setConnection(pool);
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
