package com.kubernetes.entity;

public class NameGenerate {
    private String namespace;
    private String deploymentName;
    private String serviceName;
    private String portName;
    private String projectName;
    private String repoName;
    private String containerName;

    private String userType;
    private String userId;
    private String imageName;
    private String courseId;
    private String sendMessageKey;

    public NameGenerate(String courseId,String userType,String userId,String imageName) {
        if(courseId==null) {
            this.namespace = "user-vm";
            this.deploymentName = userType + userId + imageName;
            this.serviceName = userType + userId + imageName;
            this.portName = userType + userId + imageName;

            this.projectName = "user-image";
            this.repoName = userType + "-" + userId + "-" + imageName;

            this.containerName = userType+"-"+userId+"-"+imageName;

            this.courseId="0";
        }
        if(courseId!=null){
            this.namespace = "course" + courseId;
            this.deploymentName = userType + userId + imageName;
            this.serviceName = userType + userId + imageName;
            this.portName = userType + userId + imageName;

            this.projectName = "course-" + courseId;
            this.repoName = courseId + "-" + userType + "-" + userId + "-" + imageName;

            this.containerName=courseId+"-"+userType+"-"+userId+"-"+imageName;

            this.courseId=courseId;
        }

        this.userId=userId;
        this.userType=userType;
        this.imageName=imageName;
        this.sendMessageKey=courseId+"-"+userType+"-"+userId+"-"+imageName;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getDeploymentName() {
        return deploymentName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPortName() {
        return portName;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getContainerName() {
        return containerName;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getSendMessageKey() {
        return sendMessageKey;
    }

    public String getUserType() {
        return userType;
    }

    public String getUserId() {
        return userId;
    }

    public String getImageName() {
        return imageName;
    }
}
