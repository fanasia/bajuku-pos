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
    private String sql;

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

    public ArrayList<TransactionDetailModel> getObjectDetails(int id) throws SQLException{
        conn= Dbconnection.createConnection();
        ArrayList<TransactionDetailModel> details= new ArrayList<TransactionDetailModel>();
        sql= "SELECT * FROM transaction_details WHERE trans_id= ?";

        stmt= conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs= stmt.executeQuery();

        while(rs.next()){
            TransactionDetailModel model= new TransactionDetailModel(
                    rs.getInt("trans_id"),
                    rs.getInt("customer_id"),
                    rs.getInt("product_id"),
                    rs.getString("product_fullname"),
                    rs.getInt("product_quantity"),
                    rs.getFloat("product_price")
            );
            details.add(model);
        }
        rs.close();
        stmt.close();
        conn.close();
        return details;
    }

    public ArrayList<TransactionModel> viewDailyTransactions(Timestamp time) throws SQLException{
        System.out.println(time);
        conn= Dbconnection.createConnection();
        ArrayList<TransactionModel> transactionList= new ArrayList<TransactionModel>();
        TransactionModel model;
        sql= "SELECT * FROM transaction_tb WHERE transaction_time BETWEEN ? AND TIMESTAMP '"+time+"' + INTERVAL '24 hours'";
        stmt= conn.prepareStatement(sql);

        stmt.setTimestamp(1, time);
        ResultSet rs= stmt.executeQuery();

        while (rs.next()){
            model= new TransactionModel();
            model.setId(rs.getInt("id"));
            model.setTime(rs.getTimestamp("transaction_time"));
            model.setValue(rs.getFloat("transaction_value"));
            model.setQuantity(rs.getInt("transaction_quantity"));
            model.setDiscount(rs.getFloat("discount"));
            transactionList.add(model);
        }

        rs.close();
        stmt.close();
        conn.close();
        return transactionList;
    }

    public int getCountTransaction(String interval, String time) throws SQLException{
        int count=0;
        conn= Dbconnection.createConnection();
        if(time==null) {
            sql = "SELECT count(tr.*) AS counter " +
                    "FROM (" +
                    "SELECT date_trunc(?, transaction_time) AS date " +
                    "FROM transaction_tb " +
                    "GROUP BY date " +
                    ") tr";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, interval);
        }
        else {
            //format time YYYY-MM-DD
            sql = "SELECT count(tr.*) AS counter " +
                    "FROM (" +
                    "SELECT date_trunc(?, transaction_time) AS date " +
                    "FROM transaction_tb " +
                    "WHERE to_char(transaction_time, ?) =? " +
                    "GROUP BY date " +
                    ") tr";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, interval);
            if(interval.equals("day")){
                stmt.setString(2, "yyyy-MM-dd");
            }
            else if(interval.equals("week")){
                stmt.setString(2, "yyyy-WW");
            }
            else if(interval.equals("month")){
                stmt.setString(2, "yyyy-MM");
            }
            stmt.setString(3, time);
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

    public ArrayList<TransactionModel> getAllTransactions(String interval, int page) throws SQLException{
        conn=Dbconnection.createConnection();
        ArrayList<TransactionModel> transactionList;
        sql = "SELECT date_trunc(?, transaction_time) AS date, sum(transaction_quantity) AS quantity, sum(transaction_value) AS value, sum(discount) AS discount " +
                "FROM transaction_tb " +
                "GROUP BY date " +
                "ORDER BY date DESC LIMIT 7 OFFSET ? *7";

        stmt= conn.prepareStatement(sql);
        stmt.setString(1, interval);
        stmt.setInt(2, page);
        ResultSet rs= stmt.executeQuery();
        transactionList= getObject(rs);

        rs.close();
        stmt.close();
        conn.close();
        return transactionList;
    }

    private float sumTransaction(ArrayList<ProductModel> products, List<Integer> quantity){
        float totalValue=0;
        for (int i = 0; i <quantity.size() ; i++) {
            totalValue+=(products.get(i).getPrice()*quantity.get(i));
        }
        return totalValue;
    }

    public boolean insertTransaction(ArrayList<ProductModel> products, List<Integer> quantity, String customer_id, String discount, String points, int user_id) throws SQLException{
        int total=0, trans_id=-1;
        String transaction, update;
        for (int qty: quantity) { total+=qty; }

        conn= Dbconnection.createConnection();
        conn.setAutoCommit(false);
        sql="INSERT INTO transaction_tb (user_id, transaction_time, transaction_value, transaction_quantity, discount) " +
                "VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?)" +
                "RETURNING id";
            stmt= conn.prepareStatement(sql);
            stmt.setInt(1, user_id);
            stmt.setFloat(2, sumTransaction(products, quantity));
            stmt.setInt(3, total);

            if(discount==null||discount.equals("")){
                stmt.setNull(4, Types.NUMERIC);
            }
            else {
                //replace (" ") from points & quantity
                stmt.setFloat(4, Float.parseFloat(discount.replaceAll("^\"|\"$", ""))*10);
            }

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
        System.out.println("header-update");

        transaction = "INSERT INTO transaction_details(trans_id, customer_id, product_id, product_fullname, product_quantity, product_price)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(transaction);
            stmt.setInt(1, trans_id);

        for(int i=0;i<products.size();i++) {
            if(customer_id==null||customer_id.equals("null")) {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            else {
                stmt.setInt(2, Integer.parseInt(customer_id));
            }
            stmt.setInt(3, products.get(i).getId());
            stmt.setString(4, products.get(i).getFullname());
            stmt.setInt(5, quantity.get(i));
            stmt.setFloat(6, products.get(i).getPrice());
            stmt.addBatch();
        }
            int[] status = stmt.executeBatch();
            if(Arrays.asList(status).indexOf(-1)>=0){
                conn.rollback();
                return false;
            }
        stmt.close();
        System.out.println("details-update");

        update= "UPDATE product_tb SET stock= (stock- ?) WHERE id= ?";
            stmt= conn.prepareStatement(update);

            for(int i=0;i<products.size();i++){
                stmt.setInt(1, quantity.get(i));
                stmt.setInt(2, products.get(i).getId());
                stmt.addBatch();
            }
            int[] status2 = stmt.executeBatch();
            if(Arrays.asList(status2).indexOf(-1)>=0){
                conn.rollback();
                return false;
            }
        stmt.close();
        System.out.println("product-update");

        //update customer points
        if(customer_id!=null) {
                update = "UPDATE customer_tb SET points= (points - ? + ?) WHERE id=?";
                stmt = conn.prepareStatement(update);
                if(discount==null){
                    stmt.setDouble(1, 0);
                }
                else {
                    stmt.setDouble(1, Double.parseDouble(discount.replaceAll("^\"|\"$", "")));
                }
                stmt.setDouble(2, Math.floor(Double.parseDouble(points.replaceAll("^\"|\"$", ""))));
                stmt.setInt(3, Integer.parseInt(customer_id));
            stmt.execute();
            stmt.close();
        }
        System.out.println("customer-update");

        log= conn.createStatement();
        log.execute("INSERT INTO log_tb(id, action, alter_time, alter_description) VALUES ('"+user_id +"', 'transaction', CURRENT_TIMESTAMP , 'new transaction id:"+trans_id+"')");
        conn.commit();
        System.out.println("logfile-update");

        return true;
    }

    public ArrayList<TransactionModel> getSearchTransaction(String interval, String time, int page) throws SQLException{
        conn= Dbconnection.createConnection();
        System.out.println(interval);
        ArrayList<TransactionModel> transactionList;

        sql = "SELECT date_trunc(?, transaction_time) AS date, sum(transaction_quantity) AS quantity, sum(transaction_value) AS value, sum(discount) AS discount " +
                "FROM transaction_tb " +
                "WHERE to_char(transaction_time, ?) = ? " +
                "GROUP BY date " +
                "ORDER BY date DESC LIMIT 7 OFFSET ? *7";

            stmt=conn.prepareStatement(sql);
            stmt.setString(1, interval);
            if(interval.equals("day")){
                stmt.setString(2, "yyyy-MM-dd");
            }
            else if(interval.equals("week")){
                stmt.setString(2, "yyyy-WW");
            }
            else if(interval.equals("month")){
                stmt.setString(2, "yyyy-MM");
            }
            stmt.setString(3, time);
            stmt.setInt(4, page);

        ResultSet rs= stmt.executeQuery();
        transactionList= getObject(rs);

        rs.close();
        stmt.close();
        conn.close();
        return transactionList;
    }
}