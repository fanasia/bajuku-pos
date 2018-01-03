package com.bajuku.pos.model;

public class TransactionDetailModel {
    private int id;
    private int customer_id;
    private int product_id;
    private String product_fullname;
    private int quantity;
    private float price;

    public TransactionDetailModel(){}

    public TransactionDetailModel(int id, int customer_id, int product_id, String product_fullname, int quantity, float price){
        this.id= id;
        this.customer_id= customer_id;
        this.product_id= product_id;
        this.product_fullname= product_fullname;
        this.quantity= quantity;
        this.price= price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_fullname() {
        return product_fullname;
    }

    public void setProduct_fullname(String product_fullname) {
        this.product_fullname = product_fullname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
