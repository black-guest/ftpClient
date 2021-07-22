package com.kubernetes.entity;

import java.util.List;

public class HarborRepository {
    //The ID of repository.
    private Integer id;
    //The name of repository.
    private String name;
    //The project ID of repository.
    private Integer projectId;
    //The description of repository.
    private String description;
    //The pull count of repository.
    private Integer pullCount;
    //The star count of repository.
    private Integer starCount;
    //The tags count of repository.
    private Integer tagsCount;
    //The label list.
    private List<HarborLabel> labels;
    //The creation time of repository.
    private String creationTime;
    //The update time of repository.
    private String updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPullCount() {
        return pullCount;
    }

    public void setPullCount(Integer pullCount) {
        this.pullCount = pullCount;
    }

    public Integer getStarCount() {
        return starCount;
    }

    public void setStarCount(Integer starCount) {
        this.starCount = starCount;
    }

    public Integer getTagsCount() {
        return tagsCount;
    }

    public void setTagsCount(Integer tagsCount) {
        this.tagsCount = tagsCount;
    }

    public List<HarborLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<HarborLabel> labels) {
        this.labels = labels;
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

    @Override
    public String toString() {
        return "HarborRepository{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", projectId=" + projectId +
                ", description='" + description + '\'' +
                ", pullCount=" + pullCount +
                ", starCount=" + starCount +
                ", tagsCount=" + tagsCount +
                ", labels=" + labels +
                ", creationTime='" + creationTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
