package com.goit.dev10.service;

import com.goit.dev10.configs.JdbcPool;
import com.goit.dev10.entities.Worker;
import com.goit.dev10.repo.WorkerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

import static com.goit.dev10.configs.ConnectionConfig.BATCH_SIZE;
import static com.goit.dev10.utils.UtilsDb.*;

public class Generator extends Thread {
   WorkerRepo workerRepo = new WorkerRepo();


    private static final Logger logger = LoggerFactory.getLogger(Generator.class);

    @Override
    public void run() {
        int i = ThreadLocalRandom.current().nextInt(100,5000);
        for(int j=0;j<i;j++) {
            try {
                workerRepo.save(
                        new Worker(
                                getRandomString(40),
                                new Date(getRandomTimestamp().getTime()),
                                getRandomLevel(),
                                BigDecimal.valueOf(getRandomInt())));
            } catch (SQLException e) {
                logger.error("error ", e);
            } catch (InterruptedException e) {
                logger.error("error: ", e);
            }
        }
    }
}
