package com.kubernetes.config;

import com.kubernetes.util.YmlReader;

import java.util.LinkedHashMap;

public class DockerConfig {
    private Integer exposePort;
    private String url;
    private Integer liveTime;
    private Integer delayTime;
    private Integer tagNum;
    private Boolean autoDelete;

    //等待docker desktop启动的时间
    public static Long waitTime=60000L;
    //java连接docker的端口号
    public static Integer dockerPort=2375;
    //docker的主机（客户端）
    public static String hostIp="127.0.0.1";

}
