package com.pm.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("project")
public class Project {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String icon;
    private Integer status;
    private Integer visibility;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long createUserId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
