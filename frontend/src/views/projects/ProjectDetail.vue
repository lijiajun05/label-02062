<template>
  <div class="project-detail" v-if="project">
    <div class="project-header">
      <div class="project-info">
        <h1>{{ project.name }}</h1>
        <p>{{ project.description }}</p>
      </div>
      <el-dropdown>
        <el-button type="primary">
          操作 <el-icon class="el-icon--right"><arrow-down /></el-icon>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push(`/projects/${project.id}/settings`)">项目设置</el-dropdown-item>
            <el-dropdown-item divided>归档项目</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>

    <el-tabs v-model="activeTab" @tab-change="handleTabChange">
      <el-tab-pane label="任务管理" name="tasks">
        <router-view />
      </el-tab-pane>
      <el-tab-pane label="看板" name="kanban">
        <router-view />
      </el-tab-pane>
      <el-tab-pane label="甘特图" name="gantt">
        <router-view />
      </el-tab-pane>
      <el-tab-pane label="原型管理" name="prototypes">
        <router-view />
      </el-tab-pane>
      <el-tab-pane label="知识库" name="knowledge">
        <router-view />
      </el-tab-pane>
      <el-tab-pane label="设置" name="settings">
        <router-view />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const project = ref(null)
const activeTab = ref('tasks')

const handleTabChange = (tabName) => {
  router.push(`/projects/${route.params.id}/${tabName}`)
}

watch(() => route.path, (path) => {
  const tabMap = {
    tasks: 'tasks',
    kanban: 'kanban',
    gantt: 'gantt',
    prototypes: 'prototypes',
    knowledge: 'knowledge',
    settings: 'settings'
  }
  for (const [key, value] of Object.entries(tabMap)) {
    if (path.includes(`/${key}`)) {
      activeTab.value = value
      break
    }
  }
})

onMounted(() => {
  project.value = {
    id: route.params.id,
    name: '电商平台开发',
    description: '开发一个完整的电商平台，包含用户、商品、订单、支付等模块'
  }
  
  const path = route.path
  if (path.includes('/kanban')) activeTab.value = 'kanban'
  else if (path.includes('/gantt')) activeTab.value = 'gantt'
  else if (path.includes('/prototypes')) activeTab.value = 'prototypes'
  else if (path.includes('/knowledge')) activeTab.value = 'knowledge'
  else if (path.includes('/settings')) activeTab.value = 'settings'
  else activeTab.value = 'tasks'
})
</script>

<style scoped>
.project-detail {
  padding: 0;
}

.project-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 20px;
  padding: 20px;
  background: #fff;
  border-radius: 4px;
}

.project-info h1 {
  font-size: 24px;
  color: #303133;
  margin-bottom: 8px;
}

.project-info p {
  color: #606266;
  margin: 0;
}
</style>
