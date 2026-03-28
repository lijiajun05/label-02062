<template>
  <div class="kanban-board">
    <div class="kanban-columns">
      <div class="kanban-column" v-for="column in columns" :key="column.id">
        <div class="column-header">
          <span class="column-name">{{ column.name }}</span>
          <el-badge :value="getColumnTasks(column.id).length" class="column-badge" />
        </div>
        <div class="column-tasks">
          <div class="task-card" v-for="task in getColumnTasks(column.id)" :key="task.id">
            <div class="task-name">{{ task.name }}</div>
            <div class="task-description">{{ task.description }}</div>
            <div class="task-footer">
              <el-tag size="small" type="info">{{ task.stage }}</el-tag>
              <el-progress :percentage="task.progress" :stroke-width="6" style="width: 80px" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'

const columns = ref([
  { id: 'todo', name: '待处理' },
  { id: 'in_progress', name: '进行中' },
  { id: 'done', name: '已完成' }
])

const tasks = ref([])

const getColumnTasks = (columnId) => {
  return tasks.value.filter(t => t.status === columnId)
}

onMounted(() => {
  tasks.value = [
    { id: 1, name: '需求分析文档编写', description: '编写详细的需求分析文档', stage: '需求', status: 'done', progress: 100 },
    { id: 2, name: '数据库设计', description: '设计数据库表结构和关系', stage: '设计', status: 'done', progress: 100 },
    { id: 3, name: '用户界面开发', description: '开发前端用户界面', stage: '开发', status: 'in_progress', progress: 60 },
    { id: 4, name: '后端接口开发', description: '开发后端API接口', stage: '开发', status: 'in_progress', progress: 40 },
    { id: 5, name: '系统测试', description: '进行系统功能测试', stage: '测试', status: 'todo', progress: 0 },
    { id: 6, name: '性能优化', description: '系统性能调优', stage: '优化', status: 'todo', progress: 0 }
  ]
})
</script>

<style scoped>
.kanban-board {
  padding: 20px;
  overflow-x: auto;
  height: calc(100vh - 200px);
}

.kanban-columns {
  display: flex;
  gap: 20px;
  min-width: 900px;
}

.kanban-column {
  flex: 1;
  min-width: 280px;
  background: #f5f5f5;
  border-radius: 8px;
  padding: 16px;
  display: flex;
  flex-direction: column;
}

.column-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 2px solid #e0e0e0;
}

.column-name {
  font-size: 16px;
  font-weight: bold;
  color: #303133;
}

.column-tasks {
  flex: 1;
  overflow-y: auto;
}

.task-card {
  background: white;
  border-radius: 6px;
  padding: 12px;
  margin-bottom: 12px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.2s;
}

.task-card:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
  transform: translateY(-2px);
}

.task-name {
  font-size: 14px;
  font-weight: 500;
  color: #303133;
  margin-bottom: 8px;
}

.task-description {
  font-size: 12px;
  color: #606266;
  margin-bottom: 12px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.task-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
