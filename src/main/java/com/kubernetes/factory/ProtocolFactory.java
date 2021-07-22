package com.kubernetes.factory;

import com.kubernetes.config.UrlProtocolConfig;
import com.kubernetes.protocol.DeploymentFirstVisit;
import com.kubernetes.protocol.ReplaceDeployment;
import com.kubernetes.protocol.Restore;
import com.kubernetes.protocol.base.ProtocolBase;
import com.kubernetes.protocol.snapShot.DeleteSnapShot;
import com.kubernetes.protocol.snapShot.GetSnapShots;
import com.kubernetes.protocol.snapShot.PushSnapShot;
import com.kubernetes.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class ProtocolFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolFactory.class);

    public Map<String,String> getParams(String url){
        Map<String,String> result=new HashMap<>();

        String u=url.substring(UrlProtocolConfig.protocolName.length()+3);
        System.out.println(url.substring(UrlProtocolConfig.protocolName.length()+3));

        String[] u1=u.split("[?]");
        result.put("url",u1[0].replaceAll("/",""));
        String log="[url:"+result.get("url");

        if(u1.length>=2) {
            String[] params = u1[1].split("&&");
            for (int i = 0; i < params.length; i++) {
                System.out.println(params[i]);
                String[] item = params[i].split("=");
                result.put(item[0], item[1]);
                log += "," + item[0] + ":" + item[1];
            }
        }

        log+="]";
        LOGGER.info("UrlProtocol解析结果：" +log);
        return result;
    }

    public ProtocolBase produce(String url){
        Map<String,String> params=getParams(url);
        return produce(url,params);
    }

    public ProtocolBase produce(String request,Map<String,String> params) {
        String url= params.get("url");
//        System.out.println(url);
//        System.out.println(DeploymentFirstVisit.url);
//        System.out.println(url.equals(DeploymentFirstVisit.url));

        if(url.equals(DeleteSnapShot.url)){
            if(!checkParams(params,DeleteSnapShot.necessaryParams)){
                LOGGER.error("DeleteSnapShot 协议参数错误"+ StringUtil.getString(params));
            }
            return new DeleteSnapShot(request,
                    params.get("userType"),
                    params.get("userId"),
                    params.get("courseId"),
                    params.get("imageName"),
                    params.get("tagName"));
        }else if(url.equals(GetSnapShots.url)){
            if(!checkParams(params,GetSnapShots.necessaryParams)){
                LOGGER.error("GetSnapShots 协议参数错误"+StringUtil.getString(params));
            }
            return new GetSnapShots(request,
                    params.get("userType"),
                    params.get("userId"),
                    params.get("courseId"),
                    params.get("imageName"));
        }else if(url.equals(PushSnapShot.url)){
            if(!checkParams(params,DeleteSnapShot.necessaryParams)){
                LOGGER.error("PushSnapShot 协议参数错误"+StringUtil.getString(params));
            }
            return new PushSnapShot(request,
                    params.get("userType"),
                    params.get("userId"),
                    params.get("courseId"),
                    params.get("imageName"),
                    params.get("tagName"));
        }else if(url.equals(DeploymentFirstVisit.url)){
            if(!checkParams(params,DeploymentFirstVisit.necessaryParams)){
                LOGGER.error("DeploymentFirstVisit 协议参数错误"+StringUtil.getString(params));
            }
            return new DeploymentFirstVisit(request,
                    params.get("userType"),
                    params.get("userId"),
                    params.get("courseId"),
                    params.get("imageName"));

        }else if(url.equals(ReplaceDeployment.url)){
            if(!checkParams(params,DeleteSnapShot.necessaryParams)){
                LOGGER.error("ReplaceDeployment 协议参数错误"+StringUtil.getString(params));
            }
            return new ReplaceDeployment(request,
                    params.get("userType"),
                    params.get("userId"),
                    params.get("courseId"),
                    params.get("imageName"),
                    params.get("tagName"));
        }else if(url.equals(Restore.url)){
            if(!checkParams(params,Restore.necessaryParams)){
                LOGGER.error("Restore 协议参数错误"+StringUtil.getString(params));
            }
            return new Restore(request,
                    params.get("userType"),
                    params.get("userId"),
                    params.get("courseId"),
                    params.get("imageName"));
        }

        return null;
    }

    /**
     * 验证协议的参数 是否符合要求
     * @param params
     * @param necessaryParams
     * @return
     */
    public Boolean checkParams(Map<String,String> params,List<String> necessaryParams){
        for(String param:necessaryParams){
            if(!params.containsKey(param)){
                return false;
            }
        }
        return true;
    }
}
