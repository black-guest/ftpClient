package com.kubernetes.protocol.snapShot;


import com.kubernetes.base.SendMessageBase;
import com.kubernetes.entity.HarborTag;
import com.kubernetes.entity.NameGenerate;
import com.kubernetes.protocol.base.ProtocolBase;
import com.kubernetes.service.SnapShotService;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取快照
 */
public class GetSnapShots implements ProtocolBase {
    public static String url="getSnapshots";
    public static List<String> necessaryParams=new ArrayList<String>(){{
        add("userType");
        add("userId");
        add("imageName");
    }};
    private String request;
    private String userType;
    private String userId;
    private String courseId;
    private String imageName;
    private NameGenerate nameGenerate;

    public GetSnapShots(String request,String userType, String userId, String courseId, String imageName) {
        this.request = request;
        this.userId = userId;
        this.courseId = courseId;
        this.imageName = imageName;
        this.nameGenerate=new NameGenerate(courseId,userType,userId,imageName);
    }

    @Override
    public void runProtocol() {
        SnapShotService snapShotService=new SnapShotService();
        List<HarborTag> harborTags=snapShotService.getSnapShots(nameGenerate);
        SendMessageBase sendMessageBase=new SendMessageBase();
        Map<String, Object> message=new HashMap<>();
        message.put("request",request);
        message.put("result",harborTags);
        sendMessageBase.sendMessage(nameGenerate.getSendMessageKey(),message);
    }
}
