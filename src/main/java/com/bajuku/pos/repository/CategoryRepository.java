package com.bajuku.pos.repository;

import com.bajuku.pos.model.CategoryModel;
import com.bajuku.pos.model.ProductModel;
import com.bajuku.pos.model.UserModel;

import java.sql.*;
import java.util.ArrayList;

public class CategoryRepository {
    private Connection conn= null;
    private PreparedStatement stmt= null;
    private Statement log= null;
    private String sql= null;

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

    public ArrayList<CategoryModel> getAllCategories(int limit, int page) throws SQLException{
        conn= Dbconnection.createConnection();
        ArrayList<CategoryModel> categoryList;
        if(limit==0){
            sql= "SELECT * FROM categories_tb";
            stmt= conn.prepareStatement(sql);
        }
        else {
            sql = "SELECT * FROM categories_tb LIMIT ? OFFSET ? *?";
            stmt= conn.prepareStatement(sql);
            stmt.setInt(1, limit);
            stmt.setInt(2, page);
            stmt.setInt(3, limit);
        }

        ResultSet rs= stmt.executeQuery();
        categoryList= getObject(rs);

        rs.close();
        stmt.close();
        conn.close();
        return categoryList;
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
        else{
            conn.rollback();
            log.close();
            stmt.close();
            conn.close();
            return false;
        }
    }

    public int getCountCategory(String name) throws SQLException{
        int count=0;
        conn= Dbconnection.createConnection();
        if(name==null){
            sql="SELECT count(*) as counter FROM categories_tb";
            stmt= conn.prepareStatement(sql);
        }
        else {
            sql="SELECT count(*) as counter FROM categories_tb WHERE lower(category_fullname) LIKE '%'|| ? ||'%'";
            stmt= conn.prepareStatement(sql);
            stmt.setString(1, name);
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

    public boolean insertCategory(CategoryModel model, int user_id) throws SQLException{
        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql= "INSERT INTO categories_tb(category_fullname) VALUES (?)";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, model.getFullname());

        String batch="INSERT INTO log_tb (id,action, alter_time, alter_description) VALUES ("+user_id+",'insert', CURRENT_TIMESTAMP ,'insert new category')";
        log= conn.createStatement();

        return executeStatement(batch);
    }

    public boolean updateCategory(CategoryModel model, int user_id) throws SQLException{
        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql= "UPDATE categories_tb SET category_fullname=? WHERE id=? ";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, model.getFullname());
        stmt.setInt(2, model.getId());

        String batch="INSERT INTO log_tb (id,action, alter_time, alter_description) VALUES ("+user_id+",'update', CURRENT_TIMESTAMP ,'update category id:"+model.getId()+"')";
        log= conn.createStatement();

        return executeStatement(batch);
    }

    public boolean deleteCategory(int id, int user_id) throws SQLException{
        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql= "DELETE FROM categories_tb WHERE id=?";

        stmt= conn.prepareStatement(sql);
        stmt.setInt(1, id);

        String batch="INSERT INTO log_tb (id,action, alter_time, alter_description) VALUES ("+user_id+",'delete', CURRENT_TIMESTAMP ,'delete category id:"+id+"')";
        log= conn.createStatement();

        return executeStatement(batch);
    }

    public ArrayList<CategoryModel> getCategorySearch(String name,int page) throws SQLException{
        conn= Dbconnection.createConnection();
        ArrayList<CategoryModel> categoryList;
        sql="SELECT * FROM categories_tb WHERE lower(category_fullname) LIKE '%'|| ? ||'%' LIMIT 7 OFFSET ? * 7";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setInt(2, page);
        ResultSet rs= stmt.executeQuery();
        categoryList= getObject(rs);

        rs.close();
        stmt.close();
        conn.close();
        return categoryList;
    }

}
