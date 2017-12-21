package com.bajuku.pos.model;

import java.sql.Timestamp;

public class TransactionModel {
    private int id;
    private int user_id; // reference ke user
    private Timestamp trans_time;
    private float trans_value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Timestamp getTrans_time() {
        return trans_time;
    }

    public void setTrans_time(Timestamp trans_time) {
        this.trans_time = trans_time;
    }

    public float getTrans_value() {
        return trans_value;
    }

    public void setTrans_value(float trans_value) {
        this.trans_value = trans_value;
    }

    public int getTrans_quantity() {
        return trans_quantity;
    }

    public void setTrans_quantity(int trans_quantity) {
        this.trans_quantity = trans_quantity;
    }

    private int trans_quantity;



}
