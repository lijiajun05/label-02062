<template>
  <div class="gantt-page">
    <div class="page-header">
      <h3>甘特图</h3>
    </div>
    
    <el-card class="gantt-card">
      <div class="gantt-container">
        <div class="gantt-header">
          <div class="gantt-task-column">任务名称</div>
          <div class="gantt-time-column">
            <div class="time-row" v-for="week in weeks" :key="week">
              {{ week }}
            </div>
          </div>
        </div>
        <div class="gantt-body">
          <div 
            class="gantt-row" 
            v-for="task in ganttTasks" 
            :key="task.id"
            :style="{ paddingLeft: task.level * 20 + 'px' }"
          >
            <div class="gantt-task-column">{{ task.name }}</div>
            <div class="gantt-time-column">
              <div 
                class="task-bar"
                :style="{
                  left: task.startPosition + '%',
                  width: task.duration + '%',
                  background: task.color
                }"
              >
                <span>{{ task.progress }}%</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Gantt',
  data() {
    return {
      weeks: ['W1', 'W2', 'W3', 'W4', 'W5', 'W6', 'W7', 'W8'],
      ganttTasks: [
        {
          id: 1,
          name: '项目启动',
          level: 0,
          startPosition: 0,
          duration: 25,
          progress: 100,
          color: '#67C23A'
        },
        {
          id: 2,
          name: '需求分析',
          level: 1,
          startPosition: 0,
          duration: 12,
          progress: 100,
          color: '#67C23A'
        },
        {
          id: 3,
          name: '系统设计',
          level: 1,
          startPosition: 12,
          duration: 13,
          progress: 100,
          color: '#67C23A'
        },
        {
          id: 4,
          name: '开发阶段',
          level: 0,
          startPosition: 25,
          duration: 50,
          progress: 60,
          color: '#E6A23C'
        },
        {
          id: 5,
          name: '前端开发',
          level: 1,
          startPosition: 25,
          duration: 25,
          progress: 70,
          color: '#E6A23C'
        },
        {
          id: 6,
          name: '后端开发',
          level: 1,
          startPosition: 30,
          duration: 30,
          progress: 50,
          color: '#E6A23C'
        },
        {
          id: 7,
          name: '测试部署',
          level: 0,
          startPosition: 75,
          duration: 25,
          progress: 0,
          color: '#909399'
        }
      ]
    }
  }
}
</script>

<style scoped>
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h3 {
  margin: 0;
}

.gantt-card {
  padding: 20px;
}

.gantt-container {
  overflow-x: auto;
}

.gantt-header {
  display: flex;
  border-bottom: 2px solid #e4e7ed;
  font-weight: bold;
}

.gantt-task-column {
  width: 200px;
  min-width: 200px;
  padding: 10px;
  border-right: 1px solid #e4e7ed;
}

.gantt-time-column {
  display: flex;
  flex: 1;
  position: relative;
}

.time-row {
  flex: 1;
  text-align: center;
  padding: 10px;
  border-right: 1px solid #e4e7ed;
}

.gantt-body {
  display: flex;
  flex-direction: column;
}

.gantt-row {
  display: flex;
  border-bottom: 1px solid #e4e7ed;
}

.task-bar {
  position: absolute;
  height: 24px;
  margin-top: 8px;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 12px;
  cursor: pointer;
}
</style>
