package com.bms.model;

public class User {


    private String loginId;
    private String loginPwd;
    private String pwdComfirm;
    private String name;
    private String address;
    private String phone;
    private String mail;
    private String birthday;
    private String getRegisterIp;
    private String registerTime;
    public User(){}
    public User(String loginId,String loginPwd){
        this.loginId = loginId;
        this.pwdComfirm = this.loginPwd = loginPwd;
    }
    public User(User user){
        this.loginId = user.getLoginId();
        this.loginPwd = user.getLoginPwd();
        this.pwdComfirm = user.getPwdComfirm();
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getPwdComfirm() {
        return pwdComfirm;
    }

    public void setPwdComfirm(String pwdComfirm) {
        this.pwdComfirm = pwdComfirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGetRegisterIp() {
        return getRegisterIp;
    }

    public void setGetRegisterIp(String getRegisterIp) {
        this.getRegisterIp = getRegisterIp;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }
}
