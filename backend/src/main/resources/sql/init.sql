-- 创建数据库
CREATE DATABASE IF NOT EXISTS pm_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE pm_db;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码',
    `email` VARCHAR(100) COMMENT '邮箱',
    `avatar` VARCHAR(255) COMMENT '头像',
    `real_name` VARCHAR(50) COMMENT '真实姓名',
    `phone` VARCHAR(20) COMMENT '手机号',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 项目表
CREATE TABLE IF NOT EXISTS `project` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '项目ID',
    `name` VARCHAR(100) NOT NULL COMMENT '项目名称',
    `description` TEXT COMMENT '项目描述',
    `icon` VARCHAR(255) COMMENT '项目图标',
    `status` TINYINT DEFAULT 1 COMMENT '项目状态：0-已归档，1-进行中',
    `visibility` TINYINT DEFAULT 0 COMMENT '可见范围：0-私有，1-公开',
    `start_date` DATE COMMENT '开始日期',
    `end_date` DATE COMMENT '结束日期',
    `create_user_id` BIGINT NOT NULL COMMENT '创建者ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_create_user` (`create_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目表';

-- 项目成员表
CREATE TABLE IF NOT EXISTS `project_member` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `project_id` BIGINT NOT NULL COMMENT '项目ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role` TINYINT DEFAULT 2 COMMENT '角色：0-拥有者，1-管理员，2-成员',
    `join_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_project_user` (`project_id`, `user_id`),
    INDEX `idx_project_id` (`project_id`),
    INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目成员表';

-- 任务表
CREATE TABLE IF NOT EXISTS `task` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `project_id` BIGINT NOT NULL COMMENT '项目ID',
    `parent_id` BIGINT DEFAULT 0 COMMENT '父任务ID',
    `name` VARCHAR(200) NOT NULL COMMENT '任务名称',
    `description` TEXT COMMENT '任务描述',
    `status` TINYINT DEFAULT 0 COMMENT '任务状态：0-待开始，1-进行中，2-已完成，3-已取消',
    `priority` TINYINT DEFAULT 1 COMMENT '优先级：0-低，1-中，2-高，3-紧急',
    `stage_id` BIGINT COMMENT '看板阶段ID',
    `assignee_id` BIGINT COMMENT '负责人ID',
    `start_date` DATE COMMENT '开始日期',
    `end_date` DATE COMMENT '结束日期',
    `progress` TINYINT DEFAULT 0 COMMENT '进度百分比',
    `order_num` INT DEFAULT 0 COMMENT '排序号',
    `create_user_id` BIGINT NOT NULL COMMENT '创建者ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_project_id` (`project_id`),
    INDEX `idx_stage_id` (`stage_id`),
    INDEX `idx_assignee_id` (`assignee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';

-- 看板阶段表
CREATE TABLE IF NOT EXISTS `kanban_stage` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '阶段ID',
    `project_id` BIGINT NOT NULL COMMENT '项目ID',
    `name` VARCHAR(50) NOT NULL COMMENT '阶段名称',
    `color` VARCHAR(20) DEFAULT '#409EFF' COMMENT '颜色',
    `order_num` INT DEFAULT 0 COMMENT '排序号',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='看板阶段表';

-- 原型表
CREATE TABLE IF NOT EXISTS `prototype` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '原型ID',
    `project_id` BIGINT NOT NULL COMMENT '项目ID',
    `name` VARCHAR(100) NOT NULL COMMENT '原型名称',
    `description` TEXT COMMENT '原型描述',
    `file_path` VARCHAR(255) NOT NULL COMMENT '文件路径',
    `file_name` VARCHAR(255) NOT NULL COMMENT '原始文件名',
    `file_size` BIGINT COMMENT '文件大小',
    `create_user_id` BIGINT NOT NULL COMMENT '创建者ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='原型表';

-- 知识库表
CREATE TABLE IF NOT EXISTS `knowledge_base` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '知识库ID',
    `project_id` BIGINT NOT NULL COMMENT '项目ID',
    `title` VARCHAR(200) NOT NULL COMMENT '标题',
    `content` TEXT NOT NULL COMMENT '内容',
    `category` VARCHAR(50) COMMENT '分类',
    `create_user_id` BIGINT NOT NULL COMMENT '创建者ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='知识库表';

-- 插入初始数据
INSERT INTO `user` (`username`, `password`, `email`, `real_name`) 
VALUES ('admin', '123456', 'admin@pm.com', '管理员');
