package com.kubernetes.config;


import com.kubernetes.util.YmlReader;

import java.util.LinkedHashMap;

public class HarborConfig {

//    public static String hostIp="121.36.99.173";
//    public static Integer port=2375;
//    public static String userName="admin";
//    public static String password="Harbor12345";

//    public static ProjectMetadata projectMetadata=new ProjectMetadata("true","true",
//            "true","low","true");;

    public static String hostIp="116.198.160.206";
    public static Integer port=8081;
    public static String userName="admin";
    public static String password="Harbor12345";

    public static class ProjectMetadata{
        public static  String projectPublic="true";
        public static  String enableContentTrust="true";
        public static  String preventVul="true";
        public static  String severity="low";
        public static  String autoScan="true";

        public ProjectMetadata(String projectPublic, String enableContentTrust, String preventVul, String severity, String autoScan) {
            ProjectMetadata.projectPublic = projectPublic;
            ProjectMetadata.enableContentTrust = enableContentTrust;
            ProjectMetadata.preventVul = preventVul;
            ProjectMetadata.severity = severity;
            ProjectMetadata.autoScan = autoScan;
        }
    }

}
