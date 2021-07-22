package com.kubernetes.entity;

public class Host {
    private String ip;
    private Integer port;
    private String userName;
    private String password;

    public Host(){

    }


    public Host(String ip, Integer port, String userName, String password) {
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Host{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
