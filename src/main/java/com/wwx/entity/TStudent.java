package com.wwx.entity;

import com.wwx.entity.pojo.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生表
 * @author wuweixu
 * @since 2019-08-12
 */
@Setter
@Getter
@ToString
public class TStudent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID（主键)
     */
//    @TableId(value = "studentId", type = IdType.AUTO)
    private Long studentId;
    /**
     * 学生学号
     */
//    @TableField("studentNo")
    private String studentNo;
    /**
     * 班级ID
     */
//    @TableField("classId")
    private Long classId;
    /**
     * 年级ID
     */
//    @TableField("gradeId")
    private Long gradeId;
    /**
     * 专业ID
     */
//    @TableField("majorId")
    private Long majorId;
    /**
     * 学院ID
     */
//    @TableField("collegeId")
    private Long collegeId;
    /**
     * 账户名
     */
    private String name;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 上次登录时间
     */
//    @TableField("loginTime")
    private LocalDateTime loginTime;
    /**
     * 0 男 1 女
     */
    private Integer sex;
    /**
     * 电话
     */
    private String tel;
    /**
     * 邮箱
     */
    private String mail;

    /**
     * 0 离线， 1 在线
     */
//    @TableField("onlineState")
    private Integer onlineState;

    /**
     * 0 禁用 1 启用
     */
    private Integer state;

}
