package com.kubernetes.protocol.snapShot;

import com.kubernetes.base.SendMessageBase;
import com.kubernetes.entity.NameGenerate;
import com.kubernetes.protocol.base.ProtocolBase;
import com.kubernetes.service.SnapShotService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 删除快照
 */
public class DeleteSnapShot implements ProtocolBase {
    public static String url="deleteSnapShot";
    public static List<String> necessaryParams=new ArrayList<String>(){{
        add("userType");
        add("userId");
        add("imageName");
        add("tagName");
    }};

//            "userType:userId:imageName:tagName";
    private String request;
    private String userType;
    private String userId;
    private String courseId;
    private String imageName;
    private String tagName;
    private NameGenerate nameGenerate;

    public DeleteSnapShot(String request,String userType, String userId, String courseId, String imageName, String tagName) {
        this.request=request;
        this.userType = userType;
        this.userId = userId;
        this.courseId = courseId;
        this.imageName = imageName;
        this.tagName = tagName;
        this.nameGenerate=new NameGenerate(courseId,userType,userId,imageName);
    }

    public DeleteSnapShot(String userType, String userId, String imageName, String tagName) {
        this.userType = userType;
        this.userId = userId;
        this.imageName = imageName;
        this.tagName = tagName;
    }

    @Override
    public void runProtocol() {
        SnapShotService snapShotService=new SnapShotService();
        snapShotService.deleteSnapShot(nameGenerate,tagName);
        SendMessageBase sendMessageBase=new SendMessageBase();
        Map<String, Object> message=new HashMap<>();
        message.put("request",request);
        message.put("result",true);
        sendMessageBase.sendMessage(nameGenerate.getSendMessageKey(),message);
    }
}
