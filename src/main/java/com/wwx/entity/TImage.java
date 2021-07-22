package com.wwx.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class TImage implements Serializable {

//    @ApiModelProperty(value="主键imageId",name="主键imageId")
//    @TableId(value = "imageId", type = IdType.AUTO)
    private Long imageId;

//    @ApiModelProperty(value="镜像名称",name="imageName")
//    @TableField(value = "imageName")
    private String imageName;

//    @ApiModelProperty(value="课程ID",name="courseId")
//    @TableField(value = "courseId")
    private String courseId;

//    @ApiModelProperty(value="用户类型",name="userType")
//    @TableField(value = "userType")
    private String userType;

//    @ApiModelProperty(value="用户ID",name="userId")
//    @TableField(value = "userId")
    private String userId;

//    @ApiModelProperty(value="镜像Ip",name="imageIp")
//    @TableField(value = "imageIp")
    private String imageIp;

//    @ApiModelProperty(value="网络地址",name="network")
//    @TableField(value = "network")
    private String network;

    public TImage(String imageName, String courseId, String userType,String userId, String imageIp,String network) {
        this.imageName = imageName;
        this.courseId = courseId;
        this.userId = userId;
        this.imageIp = imageIp;
        this.network = network;
        this.userType = userType;
    }

    public TImage(){

    }
}