package com.bajuku.pos.model;

public class ProductModel {
    private int id;
    private String fullname;
    private int category_id;
    private String category_name;
    private float price;
    private int stock;
    private String mapping;

    public ProductModel(){}

    public ProductModel(int id, String fullname, int category_id, String category_name, float price, int stock, String mapping){
        this.id= id;
        this.fullname= fullname;
        this.category_id= category_id;
        this.category_name= category_name;
        this.price= price;
        this.stock= stock;
        this.mapping= mapping;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }
}
