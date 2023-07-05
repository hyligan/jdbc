package com.goit.dev10.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class UtilsDb {
    private UtilsDb() {
    }
    public static int getRandomInt(){
        return ThreadLocalRandom.current().nextInt();
    }

    public static String getRandomString(int l){
        String AlphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz";

        StringBuilder s = new StringBuilder(l);

        int i;

        for ( i=0; i<l; i++) {
            s.append(AlphaNumericStr.charAt(ThreadLocalRandom.current().nextInt(AlphaNumericStr.length())));
        }
        return s.toString();
    }

    public static Timestamp getRandomTimestamp(){
        return new Timestamp(
                ThreadLocalRandom.current()
                        .nextLong(new Date(0).getTime(), new Date().getTime())
        );
    }

    public static String getRandomLevel(){
        List<String> list = Arrays.asList("Trainee", "Junior", "Middle", "Senior");
        return list.get(ThreadLocalRandom.current().nextInt(4));
    }
}
