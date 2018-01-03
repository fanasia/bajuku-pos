package com.bajuku.pos.repository;

import com.bajuku.pos.model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private Connection conn= null;
    private PreparedStatement stmt=null;
    private Statement log=null;
    private String sql=null;

    private ArrayList<ProductModel> getObject(ResultSet rs) throws SQLException{
        ProductModel model;
        ArrayList<ProductModel> models= new ArrayList<ProductModel>();

        while (rs.next()){
            model= new ProductModel(rs.getInt("id"),
                    rs.getString("product_fullname"),
                    rs.getInt("category_id"),
                    rs.getString("category_fullname"),
                    rs.getFloat("product_price"),
                    rs.getInt("stock"),
                    rs.getString("product_mapping"));
            models.add(model);
        }

        return models;
    }

    public ArrayList<ProductModel> getAllProduct(int limit, int page) throws SQLException{
        conn= Dbconnection.createConnection();
        ArrayList<ProductModel> productList;
        sql = "SELECT * FROM product_tb pr " +
                "JOIN categories_tb ca ON pr.category_id= ca.id " +
                "LIMIT ? OFFSET ? * ?";

        stmt= conn.prepareStatement(sql);
        stmt.setInt(1, limit);
        stmt.setInt(2, page);
        stmt.setInt(3, limit);
        ResultSet rs= stmt.executeQuery();
        productList= getObject(rs);

        rs.close();
        stmt.close();
        conn.close();
        return productList;
    }

    private boolean executeStatement(String batch) throws SQLException{
        if(!stmt.execute()){
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

    public int getCountProduct(String category, String name) throws SQLException{
        int count=0;
        conn= Dbconnection.createConnection();

        if(category==null&&name==null){
            sql= "SELECT count(*) as counter FROM product_tb pr JOIN categories_tb ca ON pr.category_id = ca.id";
            stmt= conn.prepareStatement(sql);
        }
        else if(name==null||name.equals("")){
            sql= "SELECT count(*) as counter FROM product_tb WHERE category_id=? ";
            stmt= conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(category));
        }
        else {
            sql = "SELECT count(*) AS counter FROM product_tb WHERE category_id=? AND product_fullname LIKE '%'|| ? ||'%'";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(category));
            stmt.setString(2, name);
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

    public boolean insertProduct(ProductModel model, int user_id) throws SQLException{
        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql="INSERT INTO product_tb (product_fullname, category_id, stock, product_price, product_mapping) " +
                "VALUES (?, ?, ?, ?, ?)";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, model.getFullname());
        stmt.setInt(2, model.getCategory_id());
        stmt.setInt(3, model.getStock());
        stmt.setFloat(4, model.getPrice());
        stmt.setString(5, model.getMapping());

        String batch= "INSERT INTO log_tb (id,action, alter_time, alter_description) VALUES ("+user_id+",'insert', CURRENT_TIMESTAMP ,'insert new product')";
        log= conn.createStatement();

        return executeStatement(batch);
    }

    public boolean deleteProduct(int id, int user_id) throws SQLException{
        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql="DELETE FROM product_tb WHERE id= ?";

        stmt= conn.prepareStatement(sql);
        stmt.setInt(1, id);

        String batch= "INSERT INTO log_tb (id,action, alter_time, alter_description) VALUES ("+user_id+",'delete', CURRENT_TIMESTAMP ,'delete product id: "+id+"')";
        log= conn.createStatement();

        return executeStatement(batch);
    }

    public boolean updateProduct(ProductModel model, int user_id) throws SQLException{
        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql="UPDATE product_tb SET " +
                "product_fullname= ?," +
                "category_id= ?," +
                "stock= ?," +
                "product_price= ?," +
                "product_mapping= ?" +
                "WHERE id= ?";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, model.getFullname());
        stmt.setInt(2, model.getCategory_id());
        stmt.setInt(3, model.getStock());
        stmt.setFloat(4, model.getPrice());
        stmt.setString(5, model.getMapping());
        stmt.setInt(6, model.getId());

        String batch= "INSERT INTO log_tb (id,action, alter_time, alter_description) VALUES ("+user_id+",'update', CURRENT_TIMESTAMP ,'update product id: "+model.getId()+"')";
        log= conn.createStatement();

        return executeStatement(batch);
    }

    public ArrayList<ProductModel> getSearchProduct(String category, String name, int limit, int page) throws SQLException{
        conn= Dbconnection.createConnection();
        ArrayList<ProductModel> productList;
        if(name==null||name.equals("")) {
            sql = "SELECT * FROM product_tb pr JOIN categories_tb ca ON pr.category_id = ca.id " +
                    "WHERE pr.category_id=? LIMIT ? OFFSET ? * ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(category));
            stmt.setInt(2, limit);
            stmt.setInt(3, page);
            stmt.setInt(4, limit);
        }
        else {
            sql = "SELECT * FROM product_tb pr JOIN categories_tb ca ON pr.category_id = ca.id " +
                    "WHERE pr.category_id= ? AND pr.product_fullname LIKE '%'|| ? ||'%' LIMIT ? OFFSET ? * ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(category));
            stmt.setString(2, name);
            stmt.setInt(3, limit);
            stmt.setInt(4, page);
            stmt.setInt(5, limit);
        }
        ResultSet rs= stmt.executeQuery();
        productList= getObject(rs);

        rs.close();
        stmt.close();
        conn.close();
        return productList;
    }

    public ArrayList<ProductModel> findById(List<Integer> arr) throws SQLException{
        conn= Dbconnection.createConnection();
        ArrayList<ProductModel> cartList= new ArrayList<ProductModel>();
        ProductModel model;
        for (int id: arr) {
            sql= "SELECT * FROM product_tb pr JOIN categories_tb ca ON pr.category_id= ca.id WHERE pr.id=?";
            stmt= conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs= stmt.executeQuery();

            if(rs.next()){
                model= new ProductModel(rs.getInt("id"),
                        rs.getString("product_fullname"),
                        rs.getInt("category_id"),
                        rs.getString("category_fullname"),
                        rs.getFloat("product_price"),
                        rs.getInt("stock"),
                        rs.getString("product_mapping"));
                cartList.add(model);
            }
            rs.close();
            stmt.close();
        }

        conn.close();
        return cartList;
    }
}
