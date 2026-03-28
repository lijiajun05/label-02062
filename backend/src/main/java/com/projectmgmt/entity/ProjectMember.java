package com.projectmgmt.entity;

import lombok.Data;
import java.util.Date;

@Data
public class ProjectMember {
    private Long id;
    private Long projectId;
    private Long userId;
    private String role;
    private Date createTime;
}
