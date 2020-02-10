package com.example.msafi.techgaa;

public class User {
    public String firstname;
    public String lastname;
    public String email;
    public String village;
    public String id;
    public String phone;

    public User() {
    }

    public User(String firstname, String lastname, String email, String village, String id, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.village = village;
        this.id = id;
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
