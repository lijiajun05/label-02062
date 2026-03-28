<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-item">
            <div class="stat-icon" style="background: #409EFF">
              <el-icon :size="30"><FolderOpened /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.projectCount }}</div>
              <div class="stat-label">项目总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-item">
            <div class="stat-icon" style="background: #67C23A">
              <el-icon :size="30"><List /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.taskCount }}</div>
              <div class="stat-label">任务总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-item">
            <div class="stat-icon" style="background: #E6A23C">
              <el-icon :size="30"><Clock /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.inProgressCount }}</div>
              <div class="stat-label">进行中</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-item">
            <div class="stat-icon" style="background: #F56C6C">
              <el-icon :size="30"><CircleCheck /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.completedCount }}</div>
              <div class="stat-label">已完成</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>项目列表</span>
              <el-button type="primary" link @click="$router.push('/projects')">查看全部</el-button>
            </div>
          </template>
          <el-table :data="projects" style="width: 100%">
            <el-table-column prop="name" label="项目名称" />
            <el-table-column prop="description" label="描述" show-overflow-tooltip />
            <el-table-column label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === 1 ? 'success' : 'info'" size="small">
                  {{ scope.row.status === 1 ? '进行中' : '已归档' }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>近期任务</span>
            </div>
          </template>
          <el-table :data="tasks" style="width: 100%">
            <el-table-column prop="name" label="任务名称" />
            <el-table-column prop="stage" label="阶段" width="100">
              <template #default="scope">
                <el-tag size="small">{{ scope.row.stage }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="状态" width="100">
              <template #default="scope">
                <el-tag :type="getTaskStatusType(scope.row.status)" size="small">
                  {{ getTaskStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const stats = ref({
  projectCount: 0,
  taskCount: 0,
  inProgressCount: 0,
  completedCount: 0
})

const projects = ref([])
const tasks = ref([])

const getTaskStatusType = (status) => {
  const map = { todo: 'info', in_progress: 'warning', done: 'success' }
  return map[status] || 'info'
}

const getTaskStatusText = (status) => {
  const map = { todo: '待处理', in_progress: '进行中', done: '已完成' }
  return map[status] || status
}

onMounted(() => {
  projects.value = [
    { id: 1, name: '电商平台开发', description: '开发一个完整的电商平台', status: 1 },
    { id: 2, name: '企业官网重构', description: '重构企业官网，提升用户体验', status: 1 },
    { id: 3, name: '移动App开发', description: '开发iOS和Android移动应用', status: 1 }
  ]
  tasks.value = [
    { id: 1, name: '需求分析文档编写', stage: '需求', status: 'done' },
    { id: 2, name: '数据库设计', stage: '设计', status: 'done' },
    { id: 3, name: '用户界面开发', stage: '开发', status: 'in_progress' },
    { id: 4, name: '接口开发', stage: '开发', status: 'todo' },
    { id: 5, name: '系统测试', stage: '测试', status: 'todo' }
  ]
  stats.value = {
    projectCount: 5,
    taskCount: 28,
    inProgressCount: 8,
    completedCount: 15
  }
})
</script>

<style scoped>
.stat-item {
  display: flex;
  align-items: center;
  gap: 16px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin-top: 4px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
