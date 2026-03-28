package com.projectmgmt.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Knowledge {
    private Long id;
    private Long projectId;
    private String title;
    private String content;
    private Long creatorId;
    private Date createTime;
    private Date updateTime;
}
