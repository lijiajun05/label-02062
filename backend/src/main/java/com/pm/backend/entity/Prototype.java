package com.pm.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("prototype")
public class Prototype {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    private String name;
    private String description;
    private String filePath;
    private String fileName;
    private Long fileSize;
    private Long createUserId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
