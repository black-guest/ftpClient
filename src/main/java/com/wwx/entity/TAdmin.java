package com.wwx.entity;

import com.wwx.entity.pojo.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author wuweixu
 * @since 2019-09-18
 */
@Getter
@Setter
@ToString
public class TAdmin extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识符
     */
//    @TableId(value = "adminId", type = IdType.AUTO)
    private Long adminId;

    /**
     * 管理员编号
     */
//    @TableField("adminNo")
    private String adminNo;

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
     * 0 禁用 1 启用
     */
    private Integer state;

}
