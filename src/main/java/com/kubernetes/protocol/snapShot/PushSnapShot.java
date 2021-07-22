package com.kubernetes.protocol.snapShot;


import com.kubernetes.base.SendMessageBase;
import com.kubernetes.entity.NameGenerate;
import com.kubernetes.protocol.base.ProtocolBase;
import com.kubernetes.service.SnapShotService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上传快照
 */
public class PushSnapShot implements ProtocolBase {
    public static String url="pushSnapShot";
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

    public PushSnapShot(String request,String userType, String userId, String courseId, String imageName,String tagName) {
        this.request = request;
        this.userType = userType;
        this.userId = userId;
        this.courseId = courseId;
        this.imageName = imageName;
        this.tagName = tagName;
        this.nameGenerate=new NameGenerate(courseId,userType,userId,imageName);
    }


    @Override
    public void runProtocol() throws InterruptedException, IOException {
        SnapShotService snapShotService=new SnapShotService();
        snapShotService.pushSnapShot(nameGenerate,tagName);
        SendMessageBase sendMessageBase=new SendMessageBase();
        Map<String, Object> message=new HashMap<>();
        message.put("request",request);
        message.put("result",true);
        sendMessageBase.sendMessage(nameGenerate.getSendMessageKey(),message);
    }
}
