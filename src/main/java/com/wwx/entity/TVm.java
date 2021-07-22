package com.wwx.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class TVm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 虚拟机模板ID
     */
//    @TableId(value = "vmId",type = IdType.AUTO)
    private Long vmId;

    /**
     * 用户ID
     */
//    @TableField("userId")
    private Long userId;

    /**
     * 用户类型（学生为s,教师为t，管理员为a）
     */
//    @TableField("userType")
    private String userType;

    /**
     * 虚机名
     */
//    @TableField("vmName")
    private String vmName;

    /**
     * 虚机模板ID
     */
//    @TableField("templateId")
    private Long templateId;

    /**
     * 虚机描述
     */
//    @TableField("description")
    private String description;


    public TVm(Long userId, String userType, String vmName, Long templateId) {
        this.userId = userId;
        this.userType = userType;
        this.vmName = vmName;
        this.templateId = templateId;
    }

    public TVm(Long userId, String userType, String vmName, Long templateId,String description) {
        this.userId = userId;
        this.userType = userType;
        this.vmName = vmName;
        this.templateId = templateId;
        this.description=description;
    }
    public TVm(){

    }

}
