package com.wwx.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class TIpTable implements Serializable{

//    @ApiModelProperty(value="网络地址映射Id",name="ipTableId")
//    @TableId(value = "ipTableId", type = IdType.AUTO)
    private Long ipTableId;

//    @ApiModelProperty(value="网络地址",name="network")
//    @TableField(value = "network")
    private String network;

//    @ApiModelProperty(value="课程ID",name="courseId")
//    @TableField(value = "courseId")
    private String courseId;

//    @ApiModelProperty(value="主机数",name="hostNum")
//    @TableField(value = "hostNum")
    private Integer hostNum;

    public TIpTable( String courseId, String network,Integer hostNum) {
        this.courseId = courseId;
        this.network = network;
        this.hostNum=hostNum;
    }

    @Override
    public String toString() {
        return "TIpTable{" +
                "ipTableId=" + ipTableId +
                ", courseId='" + courseId + '\'' +
                ", network='" + network + '\'' +
                ", hostNum=" + hostNum +
                '}';
    }

    public TIpTable(){

    }
}
