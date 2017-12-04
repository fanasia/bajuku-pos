package com.bajuku.pos.repository;

import com.bajuku.pos.model.LogModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LogRepository {
    private Connection conn= null;
    private PreparedStatement stmt=null;
    private String sql=null;

    private ArrayList<LogModel> getObject(ResultSet rs) throws SQLException {
        LogModel model;
        ArrayList<LogModel> models= new ArrayList<LogModel>();

        while (rs.next()){
            model= new LogModel();
            model.setId(rs.getInt("id"));
            model.setAction(rs.getString("action"));
            model.setTime(rs.getTimestamp("alter_time"));
            model.setDesc(rs.getString("alter_description"));
            models.add(model);
        }

        return models;
    }

    public ArrayList<LogModel> getAllLog() throws SQLException{
        conn= Dbconnection.createConnection();
        ArrayList<LogModel> logList= null;
        sql= "SELECT * FROM log_tb LIMIT 10";

        stmt= conn.prepareStatement(sql);
        ResultSet rs= stmt.executeQuery();
        logList= getObject(rs);

        return logList;
    }

}
