package com.wwx.entity;


import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wuweixu
 * @since 2019-09-23
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
//    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 编号
     */
//    @TableField("roleNo")
    private String roleNo;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限
     */
    private String perms;


    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRoleNo() {
        return roleNo;
    }

    public User setRoleNo(String roleNo) {
        this.roleNo = roleNo;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPerms() {
        return perms;
    }

    public User setPerms(String perms) {
        this.perms = perms;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
        "id=" + id +
        ", roleNo=" + roleNo +
        ", username=" + username +
        ", password=" + password +
        ", perms=" + perms +
        "}";
    }
}
