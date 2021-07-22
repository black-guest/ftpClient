package com.kubernetes.protocol;


import com.alibaba.fastjson.JSONObject;
import com.kubernetes.base.DockerBase;
import com.kubernetes.base.SendMessageBase;
import com.kubernetes.entity.NameGenerate;
import com.kubernetes.protocol.base.ProtocolBase;
import com.kubernetes.service.DockerService;
import javafx.beans.binding.ObjectExpression;

import java.io.IOException;
import java.util.*;

//"docker://courseDeploymentFirstVisit/?
// userType=t&&userId=43&&imageName=hadoop&&courseId=23");
public class DeploymentFirstVisit implements ProtocolBase {
    public static String url="deploymentFirstVisit";
    private String request;
    private String userType;
    private String userId;
    private String imageName;
    private String courseId;
    private NameGenerate nameGenerate;
    public static List<String> necessaryParams=new ArrayList<String>(){{
        add("userType");
        add("userId");
        add("imageName");
    }};

    public DeploymentFirstVisit(String request,String userType, String userId, String courseId, String imageName) {
        this.request=request;
        this.userType = userType;
        this.userId = userId;
        this.imageName = imageName;
        this.courseId = courseId;
        this.nameGenerate=new NameGenerate(courseId,userType,userId,imageName);
    }


//    this.repoName = courseId + "-" + userType + "-" + userId + "-" + imageName;
    @Override
    public void runProtocol() throws InterruptedException, IOException {
        DockerService dockerService=new DockerService();
        Map<String,Object> message=dockerService.startContainer(nameGenerate);
        SendMessageBase sendMessageBase=new SendMessageBase();
        message.put("request",request);
        sendMessageBase.sendMessage(nameGenerate.getSendMessageKey(),message);
    }

    @Override
    public String toString() {
        return "DeploymentFirstVisit{" +
                "userType='" + userType + '\'' +
                ", userId='" + userId + '\'' +
                ", imageName='" + imageName + '\'' +
                ", courseId='" + courseId + '\'' +
                '}';
    }
}
