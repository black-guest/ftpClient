package com.kubernetes.config;

import com.kubernetes.entity.Host;
import com.kubernetes.util.YmlReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class HostsConfig {
    Map<String, Host> hosts=new HashMap<>();

    public HostsConfig(){
        LinkedHashMap config= new YmlReader().readYml("kubernetes/hosts.yml");
//        LinkedHashMap hosts =(LinkedHashMap)config.get("hosts");
        for(Object object:(ArrayList<Object>)config.get("hosts")){
            LinkedHashMap<String,Object> host=(LinkedHashMap) object;
            String ip=(String) host.get("ip");
            Integer port=(Integer) host.get("port");
            String userName=(String) host.get("userName");
            String password=(String) host.get("password");
            hosts.put(ip,new Host(ip,port,userName,password));
        }
    }

    public Host getHost(String ip){
        return hosts.get(ip);
    }
}
