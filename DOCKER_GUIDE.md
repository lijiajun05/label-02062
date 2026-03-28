# Docker部署指南

## 问题说明

如果遇到类似以下错误：
```
failed to solve: openjdk:8-jre-slim: failed to resolve source metadata for docker.io/library/openjdk:8-jre-slim: unexpected status from HEAD request 403 Forbidden
```

这是Docker镜像源访问受限的问题。

## 解决方案

### 方案一：配置Docker镜像加速器（推荐）

#### macOS Docker Desktop配置：

1. 打开 Docker Desktop
2. 点击右上角设置图标（⚙️）
3. 选择「Docker Engine」
4. 在配置文件中添加以下镜像加速器配置：

```json
{
  "registry-mirrors": [
    "https://docker.mirrors.ustc.edu.cn",
    "https://hub-mirror.c.163.com",
    "https://mirror.baidubce.com"
  ],
  "builder": {
    "gc": {
      "defaultKeepStorage": "20GB",
      "enabled": true
    }
  },
  "experimental": false
}
```

5. 点击「Apply & restart」重启Docker

#### Linux配置：

编辑或创建 `/etc/docker/daemon.json` 文件：

```bash
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": [
    "https://docker.mirrors.ustc.edu.cn",
    "https://hub-mirror.c.163.com"
  ]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

### 方案二：使用官方镜像源

如果不想配置镜像加速器，可以尝试直接使用官方镜像。修改 `~/.docker/config.json` 文件，移除或注释掉镜像源配置。

### 方案三：手动拉取镜像

如果某些镜像无法通过镜像源获取，可以尝试手动从官方Docker Hub拉取：

```bash
# 拉取所需镜像
docker pull maven:3.8.6-openjdk-8
docker pull openjdk:8-jre
docker pull node:18-alpine
docker pull nginx:alpine
docker pull mysql:8.0
docker pull redis:7-alpine
```

## 常用镜像加速器列表

| 镜像加速器 | 地址 | 说明 |
|-----------|------|------|
| 中国科学技术大学 | https://docker.mirrors.ustc.edu.cn | 稳定 |
| 网易云 | https://hub-mirror.c.163.com | 稳定 |
| 百度云 | https://mirror.baidubce.com | 稳定 |
| 阿里云 | https://<你的ID>.mirror.aliyuncs.com | 需注册获取ID |
| 腾讯云 | https://mirror.ccs.tencentyun.com | 稳定 |

## 验证配置是否生效

配置完成后，可以通过以下命令验证：

```bash
docker info
```

在输出中查找 `Registry Mirrors` 部分，确认镜像加速器已配置。

## 重新构建项目

配置好镜像加速器后，重新执行构建命令：

```bash
# 清理之前的构建缓存
docker-compose down -v

# 重新构建并启动
docker-compose up --build -d
```

## 查看构建日志

如果构建过程中出现问题，可以查看详细日志：

```bash
# 查看所有服务日志
docker-compose logs -f

# 查看特定服务日志
docker-compose logs -f backend
docker-compose logs -f frontend
```
