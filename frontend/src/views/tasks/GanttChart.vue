<template>
  <div class="gantt-chart">
    <div class="gantt-header">
      <h3>项目甘特图</h3>
      <div class="timeline-controls">
        <el-radio-group v-model="viewMode" size="small">
          <el-radio-button label="day">日</el-radio-button>
          <el-radio-button label="week">周</el-radio-button>
          <el-radio-button label="month">月</el-radio-button>
        </el-radio-group>
      </div>
    </div>
    
    <div class="gantt-container">
      <div class="gantt-sidebar">
        <div class="sidebar-header">任务名称</div>
        <div class="sidebar-rows">
          <div class="task-row" v-for="task in tasks" :key="task.id">
            {{ task.name }}
          </div>
        </div>
      </div>
      
      <div class="gantt-timeline">
        <div class="timeline-header">
          <div class="timeline-cell" v-for="date in timelineDates" :key="date">
            {{ formatDateHeader(date) }}
          </div>
        </div>
        <div class="timeline-rows">
          <div class="task-timeline-row" v-for="task in tasks" :key="task.id">
            <div 
              class="task-bar"
              :style="getTaskBarStyle(task)"
              :class="getTaskBarClass(task)"
            >
              <span class="task-bar-text">{{ task.progress }}%</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'

const viewMode = ref('day')
const tasks = ref([])
const projectStartDate = ref(new Date('2024-01-15'))
const projectEndDate = ref(new Date('2024-02-28'))

const timelineDates = computed(() => {
  const dates = []
  let currentDate = new Date(projectStartDate.value)
  
  while (currentDate <= projectEndDate.value) {
    dates.push(new Date(currentDate))
    if (viewMode.value === 'day') {
      currentDate.setDate(currentDate.getDate() + 1)
    } else if (viewMode.value === 'week') {
      currentDate.setDate(currentDate.getDate() + 7)
    } else {
      currentDate.setMonth(currentDate.getMonth() + 1)
    }
  }
  
  return dates
})

const formatDateHeader = (date) => {
  if (viewMode.value === 'day') {
    return date.getDate()
  } else if (viewMode.value === 'week') {
    return `${date.getMonth() + 1}月第${Math.ceil(date.getDate() / 7)}周`
  } else {
    return `${date.getMonth() + 1}月`
  }
}

const getTaskBarStyle = (task) => {
  const startDate = new Date(task.startTime)
  const endDate = new Date(task.endTime)
  const totalDays = (projectEndDate.value - projectStartDate.value) / (1000 * 60 * 60 * 24)
  const startOffset = (startDate - projectStartDate.value) / (1000 * 60 * 60 * 24)
  const duration = (endDate - startDate) / (1000 * 60 * 60 * 24)
  
  return {
    left: `${(startOffset / totalDays) * 100}%`,
    width: `${(duration / totalDays) * 100}%`
  }
}

const getTaskBarClass = (task) => {
  return {
    'task-bar-done': task.status === 'done',
    'task-bar-in-progress': task.status === 'in_progress',
    'task-bar-todo': task.status === 'todo'
  }
}

onMounted(() => {
  tasks.value = [
    { id: 1, name: '需求分析文档编写', stage: '需求', status: 'done', progress: 100, startTime: '2024-01-15', endTime: '2024-01-20' },
    { id: 2, name: '数据库设计', stage: '设计', status: 'done', progress: 100, startTime: '2024-01-21', endTime: '2024-01-25' },
    { id: 3, name: '用户界面开发', stage: '开发', status: 'in_progress', progress: 60, startTime: '2024-01-26', endTime: '2024-02-10' },
    { id: 4, name: '后端接口开发', stage: '开发', status: 'in_progress', progress: 40, startTime: '2024-01-26', endTime: '2024-02-15' },
    { id: 5, name: '系统测试', stage: '测试', status: 'todo', progress: 0, startTime: '2024-02-16', endTime: '2024-02-20' },
    { id: 6, name: '部署上线', stage: '上线', status: 'todo', progress: 0, startTime: '2024-02-21', endTime: '2024-02-25' }
  ]
})
</script>

<style scoped>
.gantt-chart {
  padding: 20px;
  height: calc(100vh - 200px);
  display: flex;
  flex-direction: column;
}

.gantt-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.gantt-header h3 {
  margin: 0;
  color: #303133;
}

.gantt-container {
  flex: 1;
  display: flex;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  overflow: hidden;
}

.gantt-sidebar {
  width: 200px;
  border-right: 1px solid #e0e0e0;
  background: #f8f9fa;
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
}

.sidebar-header {
  padding: 12px;
  font-weight: bold;
  border-bottom: 1px solid #e0e0e0;
  background: #f5f7fa;
}

.sidebar-rows {
  flex: 1;
  overflow-y: auto;
}

.task-row {
  padding: 12px;
  border-bottom: 1px solid #e0e0e0;
  font-size: 13px;
  color: #303133;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.gantt-timeline {
  flex: 1;
  overflow: auto;
  background: white;
}

.timeline-header {
  display: flex;
  background: #f5f7fa;
  border-bottom: 1px solid #e0e0e0;
  position: sticky;
  top: 0;
  z-index: 10;
}

.timeline-cell {
  min-width: 40px;
  padding: 12px 0;
  text-align: center;
  font-size: 12px;
  color: #606266;
  border-right: 1px solid #e0e0e0;
  flex: 1;
}

.timeline-rows {
  position: relative;
}

.task-timeline-row {
  position: relative;
  height: 47px;
  border-bottom: 1px solid #e0e0e0;
}

.task-bar {
  position: absolute;
  top: 8px;
  height: 30px;
  border-radius: 4px;
  background: #409EFF;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 11px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s;
}

.task-bar:hover {
  opacity: 0.9;
}

.task-bar-done {
  background: #67C23A;
}

.task-bar-in-progress {
  background: #E6A23C;
}

.task-bar-todo {
  background: #909399;
}

.task-bar-text {
  padding: 0 8px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
