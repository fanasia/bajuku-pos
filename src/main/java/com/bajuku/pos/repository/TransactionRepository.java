package com.bajuku.pos.repository;

import com.bajuku.pos.model.ProductModel;
import com.bajuku.pos.model.TransactionDetailModel;
import com.bajuku.pos.model.TransactionModel;
import com.bajuku.pos.model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionRepository {
    private Connection conn= null;
    private PreparedStatement stmt=null;
    private Statement log=null;
    private String sql, transaction;

    private ArrayList<TransactionModel> getObject(ResultSet rs) throws SQLException{
        TransactionModel model;
        ArrayList<TransactionModel> models=new ArrayList<TransactionModel>();
        while (rs.next()){
            model=new TransactionModel(
                    rs.getTimestamp("date"),
                    rs.getFloat("value"),
                    rs.getInt("quantity"),
                    rs.getFloat("discount")
            );
            models.add(model);
        }
        return models;
    }

    public ArrayList<TransactionDetailModel> getObjectDetails() throws SQLException{
        ArrayList<TransactionDetailModel> details= new ArrayList<TransactionDetailModel>();

        return details;
    }

    public ArrayList<TransactionModel> getAllTransactions() throws SQLException{
        conn=Dbconnection.createConnection();
        ArrayList<TransactionModel> transactionList;
        sql="SELECT date_trunc('day', transaction_time) as date, sum(transaction_quantity) as quantity, sum(transaction_value) as value, sum(discount) as discount " +
                "FROM transaction_tb " +
                "GROUP BY date " +
                "ORDER BY date DESC LIMIT 10";

        stmt= conn.prepareStatement(sql);
        ResultSet rs= stmt.executeQuery();
        transactionList= getObject(rs);

        rs.close();
        stmt.close();
        conn.close();
        return transactionList;
    }

    public ArrayList<TransactionModel> getWeeklyTransaction() throws SQLException{
        conn=Dbconnection.createConnection();
        ArrayList<TransactionModel> transactionList;
        sql="SELECT extract(WEEK FROM transaction_time) as date, sum(transaction_value), sum(transaction_quantity), sum(discount) " +
                "FROM transaction_tb " +
                "GROUP BY date " +
                "ORDER BY date DESC LIMIT 10";

        stmt= conn.prepareStatement(sql);
        ResultSet rs= stmt.executeQuery();
        transactionList= getObject(rs);

        return transactionList;
    }

    public ArrayList<TransactionModel> getMonthlyTransaction() throws SQLException{
        conn= Dbconnection.createConnection();
        ArrayList<TransactionModel> transactionList;
        sql="SELECT date_trunc('month', transaction_time) as date, sum(transaction_quantity), sum(transaction_value), sum(discount) " +
                "FROM transaction_tb " +
                "GROUP BY date " +
                "ORDER BY date LIMIT 10";

        stmt= conn.prepareStatement(sql);
        ResultSet rs= stmt.executeQuery();
        transactionList= getObject(rs);

        rs.close();
        stmt.close();
        conn.close();
        return transactionList;
    }

    public ArrayList<TransactionModel> getYearlyTransaction(){
        ArrayList<TransactionModel> transactionList= new ArrayList<TransactionModel>();

        return transactionList;
    }

    private float sumTransaction(ArrayList<ProductModel> products, List<Integer> quantity){
        float totalValue=0;
        for (int i = 0; i <quantity.size() ; i++) {
            totalValue+=(products.get(i).getPrice()*quantity.get(i));
        }
        return totalValue;
    }

    public boolean insertTransaction(ArrayList<ProductModel> products, List<Integer> quantity, String customer_id, int user_id) throws SQLException{
        int total=0, trans_id=-1;
        for (int qty: quantity) {
            total+=qty;
        }
        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql="INSERT INTO transaction_tb (user_id, transaction_time, transaction_value, transaction_quantity) " +
                "VALUES (?, CURRENT_TIMESTAMP, ?, ?)" +
                "RETURNING id";
        stmt= conn.prepareStatement(sql);
        stmt.setInt(1, user_id);
        stmt.setFloat(2, sumTransaction(products, quantity));
        stmt.setInt(3, total);
        ResultSet rs= stmt.executeQuery();

        if(rs.next()){
            trans_id= rs.getInt("id");
        }
        rs.close();
        if(trans_id<0){
            conn.rollback();
            return false;
        }
        stmt.close();

        if(!customer_id.equals("null")){
            for(int i=0;i<products.size();i++) {
                transaction = "INSERT INTO transaction_details(trans_id, customer_id, product_id, product_fullname, product_quantity, product_price)" +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(transaction);
                stmt.setInt(1, trans_id);
                stmt.setInt(2, Integer.parseInt(customer_id));
                stmt.setInt(3, products.get(i).getId());
                stmt.setString(4, products.get(i).getFullname());
                stmt.setInt(5, quantity.get(i));
                stmt.setFloat(6, products.get(i).getPrice());
                stmt.addBatch();
            }
        }
        else {
            for(int i=0;i<products.size();i++) {
                transaction = "INSERT INTO transaction_details(trans_id, product_id, product_fullname, product_quantity, product_price)" +
                        "VALUES (?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(transaction);
                stmt.setInt(1, trans_id);
                stmt.setInt(2, products.get(i).getId());
                stmt.setString(3, products.get(i).getFullname());
                stmt.setInt(4, quantity.get(i));
                stmt.setFloat(5, products.get(i).getPrice());
                stmt.addBatch();
            }
        }
        int[] status = stmt.executeBatch();
        if(Arrays.asList(status).indexOf(-1)>=0){
            conn.rollback();
            return false;
        }

        log= conn.createStatement();
        log.execute("INSERT INTO log_tb(id, action, alter_time, alter_description) VALUES ('"+user_id +"', 'transaction', CURRENT_TIMESTAMP , 'new transaction id:"+trans_id+"')");
        conn.commit();

        return true;
    }

}