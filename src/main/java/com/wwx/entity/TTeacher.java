package com.wwx.entity;

import com.wwx.entity.pojo.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  教师表
 * @author wuweixu
 * @since 2019-08-12
 */
@Setter
@Getter
@ToString
public class TTeacher extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID(主键)
     */
//    @ApiModelProperty(value="主键teacherId",name="主键teacherId")
//    @TableId(value = "teacherId", type = IdType.AUTO)
    private Long teacherId;

    /**
     * 教师工号
     */
//    @ApiModelProperty(value="教师工号",name="teacherNo")
//    @TableField(value = "teacherNo")
    private String teacherNo;

    /**
     * 学院ID
     */
//    @ApiModelProperty(value="学院id",name="collegeId")
//    @TableField("collegeId")
    private Long collegeId;

    /**
     * 账户
     */
//    @ApiModelProperty(value="账户",name="name")
    private String name;

    /**
     * 密码
     */
//    @ApiModelProperty(value="密码",name="pwd")
    private String pwd;

    /**
     * 上次登录时间
     */
//    @ApiModelProperty(value="上次登录时间",name="loginTime")
//    @TableField("loginTime")
    private LocalDateTime loginTime;

    /**
     * 0 禁用 1 启用
     */
//    @ApiModelProperty(value="状态 0 禁用 1 启用",name="state")
    private Integer state;

    /**
     * 0 男 1 女
     */
//    @ApiModelProperty(value="性别 0 男 1 女",name="sex")
    private Integer sex;
    /**
     * 电话
     */
//    @ApiModelProperty(value="电话",name="tel")
    private String tel;
    /**
     * 邮箱
     */
//    @ApiModelProperty(value="邮箱",name="mail")
    private String mail;

    /**
     * 0离线，1在线
     */
//    @ApiModelProperty(value="在线状态 0离线，1在线",name="onlineState")
//    @TableField("onlineState")
    private Integer onlineState;

}
