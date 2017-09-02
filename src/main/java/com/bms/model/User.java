package com.bms.model;

public class User {


    private String username;
    private String password;
    private String passwordComfirm;
    private String telephone;
    private String email;
    private String address;
    private String name;
    public User(){}
    public User(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.passwordComfirm = user.getPasswordComfirm();
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordComfirm() {
        return passwordComfirm;
    }

    public void setPasswordComfirm(String passwordComfirm) {
        this.passwordComfirm = passwordComfirm;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
