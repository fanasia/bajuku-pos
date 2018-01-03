package com.bajuku.pos.repository;

import com.bajuku.pos.model.UserModel;
import com.sun.istack.internal.Nullable;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.*;
import java.util.ArrayList;

public class UserRepository {
    private Connection conn= null;
    private PreparedStatement stmt=null;
    private Statement log=null;
    private String sql=null;

    private ArrayList<UserModel> getObject(ResultSet rs) throws SQLException{
        UserModel model;
        ArrayList<UserModel> models=new ArrayList<UserModel>();
        while (rs.next()){
            model=new UserModel(rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("user_fullname"),
                    rs.getTimestamp("log_time"),
                    rs.getString("user_role"));
            models.add(model);
        }
        return models;
    }

    public UserModel authenticateUser(String user, String password) throws SQLException{
        conn=Dbconnection.createConnection();
        UserModel model=null;
        sql= "SELECT * FROM user_tb WHERE username = ? AND password = md5(?)";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, user);
        stmt.setString(2, password);

        ResultSet rs= stmt.executeQuery();
        if(rs.next()){
            model= new UserModel(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("user_fullname"),
                    new Timestamp(System.currentTimeMillis()),
                    rs.getString("user_role"));
        }

        if(model!=null) {
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

    public int getCountUser(String role, String name) throws SQLException{
        int count=0;
        conn=Dbconnection.createConnection();

        if(role==null&&name==null) {
            sql = "SELECT count(id) as counter FROM user_tb";
            stmt= conn.prepareStatement(sql);
        }
        else if(name.equals("")){
            sql = "SELECT count(id) as counter FROM user_tb WHERE user_role=?";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1, role);
        }
        else {
            sql = "SELECT count(id) as counter FROM user_tb WHERE user_role=? AND lower(user_fullname) LIKE '%'|| ? ||'%'";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1, role);
            stmt.setString(2, name);
        }
        ResultSet rs= stmt.executeQuery();

        if(rs.next()){
            count=rs.getInt("counter");
        }
        rs.close();
        stmt.close();
        conn.close();
        return count;
    }

    public ArrayList<UserModel> getAllUser(int page) throws SQLException{
        conn=Dbconnection.createConnection();
        ArrayList<UserModel> userList;
        sql="SELECT * FROM user_tb LIMIT 10 OFFSET ?";

        stmt= conn.prepareStatement(sql);
        stmt.setInt(1, page);
        ResultSet rs=stmt.executeQuery();

        userList= getObject(rs);
        rs.close();
        stmt.close();
        conn.close();
        return userList;
    }

    private boolean executeStatement(String batch) throws SQLException{
        if(!stmt.execute()) {
            log.execute(batch);
            conn.commit();
            log.close();
            stmt.close();
            conn.close();
            return true;
        }
        else {
            conn.rollback();
            log.close();
            stmt.close();
            conn.close();
            return false;
        }
    }

    public boolean insertUser(UserModel model, int user_id) throws SQLException{
        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql="INSERT INTO user_tb (username, user_fullname, password, log_time, user_role) " +
                "VALUES (?, ?, md5(?), CURRENT_TIMESTAMP , ?)";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, model.getUsername());
        stmt.setString(2, model.getFullname());
        stmt.setString(3, model.getPassword());
        stmt.setString(4, model.getUser_role());

        String batch= "INSERT INTO log_tb (id, action, alter_time, alter_description) VALUES ("+user_id+", 'insert', current_timestamp, 'insert new user')";
        log= conn.createStatement();

        return executeStatement(batch);
    }

    public boolean deleteUser(int id, int user_id) throws SQLException{
        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql="DELETE  FROM user_tb WHERE id= ?";

        stmt= conn.prepareStatement(sql);
        stmt.setInt(1, id);

        String batch= "INSERT INTO log_tb (id, action, alter_time, alter_description) VALUES ("+user_id+", 'delete', current_timestamp, 'delete user id: "+id+"')";
        log= conn.createStatement();

        return executeStatement(batch);
    }

    public boolean updateUser(UserModel model, int user_id) throws SQLException{
        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql="UPDATE user_tb " +
                "SET username= ?," +
                "log_time= CURRENT_TIMESTAMP ," +
                "user_fullname= ?," +
                "user_role= ?" +
                "WHERE id= ? ";

        stmt=conn.prepareStatement(sql);
        stmt.setString(1, model.getUsername());
        stmt.setString(2, model.getFullname());
        stmt.setString(3, model.getUser_role());
        stmt.setInt(4, model.getId());

        String batch= "INSERT INTO log_tb (id, action, alter_time, alter_description) VALUES ("+user_id+", 'update', current_timestamp, 'update user id: "+model.getId()+"')";
        log= conn.createStatement();

        return executeStatement(batch);
    }

    public boolean changePassword(String password, int id, int user_id) throws SQLException{
        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql="UPDATE user_tb SET password= md5(?) WHERE id=?";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, password);
        stmt.setInt(2, id);

        String batch= "INSERT INTO log_tb (id, action, alter_time, alter_description) VALUES ('"+user_id+"', 'update', current_timestamp, 'change password user: "+id+"')";
        log= conn.createStatement();

        return executeStatement(batch);
    }

    public ArrayList<UserModel> getSearchUser(String role, String name, int page) throws SQLException{
        ArrayList<UserModel> userList;
        conn= Dbconnection.createConnection();
        if(name.equals("")){
            sql="SELECT * FROM user_tb WHERE user_role=? LIMIT 10 OFFSET ? *10";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1, role);
            stmt.setInt(2,page);
        }
        else {
            sql = "SELECT * FROM user_tb WHERE user_role=? AND lower(user_fullname) LIKE '%'|| ? ||'%' LIMIT 10 OFFSET ? *10";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1, role);
            stmt.setString(2, name);
            stmt.setInt(3,page);
        }
        ResultSet rs= stmt.executeQuery();
        userList= getObject(rs);

        return userList;
    }
}
