package com.bms.model;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    private Integer id;

    private String loginId;
    private String loginPwd;
    private String pwdComfirm;
    private String name;
    private String address;
    private String phone;
    private String mail;
    private String birthday;
    private String registerIp;
    private String registerTime;
    private String userRole;
    private Integer userState;
    public User(){}
    public User(String loginId,String loginPwd){
        this.loginId = loginId;
        this.pwdComfirm = this.loginPwd = loginPwd;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public User(User user){
        this.loginId = user.getLoginId();
        this.loginPwd = user.getLoginPwd();
        this.pwdComfirm =user.getPwdComfirm();
        this.name=user.getName();
        this.address = user.getAddress();
        this.phone=user.getPhone();
        this.mail=user.getMail();
        this.birthday=user.getBirthday();
        this.registerIp=user.getRegisterIp();
        this.registerTime=user.getRegisterTime();
        this.userRole=user.getUserRole();
        this.userState = user.getUserState();
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
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

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    public void setRegisterTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.registerTime = sdf.format(d);
    }
    public void setRegisterIp(HttpServletRequest request) {
        this.registerIp = getIpAddress(request);
    }

}
