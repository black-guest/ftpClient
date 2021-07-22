package com.kubernetes.config;

import com.kubernetes.util.YmlReader;

import java.util.LinkedHashMap;

public class NetworkConfig {
    public static String subIp="10.244.0.0/16";
    public static String bridgeName="networkIpam";
    public static String[] k8sNetwork={"10.244.0.0"};
//    private String cidr;
//    private String[] k8sNetwork;
//
//    public NetworkConfig(){
//        LinkedHashMap config= new YmlReader().readYml("kubernetes/network.yml");
//        LinkedHashMap network=(LinkedHashMap) config.get("network");
//        cidr=(String) network.get("cidr");
//        k8sNetwork=((String) network.get("kubernetes-network")).split(",");
//    }
//
//    public String getCidr() {
//        return cidr;
//    }
//
//    public void setCidr(String cidr) {
//        this.cidr = cidr;
//    }
//
//    public String[] getK8sNetwork() {
//        return k8sNetwork;
//    }
//
//    public void setK8sNetwork(String[] k8sNetwork) {
//        this.k8sNetwork = k8sNetwork;
//    }

}
