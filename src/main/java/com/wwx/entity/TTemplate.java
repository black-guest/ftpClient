package com.wwx.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 虚拟机模板
 * </p>
 *
 * @author wuweixu
 * @since 2020-03-27
 */
@Setter
@Getter
@ToString
public class TTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 虚拟机模板ID
     */
//    @TableId(value = "templateId", type = IdType.AUTO)
    private Long templateId;

    /**
     * cpu限制
     */
//    @TableField("cpu")
    private Double cpu;

    /**
     * 内存限制
     */
//    @TableField("memory")
    private String memory;

    /**
     * 主机名
     */
//    @TableField("hostname")
    private String hostname;

    /**
     * 镜像
     */
//    @TableField("image")
    private String image;
    /**
     * 虚拟机模板名称
     */
//    @TableField("name")
    private String name;
    /**
     * 扩展为状态： 0 禁用（后续不能被课程使用） 1 启用（后续能被课程使用）
     */
    private Integer state;

    /**
     * 访问exposePort的url 完整的url为 nodeIp:nodePort+url
     */
//    @TableField("url")
    private String url;

    /**
     * docker对外暴露的端口号
     */
//    @TableField("exposePort")
    private Integer exposePort;

    /**
     * 访问类型 0、正常的url 1、vnc访问
     */
//    @TableField("type")
    private Integer type;

}
