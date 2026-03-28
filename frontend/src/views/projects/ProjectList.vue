<template>
  <div class="project-list">
    <div class="header-actions">
      <el-button type="primary" @click="showCreateDialog = true">
        <el-icon><Plus /></el-icon>
        新建项目
      </el-button>
    </div>

    <el-row :gutter="20">
      <el-col :span="6" v-for="project in projects" :key="project.id" style="margin-top: 20px">
        <el-card shadow="hover" class="project-card" @click="goToProject(project.id)">
          <div class="project-header">
            <span class="project-name">{{ project.name }}</span>
            <el-tag :type="project.status === 1 ? 'success' : 'info'" size="small">
              {{ project.status === 1 ? '进行中' : '已归档' }}
            </el-tag>
          </div>
          <div class="project-description">{{ project.description }}</div>
          <div class="project-footer">
            <span class="project-time">创建于 {{ formatDate(project.createTime) }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="showCreateDialog" title="新建项目" width="500px">
      <el-form :model="projectForm" label-width="80px">
        <el-form-item label="项目名称">
          <el-input v-model="projectForm.name" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目描述">
          <el-input v-model="projectForm.description" type="textarea" :rows="3" placeholder="请输入项目描述" />
        </el-form-item>
        <el-form-item label="可见性">
          <el-radio-group v-model="projectForm.visibility">
            <el-radio :label="0">私有</el-radio>
            <el-radio :label="1">公开</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="createProject">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const showCreateDialog = ref(false)
const projects = ref([])

const projectForm = reactive({
  name: '',
  description: '',
  visibility: 0
})

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString('zh-CN')
}

const goToProject = (id) => {
  router.push(`/projects/${id}`)
}

const createProject = () => {
  if (!projectForm.name) {
    ElMessage.error('请输入项目名称')
    return
  }
  const newProject = {
    id: Date.now(),
    name: projectForm.name,
    description: projectForm.description,
    visibility: projectForm.visibility,
    status: 1,
    createTime: new Date()
  }
  projects.value.unshift(newProject)
  showCreateDialog.value = false
  projectForm.name = ''
  projectForm.description = ''
  projectForm.visibility = 0
  ElMessage.success('创建成功')
}

onMounted(() => {
  projects.value = [
    { id: 1, name: '电商平台开发', description: '开发一个完整的电商平台，包含用户、商品、订单、支付等模块', status: 1, createTime: '2024-01-15' },
    { id: 2, name: '企业官网重构', description: '重构企业官网，提升用户体验和SEO效果', status: 1, createTime: '2024-02-01' },
    { id: 3, name: '移动App开发', description: '开发iOS和Android移动应用，实现核心业务功能', status: 1, createTime: '2024-02-15' },
    { id: 4, name: '数据分析平台', description: '构建数据分析平台，提供数据可视化和报表功能', status: 1, createTime: '2024-03-01' }
  ]
})
</script>

<style scoped>
.project-list {
  padding: 20px;
}

.header-actions {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

.project-card {
  cursor: pointer;
  height: 200px;
  display: flex;
  flex-direction: column;
}

.project-card:hover {
  transform: translateY(-5px);
  transition: all 0.3s;
}

.project-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.project-name {
  font-size: 16px;
  font-weight: bold;
  color: #303133;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.project-description {
  flex: 1;
  color: #606266;
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  margin-bottom: 12px;
}

.project-footer {
  color: #909399;
  font-size: 12px;
  border-top: 1px solid #ebeef5;
  padding-top: 12px;
}
</style>
