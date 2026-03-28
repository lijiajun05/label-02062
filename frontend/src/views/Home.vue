<template>
  <div class="home-page">
    <el-container style="height: 100vh;">
      <el-header class="home-header">
        <div class="logo">项目管理平台</div>
        <el-button type="primary" @click="$router.push('/login')">登录</el-button>
      </el-header>
      <el-main class="home-main">
        <div class="hero-section">
          <h1>轻量级项目管理平台</h1>
          <p>致力于为个人及企业提供高效、便捷的项目管理服务</p>
          <el-button type="primary" size="large" @click="$router.push('/login')">立即开始</el-button>
        </div>
        <div class="features-section">
          <el-row :gutter="20">
            <el-col :span="6">
              <div class="feature-card">
                <el-icon size="48" color="#409EFF"><Document /></el-icon>
                <h3>任务管理</h3>
                <p>支持将项目拆解为多阶段任务，自动生成任务进度</p>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="feature-card">
                <el-icon size="48" color="#67C23A"><List /></el-icon>
                <h3>看板管理</h3>
                <p>带有流程的看板，按任务的阶段展示任务的进度</p>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="feature-card">
                <el-icon size="48" color="#E6A23C"><Calendar /></el-icon>
                <h3>甘特图</h3>
                <p>展示项目任务的时间进度，帮助团队成员了解任务的完成情况和时间分配</p>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="feature-card">
                <el-icon size="48" color="#F56C6C"><Monitor /></el-icon>
                <h3>原型管理</h3>
                <p>托管HTML文件，在线浏览原型</p>
              </div>
            </el-col>
          </el-row>
          <el-row :gutter="20" style="margin-top: 20px;">
            <el-col :span="6">
              <div class="feature-card">
                <el-icon size="48" color="#909399"><Reading /></el-icon>
                <h3>知识库管理</h3>
                <p>方便进行项目信息和内容分享</p>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="feature-card">
                <el-icon size="48" color="#409EFF"><Setting /></el-icon>
                <h3>项目管理</h3>
                <p>管理项目和项目成员，控制项目的可见范围</p>
              </div>
            </el-col>
          </el-row>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'Home'
}
</script>

<style scoped>
.home-page {
  height: 100vh;
}

.home-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  padding: 0 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #409EFF;
}

.home-main {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 40px;
}

.hero-section {
  text-align: center;
  color: white;
  margin-bottom: 60px;
}

.hero-section h1 {
  font-size: 48px;
  margin-bottom: 20px;
}

.hero-section p {
  font-size: 20px;
  margin-bottom: 40px;
  opacity: 0.9;
}

.features-section {
  background: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.feature-card {
  text-align: center;
  padding: 20px;
}

.feature-card h3 {
  margin: 15px 0;
  font-size: 18px;
}

.feature-card p {
  color: #606266;
  font-size: 14px;
}
</style>
