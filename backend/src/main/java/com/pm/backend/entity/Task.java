package com.pm.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("task")
public class Task {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private Long parentId;
    private String name;
    private String description;
    private Integer status;
    private Integer priority;
    private Long stageId;
    private Long assigneeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer progress;
    private Integer orderNum;
    private Long createUserId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
