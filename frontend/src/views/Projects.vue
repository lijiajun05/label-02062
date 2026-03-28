<template>
  <div class="projects-page">
    <el-container style="height: 100vh;">
      <el-header class="projects-header">
        <div class="header-left">
          <div class="logo">项目管理平台</div>
          <el-button type="primary" @click="showCreateDialog = true">创建项目</el-button>
        </div>
        <div class="header-right">
          <el-avatar :size="32">A</el-avatar>
        </div>
      </el-header>
      <el-main class="projects-main">
        <el-row :gutter="20">
          <el-col :span="8" v-for="project in projects" :key="project.id">
            <el-card class="project-card" @click.native="goToProject(project.id)">
              <div class="project-header">
                <div class="project-icon">{{ project.name.charAt(0) }}</div>
                <div class="project-info">
                  <h3>{{ project.name }}</h3>
                  <p>{{ project.description }}</p>
                </div>
              </div>
              <div class="project-footer">
                <span>{{ project.memberCount }} 成员</span>
                <el-tag :type="project.status === 1 ? 'success' : 'info'">
                  {{ project.status === 1 ? '进行中' : '已归档' }}
                </el-tag>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-main>
    </el-container>

    <!-- 创建项目对话框 -->
    <el-dialog v-model="showCreateDialog" title="创建项目" width="500px">
      <el-form ref="createForm" :model="createForm" label-width="80px">
        <el-form-item label="项目名称" prop="name">
          <el-input v-model="createForm.name" placeholder="请输入项目名称"></el-input>
        </el-form-item>
        <el-form-item label="项目描述" prop="description">
          <el-input type="textarea" v-model="createForm.description" placeholder="请输入项目描述"></el-input>
        </el-form-item>
        <el-form-item label="可见范围" prop="visibility">
          <el-radio-group v-model="createForm.visibility">
            <el-radio :label="0">私有</el-radio>
            <el-radio :label="1">公开</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="createProject">创建</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Projects',
  data() {
    return {
      showCreateDialog: false,
      createForm: {
        name: '',
        description: '',
        visibility: 0
      },
      projects: [
        {
          id: 1,
          name: '电商平台开发',
          description: '开发一个功能完善的电商平台',
          memberCount: 5,
          status: 1
        },
        {
          id: 2,
          name: '企业管理系统',
          description: '开发企业内部管理系统',
          memberCount: 3,
          status: 1
        },
        {
          id: 3,
          name: '移动应用开发',
          description: '开发一款移动应用',
          memberCount: 2,
          status: 0
        }
      ]
    }
  },
  methods: {
    goToProject(projectId) {
      this.$router.push(`/project/${projectId}`)
    },
    createProject() {
      // 模拟创建项目
      const newProject = {
        id: this.projects.length + 1,
        name: this.createForm.name,
        description: this.createForm.description,
        memberCount: 1,
        status: 1
      }
      this.projects.push(newProject)
      this.showCreateDialog = false
      this.createForm = { name: '', description: '', visibility: 0 }
    }
  }
}
</script>

<style scoped>
.projects-page {
  height: 100vh;
}

.projects-header {
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
  gap: 20px;
}

.logo {
  font-size: 20px;
  font-weight: bold;
  color: #409EFF;
}

.projects-main {
  background: #f5f7fa;
  padding: 20px;
}

.project-card {
  cursor: pointer;
  margin-bottom: 20px;
}

.project-header {
  display: flex;
  align-items: center;
  gap: 15px;
  margin-bottom: 15px;
}

.project-icon {
  width: 50px;
  height: 50px;
  background: #409EFF;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 24px;
  border-radius: 5px;
}

.project-info h3 {
  margin: 0 0 5px 0;
  font-size: 18px;
}

.project-info p {
  margin: 0;
  color: #909399;
  font-size: 14px;
}

.project-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid #ebeef5;
}
</style>
