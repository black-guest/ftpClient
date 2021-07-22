package com.kubernetes.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HarborTag {
    private String digest;
    private String name;
    private Long size;
    private String architecture;
    private String os;
    private String dockerVersion;
    private String author;
    private String created;
    private Date createdDate;
//    private Object config;
//    private Object signature;
//    private Object scanOverview;
//    private List<HarborLabel> labels;

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDockerVersion() {
        return dockerVersion;
    }

    public void setDockerVersion(String dockerVersion) {
        this.dockerVersion = dockerVersion;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreated() {
        return created;
    }

    public Date getCreatedDate(){
        return createdDate;
    }

    public void setCreated(String created) {
        this.created = created;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSXXX");
        try {
            this.createdDate = sdf1.parse(created);//拿到Date对象
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public Object getConfig() {
//        return config;
//    }
//
//    public void setConfig(Object config) {
//        this.config = config;
//    }
//
//    public Object getSignature() {
//        return signature;
//    }
//
//    public void setSignature(Object signature) {
//        this.signature = signature;
//    }
//
//    public Object getScanOverview() {
//        return scanOverview;
//    }
//
//    public void setScanOverview(Object scanOverview) {
//        this.scanOverview = scanOverview;
//    }
//
//    public List<HarborLabel> getLabels() {
//        return labels;
//    }
//
//    public void setLabels(List<HarborLabel> labels) {
//        this.labels = labels;
//    }

    @Override
    public String toString() {
        return "HarborTag{" +
                "digest='" + digest + '\'' +
                ", name='" + name + '\'' +
                ", size=" + size + '\'' +
//                ", architecture='" + architecture + '\'' +
                ", os='" + os + '\'' +
                ", dockerVersion='" + dockerVersion + '\'' +
                ", author='" + author + '\'' +
                ", created='" + created + '\'' +
//                ", config=" + config +
//                ", signature=" + signature +
//                ", scanOverview=" + scanOverview +
//                ", labels=" + labels +
                '}';
    }
}
