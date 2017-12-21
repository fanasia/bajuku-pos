package com.bajuku.pos.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Dbconnection {
    private static Connection conn=null;
    private static final String url="jdbc:postgresql://localhost:5432/bajuku_pos_db";
    private static final String USER="postgres";
    private static final String PASS="2017igi";

    public static Connection createConnection(){
        try {
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection(url,USER,PASS);
            System.out.println("Connection created");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fail creating connection");
        }
        return null;
    }

}
