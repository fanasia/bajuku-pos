package com.bajuku.pos.repository;

import com.bajuku.pos.model.CustomerModel;

import java.sql.*;
import java.util.ArrayList;

public class CustomerRepository {
    private Connection conn= null;
    private PreparedStatement stmt=null;
    private String sql=null;

    private ArrayList<CustomerModel> getObject(ResultSet rs) throws SQLException{
        CustomerModel model;
        ArrayList<CustomerModel> models= new ArrayList<CustomerModel>();

        while (rs.next()){
            model= new CustomerModel(rs.getInt("id"),
                    rs.getString("customer_email"),
                    rs.getString("customer_phone"),
                    rs.getString("customer_fullname"),
                    rs.getInt("points"));
            models.add(model);
        }
        return models;
    }

    private boolean executeStatement() throws SQLException{
        if(!stmt.execute()){
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

    public int getCountCustomer(String name) throws SQLException{
        int count=0;
        conn=Dbconnection.createConnection();
        if(name==null) {
            sql = "SELECT count(id) as counter FROM customer_tb";
            stmt= conn.prepareStatement(sql);
        }
        else {
            sql = "SELECT count(id) as counter FROM customer_tb WHERE lower(customer_fullname) LIKE '%'|| ? ||'%'";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1, name);
        }
        ResultSet rs= stmt.executeQuery();

        if(rs.next()){
            count=rs.getInt("counter");
        }
        stmt.close();
        conn.close();
        return count;
    }

    public ArrayList<CustomerModel> getAllCustomer() throws SQLException{
        conn = Dbconnection.createConnection();
        ArrayList<CustomerModel> customerList;
        sql="SELECT * FROM customer_tb LIMIT 10";

        stmt= conn.prepareStatement(sql);
        ResultSet rs= stmt.executeQuery();

        customerList = getObject(rs);
        rs.close();
        stmt.close();
        conn.close();
        return customerList;
    }

    public boolean insertCustomer(CustomerModel model) throws SQLException{
        System.out.println(model.getFullname());
        conn = Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql="INSERT INTO customer_tb (customer_email, customer_phone, customer_fullname, points) " +
                "VALUES (?, ?, ?, ?)";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, model.getEmail());
        stmt.setString(2, model.getPhone());
        stmt.setString(3, model.getFullname());
        stmt.setInt(4, model.getPoints());

        return executeStatement();
    }

    public boolean deleteCustomer(int id) throws SQLException{
        conn = Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql="DELETE FROM customer_tb WHERE id= ?";

        stmt= conn.prepareStatement(sql);
        stmt.setInt(1, id);
        return executeStatement();
    }

    public boolean updateCustomer(CustomerModel model) throws SQLException{
        conn = Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql="UPDATE customer_tb " +
                "SET customer_email = ?," +
                "customer_phone = ?," +
                "customer_fullname = ?," +
                "points = ?" +
                "WHERE id= ? ";

        stmt=conn.prepareStatement(sql);
        stmt.setString(1, model.getEmail());
        stmt.setString(2, model.getPhone());
        stmt.setString(3, model.getFullname());
        stmt.setInt(4, model.getPoints());
        stmt.setInt(5, model.getId());
        return executeStatement();
    }

    public ArrayList<CustomerModel> getSearchCustomer(String query, int page) throws SQLException{
        ArrayList<CustomerModel> customerList;
        conn= Dbconnection.createConnection();
        sql = "SELECT * FROM customer_tb WHERE lower(customer_fullname) LIKE '%'|| ? || '%' OR customer_phone LIKE '%'|| ? || '%' LIMIT 10 OFFSET ? *10";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, query);
        stmt.setString(2, query);
        stmt.setInt(3,page);

        ResultSet rs= stmt.executeQuery();
        customerList= getObject(rs);
        return customerList;
    }
}
