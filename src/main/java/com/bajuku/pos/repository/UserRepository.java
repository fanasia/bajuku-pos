package com.bajuku.pos.repository;

import com.bajuku.pos.model.UserModel;
import com.sun.istack.internal.Nullable;

import java.sql.*;
import java.util.ArrayList;

public class UserRepository {
    private Connection conn= null;
    private PreparedStatement stmt=null;
    private Statement log=null;
    private String sql=null;

    private ArrayList<UserModel> getObject(ResultSet rs) throws SQLException{
        UserModel model=null;
        ArrayList<UserModel> models=new ArrayList<UserModel>();
        while (rs.next()){
            model=new UserModel();
            model.setId(rs.getInt("id"));
            model.setUsername(rs.getString("username"));
            model.setFullname(rs.getString("user_fullname"));
            model.setPassword(rs.getString("password"));
            model.setLog_time(rs.getTimestamp("log_time"));
            model.setUser_role(rs.getString("user_role"));
            models.add(model);
        }
        return models;
    }

    @Nullable
    public UserModel authenticateUser(String user, String password) throws SQLException{
        conn=Dbconnection.createConnection();
        UserModel model=new UserModel();
        sql= "SELECT * FROM user_tb WHERE username = ? AND password = md5(?)";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, user);
        stmt.setString(2, password);

        ResultSet rs= stmt.executeQuery();
        if(rs.next()){
            model.setId(rs.getInt("id"));
            model.setUsername(rs.getString("username"));
            model.setPassword(rs.getString("password"));
            model.setFullname(rs.getString("user_fullname"));
            model.setUser_role(rs.getString("user_role"));
            model.setLog_time(new Timestamp(System.currentTimeMillis()));
        }

        if(model.getId()!=0) {
            log= conn.createStatement();
            String batch = "INSERT INTO log_tb VALUES (" + model.getId() + ", 'login', CURRENT_TIMESTAMP , '" + model.getFullname() + " " + model.getUser_role() + "')";
            log.addBatch(batch);
            String update = "UPDATE user_tb SET log_time= CURRENT_TIMESTAMP WHERE id="+model.getId();
            log.addBatch(update);
            log.executeBatch();
        }

        rs.close();
        stmt.close();
        conn.close();

        return model;
    }

    public int getCountUser() throws SQLException{
        int count=0;
        conn=Dbconnection.createConnection();
        String sql= "SELECT count(id) as counter FROM user_tb";
        stmt= conn.prepareStatement(sql);
        ResultSet rs= stmt.executeQuery();

        if(rs.next()){
            count=rs.getInt("counter");
        }
        stmt.close();
        conn.close();
        return count;
    }

    public ArrayList<UserModel> getAllUser() throws SQLException{
        conn=Dbconnection.createConnection();
        ArrayList<UserModel> userList;
        sql="SELECT * FROM user_tb LIMIT 5";

        stmt= conn.prepareStatement(sql);
        ResultSet rs=stmt.executeQuery();

        userList= getObject(rs);
        rs.close();
        stmt.close();
        conn.close();
        return userList;
    }

    private boolean executeStatement() throws SQLException{
        if(stmt.execute()){
            conn.commit();
            stmt.close();
            conn.close();
            return true;
        }
        else{
            conn.rollback();
            stmt.close();
            conn.close();
            return false;
        }
    }

    public boolean insertUser(UserModel model) throws SQLException{
        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql="INSERT INTO user_tb (username, user_fullname, password, log_time, user_role) " +
                "VALUES (?, ?, md5(?), CURRENT_TIMESTAMP , ?)";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, model.getUsername());
        stmt.setString(2, model.getFullname());
        stmt.setString(3, model.getPassword());
        stmt.setString(4, model.getUser_role());

        return executeStatement();
    }

    public boolean deleteUser(int id) throws SQLException{
        conn.setAutoCommit(false);
        sql="DELETE  FROM user_tb WHERE id= ?";

        stmt= conn.prepareStatement(sql);
        stmt.setInt(1, id);
        return executeStatement();
    }

    public boolean updateUser(UserModel model) throws SQLException{
        conn.setAutoCommit(false);
        sql="UPDATE user_tb " +
                "SET username= ?," +
                "password= ?," +
                "log_time= CURRENT_TIMESTAMP ," +
                "user_fullname= ?," +
                "user_role= ?" +
                "WHERE user_id= ? ";

        stmt=conn.prepareStatement(sql);
        stmt.setString(1, model.getUsername());
        stmt.setString(2, model.getPassword());
        stmt.setString(3, model.getFullname());
        stmt.setString(4, model.getUser_role());
        stmt.setInt(5, model.getId());
        return executeStatement();
    }

}
