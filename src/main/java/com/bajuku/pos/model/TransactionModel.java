package com.bajuku.pos.model;

import java.sql.Timestamp;

public class TransactionModel {
    private int id;
    private int user_id;
    private Timestamp time;
    private float value;
    private int quantity;
    private float discount;

    public TransactionModel(){}

    public TransactionModel(Timestamp time, float value, int quantity, float discount){
        this.time=time;
        this.value=value;
        this.quantity=quantity;
        this.discount=discount;
    }

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

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}