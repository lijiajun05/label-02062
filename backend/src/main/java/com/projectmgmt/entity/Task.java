package com.projectmgmt.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Task {
    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private String stage;
    private String status;
    private Long assigneeId;
    private Date startTime;
    private Date endTime;
    private Integer progress;
    private Long parentId;
    private Date createTime;
    private Date updateTime;
}
