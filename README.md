# 轻量级项目管理平台

一款轻量级的项目管理平台，致力于为个人及企业提供高效、便捷的项目管理服务。

## 功能特性

### 1. 任务管理
- 支持将项目拆解为多阶段任务
- 自动生成任务进度
- 任务状态跟踪（待办、进行中、已完成）
- 任务优先级管理

### 2. 看板管理
- 带有流程的看板
- 按任务的阶段展示任务的进度
- 可视化任务流程

### 3. 甘特图
- 展示项目任务的时间进度
- 帮助团队成员了解任务的完成情况和时间分配
- 直观的时间轴展示

### 4. 原型管理
- 托管 HTML 文件
- 在线浏览原型
- 原型版本管理

### 5. 知识库管理
- 方便进行项目信息和内容分享
- 支持富文本编辑
- 知识分类管理

### 6. 项目管理
- 管理项目基本信息
- 项目成员管理
- 控制项目的可见范围（公开/私有）
- 角色权限管理

## 技术架构

### 后端技术栈
- **框架**: Spring Boot 2.7.18
- **ORM**: MyBatis 3.5.15
- **数据库**: MySQL 8.0
- **缓存**: Redis
- **连接池**: Druid 1.2.20
- **认证**: JWT 0.11.5
- **JSON处理**: FastJSON 2.0.43

### 前端技术栈
- **框架**: Vue 3.4.21
- **UI组件库**: Element Plus 2.8.4
- **构建工具**: Vite 5.2.0
- **路由**: Vue Router 4.3.0
- **状态管理**: Pinia 2.2.4
- **HTTP客户端**: Axios 1.7.7
- **图表**: ECharts 5.5.1

## 项目结构

```
project-management-platform/
├── backend/                    # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/projectmgmt/
│   │   │   │   ├── common/     # 公共类
│   │   │   │   ├── config/     # 配置类
│   │   │   │   ├── controller/ # 控制器
│   │   │   │   ├── entity/     # 实体类
│   │   │   │   ├── mapper/     # 数据访问层
│   │   │   │   ├── service/    # 业务逻辑层
│   │   │   │   └── ProjectMgmtApplication.java
│   │   │   └── resources/
│   │   │       ├── application.yml
│   │   │       └── schema.sql
│   │   └── test/
│   └── pom.xml
└── frontend/                   # 前端项目
    ├── src/
    │   ├── views/              # 页面组件
    │   ├── utils/              # 工具类
    │   ├── router/             # 路由配置
    │   ├── App.vue
    │   └── main.js
    ├── index.html
    ├── package.json
    └── vite.config.js
```

## 数据库设计

### 主要表结构
- **sys_user**: 用户表
- **sys_project**: 项目表
- **sys_task**: 任务表
- **sys_project_member**: 项目成员表
- **sys_knowledge**: 知识库表
- **sys_prototype**: 原型表

详细表结构请参考 `backend/src/main/resources/schema.sql`

## 快速开始

### 环境要求
- JDK 1.8+
- Node.js 16+
- MySQL 8.0+
- Redis 6.0+

### 后端启动

1. 创建数据库
```sql
CREATE DATABASE project_mgmt DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 执行初始化脚本
```bash
mysql -u root -p project_mgmt < backend/src/main/resources/schema.sql
```

3. 修改配置文件
编辑 `backend/src/main/resources/application.yml`，修改数据库和Redis连接信息

4. 启动后端服务
```bash
cd backend
mvn spring-boot:run
```

后端服务将在 http://localhost:8080 启动

### 前端启动

1. 安装依赖
```bash
cd frontend
npm install
```

2. 启动开发服务器
```bash
npm run dev
```

前端服务将在 http://localhost:3000 启动

## 测试账号

系统初始化时会创建一个测试用户：
- 用户名: admin
- 密码: 123456

## API文档

### 认证接口
- `POST /api/auth/login` - 用户登录

### 项目接口
- `GET /api/projects` - 获取项目列表
- `GET /api/projects/{id}` - 获取项目详情
- `POST /api/projects` - 创建项目
- `PUT /api/projects/{id}` - 更新项目
- `DELETE /api/projects/{id}` - 删除项目
- `POST /api/projects/{id}/members` - 添加项目成员
- `DELETE /api/projects/{id}/members/{userId}` - 移除项目成员

### 任务接口
- `GET /api/tasks/project/{projectId}` - 获取项目任务列表
- `GET /api/tasks/{id}` - 获取任务详情
- `POST /api/tasks` - 创建任务
- `PUT /api/tasks/{id}` - 更新任务
- `DELETE /api/tasks/{id}` - 删除任务

### 知识库接口
- `GET /api/knowledge/project/{projectId}` - 获取项目知识库列表
- `GET /api/knowledge/{id}` - 获取知识详情
- `POST /api/knowledge` - 创建知识
- `PUT /api/knowledge/{id}` - 更新知识
- `DELETE /api/knowledge/{id}` - 删除知识

## 开发指南

### 后端开发
1. 实体类放在 `entity` 包下
2. Mapper接口放在 `mapper` 包下
3. 业务逻辑放在 `service` 包下
4. API控制器放在 `controller` 包下

### 前端开发
1. 页面组件放在 `src/views` 目录下
2. 路由配置在 `src/router/index.js`
3. API调用封装在 `src/utils/request.js`

## 许可证

本项目采用 MIT 许可证。
