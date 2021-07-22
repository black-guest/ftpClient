package com.wwx.entity.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author wuweixu
 * @create 2019/9/4
 */
@Setter
@Getter
@ToString
public class BaseEntity {

    /**
     * 创建时间
     */
//    @TableField("createdTime")
    private Date createdTime;

    /**
     * 上次信息更新时间
     */
//    @TableField("modifiedTime")
    private LocalDateTime modifiedTime;

    /**
     * 0 垃圾数据 1 可用数据
     */
    @JSONField(deserialize = false, serialize = false)
    private Integer yn;

    /**
     * 最后更新人
     */
    @JSONField(deserialize = false, serialize = false)
//    @TableField("updateUser")
    private String updateUser;

    /**
     * 版本号
     */
    @JSONField(deserialize = false, serialize = false)
//    @TableField("lastUpdateNo")
    //版本号注解 支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
    //整数类型下 newVersion = oldVersion + 1
    //newVersion 会回写到 entity 中
    //仅支持 updateById(id) 与 update(entity, wrapper) 方法
    //在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
//    @Version
    private Integer lastUpdateNo;


}
