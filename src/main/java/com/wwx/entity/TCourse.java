package com.wwx.entity;

import com.wwx.entity.pojo.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 *  课程
 * </p>
 *
 * @author wuweixu
 * @since 2019-08-21
 */
@Getter
@Setter
@ToString
public class TCourse extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 课程ID
     */
//    @TableId(value = "courseId", type = IdType.AUTO)
    private Long courseId;

    /**
     * 课程名字 
     */
//    @TableField("courseName")
    private String courseName;

    /**
     * 课程类型
     */
//    @TableField("courseType")
    private Integer courseType;

    /**
     * 课程图片路径
     */
//    @TableField("courseImagePath")
    private String courseImagePath;

    /**
     * 0 禁用 1 启用
     */
    private Integer state;
    /**
     * 课程标签，多个按英文逗号隔开
     */
//    @ApiModelProperty(value="课程标签",name="多个按英文逗号隔开")
    private String tags;

    /**
     * 虚拟机模板ID
     */
//    @TableField("templateId")
    private Long templateId;

}
