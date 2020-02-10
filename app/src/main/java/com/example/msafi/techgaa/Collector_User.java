package com.example.msafi.techgaa;

public class Collector_User {
    public String firstname;
    public String lastname;
    public String email;
    public String village;
    public String national_id;
    public String phone;

    public Collector_User() {
    }

    public Collector_User(String firstname, String lastname, String email, String village, String national_id, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.village = village;
        this.national_id = national_id;
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

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
