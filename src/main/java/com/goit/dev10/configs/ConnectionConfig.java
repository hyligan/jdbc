package com.goit.dev10.configs;

public class ConnectionConfig {
    private ConnectionConfig() {
    }

    public static final String URL_JDBC = "jdbc:postgresql://35.238.176.199:5432/shop";
    public static final String USER = "dev12";
    public static final String PASSWORD ="dev12thebest";
    public static final String TABLE_NAME = "consumers";
    public static final String CREATE_TABLE = "create table public."+TABLE_NAME+"\n" +
            "(\n" +
            "    consumer_id       bigint primary key,\n" +
            "    organization      boolean default false not null,\n" +
            "    organization_name text,\n" +
            "    gender            varchar(1),\n" +
            "    first_name        text,\n" +
            "    middle_inital     text,\n" +
            "    last_name         text,\n" +
            "    email_id          bigint                not null,\n" +
            "    auth_id           bigint                not null,\n" +
            "    phone_id          bigint                not null,\n" +
            "    address_id        bigint\n" +
            ");";

}
