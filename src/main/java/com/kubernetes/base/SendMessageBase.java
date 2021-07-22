package com.kubernetes.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kubernetes.config.HarborConfig;
import com.kubernetes.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class SendMessageBase {
    private RestTemplate restTemplate =new RestTemplateBuilder().build();
    private String sendMessageBaseUrl="http://"+ ServerConfig.server
            +":"+ServerConfig.serverPort+"/api/";
    private static final Logger LOGGER = LoggerFactory.getLogger("com.kubernetes.base.SendMessageBase");


    public Boolean sendMessage(String key,Map<String,Object> message){
        String url=sendMessageBaseUrl+"dockerClient/sendMessage";
//        JSONObject params=new JSONObject(true);
//        params.put("message",message);
//        params.put("key",key);
//        System.out.println(params.toString());

//        HttpHeaders headers=new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        headers.add("Accept", "application/json");
//
//        HttpEntity<String> entity = new HttpEntity<String>(params.toJSONString(), headers);
//        restTemplate.postForEntity(url, entity, String.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        //封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        LOGGER.info("sendMessage方法中传输的url: "+url);
        LOGGER.info("sendMessage方法中传输数据: {key:"+key+"},"+ JSON.toJSONString(message));
        //添加请求的参数
        params.add("key", key);             //必传
        params.add("message", JSON.toJSONString(message));           //选传
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        restTemplate.postForEntity(url, requestEntity, String.class);


        return true;
    }
}
