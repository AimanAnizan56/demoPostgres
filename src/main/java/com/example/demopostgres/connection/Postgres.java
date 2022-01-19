package com.example.demopostgres.connection;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;

public class Postgres {
    private static Connection conn = null;

    private static void initDb() {
        try {
            URI dbUri = null;
            if (System.getenv("DATABASE_URL") != null ) {
                dbUri = new URI(System.getenv("DATABASE_URL"));
            } else {
                // postgres://<username>:<password>@<host>:<port>/<dbname>
                dbUri = new URI("postgresql://postgres:system@localhost:5432/mvcdemo");
            }

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

            conn = DriverManager.getConnection(dbUrl, username, password);
        } catch (Exception err) {
            err.printStackTrace();
        }

    }

    public static Connection getConnection() {
        if(conn == null) {
            // initialize db
            initDb();
        }
        return conn;
    }

    public static void closeConnection() {
        try{
            if(conn.isClosed() || conn != null) {
                conn.close();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
