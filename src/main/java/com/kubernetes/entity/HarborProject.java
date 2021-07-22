package com.kubernetes.entity;

import java.util.Map;

public class HarborProject {
    private Long projectId;
    private Integer ownerId;
    private String name;
    private String creationTime;
    private String updateTime;
    private Boolean deleted;
    private String ownerName;
    private Boolean togglable;
    private Integer currentUserRoleId;
    private Integer repoCount;//镜像数
    private Integer chartCount;
    private Map<String,String> metadata;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Boolean getTogglable() {
        return togglable;
    }

    public void setTogglable(Boolean togglable) {
        this.togglable = togglable;
    }

    public Integer getCurrentUserRoleId() {
        return currentUserRoleId;
    }

    public void setCurrentUserRoleId(Integer currentUserRoleId) {
        this.currentUserRoleId = currentUserRoleId;
    }

    public Integer getRepoCount() {
        return repoCount;
    }

    public void setRepoCount(Integer repoCount) {
        this.repoCount = repoCount;
    }

    public Integer getChartCount() {
        return chartCount;
    }

    public void setChartCount(Integer chartCount) {
        this.chartCount = chartCount;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "{" +
                "projectId=" + projectId +
                ", ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", deleted=" + deleted +
                ", ownerName='" + ownerName + '\'' +
                ", togglable=" + togglable +
                ", currentUserRoleId=" + currentUserRoleId +
                ", repoCount=" + repoCount +
                ", chartCount=" + chartCount +
                ", metadata=" + metadata +
                '}';
    }
}
