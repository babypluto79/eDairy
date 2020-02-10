package com.example.msafi.techgaa;

public class Farmer_User {
    public String firstname;
    public String lastname;
    public String village;
    public String phone;
    public String id;
    public String email;

    public Farmer_User() {
    }

    public Farmer_User(String firstname, String lastname, String village, String phone, String id, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.village = village;
        this.phone = phone;
        this.id = id;
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

