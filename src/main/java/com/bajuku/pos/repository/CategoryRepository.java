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
    private Connection conn= Dbconnection.createConnection();
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
        ArrayList<CategoryModel> categoryList;
        sql= "SELECT * FROM categories_tb";

        stmt= conn.prepareStatement(sql);
        ResultSet rs= stmt.executeQuery();
        categoryList= getObject(rs);

        return categoryList;
    }

    public boolean insertCategory(){

        return true;
    }

    public boolean updateCategory(){

        return true;
    }

    public boolean deleteCategory(){

        return true;
    }

}
