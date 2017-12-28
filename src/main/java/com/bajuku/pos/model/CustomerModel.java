package com.bajuku.pos.model;

public class CustomerModel {
    private int id;
    private String email;
    private String phone;
    private String fullname;
    private int points;

    public CustomerModel(){}

    public CustomerModel(int id, String email, String phone, String fullname, int points){
        this.id=id;
        this.email=email;
        this.phone=phone;
        this.fullname=fullname;
        this.points=points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
