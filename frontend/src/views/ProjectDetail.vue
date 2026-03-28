<template>
  <div class="project-detail-page">
    <el-container style="height: 100vh;">
      <el-header class="project-header">
        <div class="header-left">
          <el-button type="text" @click="goBack">
            <el-icon><ArrowLeft /></el-icon>
          </el-button>
          <div class="project-info">
            <h2>{{ projectName }}</h2>
            <el-tag size="small" type="success">进行中</el-tag>
          </div>
        </div>
        <div class="header-right">
          <el-avatar :size="32">A</el-avatar>
        </div>
      </el-header>
      <el-container>
        <el-aside width="200px" class="project-aside">
          <el-menu
            :router="true"
            default-active="/project/1/tasks"
            class="project-menu"
            background-color="#f5f7fa"
            text-color="#303133"
            active-text-color="#409EFF"
          >
            <el-menu-item :index="`/project/${$route.params.id}/tasks`">
              <el-icon><Document /></el-icon>
              <span>任务管理</span>
            </el-menu-item>
            <el-menu-item :index="`/project/${$route.params.id}/kanban`">
              <el-icon><List /></el-icon>
              <span>看板管理</span>
            </el-menu-item>
            <el-menu-item :index="`/project/${$route.params.id}/gantt`">
              <el-icon><Calendar /></el-icon>
              <span>甘特图</span>
            </el-menu-item>
            <el-menu-item :index="`/project/${$route.params.id}/prototypes`">
              <el-icon><Monitor /></el-icon>
              <span>原型管理</span>
            </el-menu-item>
            <el-menu-item :index="`/project/${$route.params.id}/knowledge`">
              <el-icon><Reading /></el-icon>
              <span>知识库</span>
            </el-menu-item>
            <el-menu-item :index="`/project/${$route.params.id}/settings`">
              <el-icon><Setting /></el-icon>
              <span>项目设置</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main class="project-main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'ProjectDetail',
  computed: {
    projectName() {
      // 这里应该从后端获取项目名称，暂时用固定值
      return '电商平台开发'
    }
  },
  methods: {
    goBack() {
      this.$router.push('/projects')
    }
  }
}
</script>

<style scoped>
.project-detail-page {
  height: 100vh;
}

.project-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  padding: 0 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.header-left {
  display: flex;
  align-items: center;
  gap: 15px;
}

.project-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.project-info h2 {
  margin: 0;
  font-size: 18px;
}

.project-aside {
  background: #f5f7fa;
  padding-top: 20px;
}

.project-menu {
  border-right: none;
}

.project-main {
  background: #f5f7fa;
  padding: 20px;
}
</style>
