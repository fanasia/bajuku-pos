package com.bajuku.pos.repository;

import com.bajuku.pos.model.CategoryModel;
import com.bajuku.pos.model.ProductModel;
import com.bajuku.pos.model.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryRepository {
    private Connection conn= null;
    private PreparedStatement stmt=null;
    private String sql=null;

    private ArrayList<CategoryModel> getObject(ResultSet rs) throws SQLException{
        CategoryModel model;
        ArrayList<CategoryModel> models= new ArrayList<CategoryModel>();

        while (rs.next()){
            model= new CategoryModel();
            model.setId(rs.getInt("id"));
            model.setFullname(rs.getString("category_fullname"));
            models.add(model);
        }

        return models;
    }

    public ArrayList<CategoryModel> getAllCategories() throws SQLException{
        conn= Dbconnection.createConnection();
        ArrayList<CategoryModel> categoryList=null;
        sql= "SELECT * FROM categories_tb LIMIT 10";

        stmt= conn.prepareStatement(sql);
        ResultSet rs= stmt.executeQuery();
        categoryList= getObject(rs);

        return categoryList;
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

    public boolean insertCategory(CategoryModel model) throws SQLException{
        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql= "INSERT INTO categories_tb(category_fullname) VALUES (?)";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, model.getFullname());

        return executeStatement();
    }

    public boolean updateCategory(){

        return true;
    }

    public boolean deleteCategory(){

        return true;
    }

}
