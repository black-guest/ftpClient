package com.kubernetes.config;

import com.kubernetes.util.YmlReader;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class KubernetesConfig {
    private Boolean balance;
    private String serverIp;
    private Integer serverPort;
    private HashMap<String,String> ipTabels;

    /**
     *
     */
    public KubernetesConfig(){
        LinkedHashMap config= new YmlReader().readYml("kubernetes/kubernetes.yml");
        LinkedHashMap kubernetes=(LinkedHashMap) config.get("kubernetes");
        LinkedHashMap serverApi=(LinkedHashMap) kubernetes.get("serverApi");

        this.ipTabels=new HashMap<>();
        this.serverIp=(String) serverApi.get("ip");
        this.serverPort=(Integer) serverApi.get("port");
        this.balance=(Boolean) kubernetes.get("balance");
        String[] iptabels=((String) kubernetes.get("ipTabels")).split(",");
        for(String s:iptabels){
            this.ipTabels.put(s.split("/")[0].trim(),s.split("/")[1].trim());
        }
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public HashMap<String, String> getIpTabels() {
        return ipTabels;
    }

    public void setIpTabels(HashMap<String, String> ipTabels) {
        this.ipTabels = ipTabels;
    }

    public Boolean getBalance() {
        return balance;
    }

    public void setBalance(Boolean balance) {
        this.balance = balance;
    }
}
