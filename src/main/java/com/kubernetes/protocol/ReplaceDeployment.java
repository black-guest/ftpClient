package com.kubernetes.protocol;

import com.alibaba.fastjson.JSONObject;
import com.kubernetes.base.SendMessageBase;
import com.kubernetes.entity.NameGenerate;
import com.kubernetes.protocol.base.ProtocolBase;
import com.kubernetes.service.DockerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplaceDeployment implements ProtocolBase {
    public static String url="replaceDeployment";
    public static List<String> necessaryParams=new ArrayList<String>(){{
        add("userType");
        add("userId");
        add("imageName");
        add("tagName");
    }};
    private String request;
    private String userType;
    private String userId;
    private String courseId;
    private String imageName;
    private String tagName;
    private NameGenerate nameGenerate;

    public ReplaceDeployment(String request,String userType, String userId, String courseId,
                             String imageName,String tagName) {
        this.request=request;
        this.userType = userType;
        this.userId = userId;
        this.courseId = courseId;
        this.imageName = imageName;
        this.tagName = tagName;
        this.nameGenerate=new NameGenerate(courseId,userType,userId,imageName);
    }

    @Override
    public void runProtocol() throws InterruptedException, IOException {
        DockerService dockerService=new DockerService();
        Map<String,Object> message=dockerService.replaceDeployment(nameGenerate,tagName);
        SendMessageBase sendMessageBase=new SendMessageBase();
        message.put("request",request);
        sendMessageBase.sendMessage(nameGenerate.getSendMessageKey(),message);
    }
}
