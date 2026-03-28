package com.projectmgmt.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Project {
    private Long id;
    private String name;
    private String description;
    private Long creatorId;
    private Integer visibility;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
