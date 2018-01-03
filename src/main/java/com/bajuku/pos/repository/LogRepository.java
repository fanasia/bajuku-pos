package com.bajuku.pos.repository;

import com.bajuku.pos.model.LogModel;

import java.sql.*;
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

    public ArrayList<LogModel> getAllLog(int page) throws SQLException{
        conn= Dbconnection.createConnection();
        ArrayList<LogModel> logList;
        sql= "SELECT * FROM log_tb ORDER BY alter_time DESC LIMIT 10 OFFSET ? *10";

        stmt= conn.prepareStatement(sql);
        stmt.setInt(1, page);
        ResultSet rs= stmt.executeQuery();
        logList= getObject(rs);

        return logList;
    }

    public int getLogCount(Timestamp begin, Timestamp end) throws SQLException{
        conn= Dbconnection.createConnection();
        int count=0;
        if(begin==null&&end==null){
            sql="SELECT count(*) as counter FROM log_tb";
            stmt= conn.prepareStatement(sql);
        }
        else {
            sql = "SELECT count(id) AS counter FROM log_tb WHERE alter_time>=? AND alter_time<=?";
            stmt= conn.prepareStatement(sql);
            stmt.setTimestamp(1, begin);
            stmt.setTimestamp(2, end);
        }
        ResultSet rs= stmt.executeQuery();

        if(rs.next()){
            count= rs.getInt("counter");
        }
        rs.close();
        stmt.close();
        conn.close();
        return count;
    }

    public ArrayList<LogModel> getSearchLog(Timestamp begin, Timestamp end, int page) throws SQLException{
        conn= Dbconnection.createConnection();
        ArrayList<LogModel> logList= null;
        sql= "SELECT * FROM log_tb WHERE alter_time>=? AND alter_time<=? LIMIT 10 OFFSET ? *10";

        stmt= conn.prepareStatement(sql);
        stmt.setTimestamp(1, begin);
        stmt.setTimestamp(2, end);
        stmt.setInt(3, page);
        ResultSet rs= stmt.executeQuery();
        logList= getObject(rs);

        return logList;
    }

}
