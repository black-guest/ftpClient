package com.kubernetes;


import com.alibaba.fastjson.JSONObject;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.kubernetes.base.*;
import com.kubernetes.dao.VmDao;
import com.kubernetes.dao.impl.VmDaoImpl;
import com.kubernetes.entity.HarborTag;
import com.kubernetes.factory.ProtocolFactory;
import com.kubernetes.protocol.base.ProtocolBase;
import com.kubernetes.service.DockerService;
import com.kubernetes.service.SnapShotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


//chcp 65001
public class Test {
    private static final Logger LOGGER = LoggerFactory.getLogger("com.kubernetes.Test");
    public static void main(String[] args) throws IOException, InterruptedException {
        EnvironmentBase environmentBase = new EnvironmentBase();
        //运行docker客户端
        if (!environmentBase.execDockerDesktop()) {
            LOGGER.error("Docker Desktop 运行异常");
        }
        if (!environmentBase.checkNetwork()) {
            LOGGER.error("Docker Desktop 网络异常");
        }


        System.out.println(args.length);
        LOGGER.info("获取自定义协议参数  args.length : " + args.length);
        ProtocolFactory protocolFactory = new ProtocolFactory();
        if (args.length >= 1) {
            System.out.println(args[0]);
            LOGGER.info("获取自定义协议参数 args[0] : " + args[0]);
            Map<String, String> map = protocolFactory.getParams(args[0]);
            for (String key : map.keySet()) {
                System.out.println(key + "  " + map.get(key));
            }
            ProtocolBase protocolBase = protocolFactory.produce(args[0]);
            protocolBase.runProtocol();
        } else {
//        String url= "docker://deploymentFirstVisit/" +
//                "?userType=t&&userId=2&&imageName=hadoop&&courseId=1";

//        String url= "docker://replaceDeployment/" +
//                "?userType=t&&userId=1&&imageName=hadoop&&courseId=1&&tagName=test";

//        String url= "docker://restore/" +
//                "?userType=t&&userId=1&&imageName=hadoop&&courseId=1";

//        String url= "docker://getSnapshots/?userType=t&&userId=2&&imageName=hadoop&&courseId=2";

//        String url= "docker://deleteSnapShot/" +
//                "?userType=t&&userId=1&&imageName=hadoop&&courseId=1&&tagName=test";
//        String url= "docker://pushSnapShot?userType=t&&userId=2&&imageName=hadoop&&courseId=1&&tagName=te";


//            String image="116.198.160.206:8081/course-7/7-t-1-hadoop:test";
//            String cmd="cmd /c start cmd.exe /c docker pull "+image;
//            Runtime.getRuntime().exec(cmd);

//            DockerService dockerService=new DockerService();
//            dockerService.pullImage("");
        String url= "docker://deploymentFirstVisit?userType=t&&userId=1&&imageName=hadoop&&courseId=7";
        ProtocolBase protocolBase=protocolFactory.produce(url);
        protocolBase.runProtocol();


//        WinBase winBase=new WinBase();
//        System.out.println();
//        winBase.execCmdWithResult("docker pull 121.36.99.173:2375/base-image/ubuntu-clean:latest");
        }
    }

}