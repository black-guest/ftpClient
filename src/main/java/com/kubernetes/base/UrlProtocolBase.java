package com.kubernetes.base;

import com.kubernetes.Test;
import com.kubernetes.config.UrlProtocolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UrlProtocolBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlProtocolBase.class);
    //     "docker://fjent/?a=323&&jifne=fjeinsnf&&type=s"
    public Map<String,String> getParams(String url){
        Map<String,String> result=new HashMap<>();

        String u=url.substring(UrlProtocolConfig.protocolName.length()+3);
        System.out.println(url.substring(UrlProtocolConfig.protocolName.length()+3));

        String[] u1=u.split("[?]");
        String[] params=u1[1].split("&&");

        result.put("url",u1[0].replaceAll("/",""));
        String log="[url:"+u1[0];
        for(int i=0;i<params.length;i++){
            System.out.println(params[i]);
            String[] item=params[i].split("=");
            result.put(item[0],item[1]);
            log+=","+item[0]+":"+item[1];
        }
        log+="]";
        LOGGER.info("UrlProtocol解析结果：" +log);
        return result;
    }
}

