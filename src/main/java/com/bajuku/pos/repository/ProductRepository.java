package com.bajuku.pos.repository;

import com.bajuku.pos.model.ProductModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductRepository {
    private Connection conn= Dbconnection.createConnection();
    private PreparedStatement stmt=null;
    private String sql=null;

    private ArrayList<ProductModel> getObject(ResultSet rs) throws SQLException{
        ProductModel model;
        ArrayList<ProductModel> models= new ArrayList<ProductModel>();

        while (rs.next()){
            model= new ProductModel();
            model.setId(rs.getInt("id"));
            model.setFullname(rs.getString("product_fullname"));
            model.setCategory_id(rs.getInt("category_id"));
            model.setPrice(rs.getFloat("product_price"));
            model.setStock(rs.getInt("stock"));
            model.setMapping(rs.getString("product_mapping"));
            models.add(model);
        }

        return models;
    }

    public ArrayList<ProductModel> getAllProduct() throws SQLException{
        ArrayList<ProductModel> productList;
        sql="SELECT * FROM product_tb";

        stmt= conn.prepareStatement(sql);
        ResultSet rs= stmt.executeQuery();
        productList= getObject(rs);

        return productList;
    }

    private boolean executeStatement() throws SQLException{
        if(stmt.execute()){
            conn.commit();
            stmt.close();
            conn.close();
            return true;
        }
        else {
            conn.rollback();
            stmt.close();
            conn.close();
            return false;
        }
    }

    public boolean insertProduct(ProductModel model) throws SQLException{
        conn.setAutoCommit(false);
        sql="INSERT INTO product_tb (product_fullname, category_id, stock, product_price, product_mapping) " +
                "VALUES (?, ?, ?, ?, ?)";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, model.getFullname());
        stmt.setInt(2, model.getCategory_id());
        stmt.setInt(3, model.getStock());
        stmt.setFloat(4, model.getPrice());
        stmt.setString(5, model.getMapping());

        return executeStatement();
    }

    public boolean deleteProduct(int id) throws SQLException{
        conn.setAutoCommit(false);
        sql="DELETE FROM product_tb WHERE id= ?";

        stmt= conn.prepareStatement(sql);
        stmt.setInt(1, id);
        return executeStatement();
    }

    public boolean updateProduct(ProductModel model) throws SQLException{
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

        return executeStatement();
    }
}
