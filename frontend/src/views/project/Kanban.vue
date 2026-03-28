<template>
  <div class="kanban-page">
    <div class="page-header">
      <h3>看板管理</h3>
      <el-button type="primary" @click="showStageDialog = true">添加阶段</el-button>
    </div>
    
    <div class="kanban-container">
      <div 
        class="kanban-column" 
        v-for="stage in kanbanStages" 
        :key="stage.id"
        :style="{ background: stage.color }"
      >
        <div class="column-header">
          <span class="column-title">{{ stage.name }}</span>
          <el-button type="text" size="small" @click="addTaskToStage(stage)">
            <el-icon><Plus /></el-icon>
          </el-button>
        </div>
        <div class="column-tasks">
          <div 
            class="task-card" 
            v-for="task in stage.tasks" 
            :key="task.id"
            @click="handleTaskClick(task)"
          >
            <div class="task-card-header">
              <span class="task-name">{{ task.name }}</span>
              <el-tag :type="getPriorityType(task.priority)" size="small">
                {{ getPriorityText(task.priority) }}
              </el-tag>
            </div>
            <div class="task-card-footer">
              <el-avatar :size="24">{{ task.assignee }}</el-avatar>
              <span class="task-date">{{ task.endDate }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加阶段对话框 -->
    <el-dialog v-model="showStageDialog" title="添加阶段" width="400px">
      <el-form ref="stageForm" :model="stageForm" label-width="80px">
        <el-form-item label="阶段名称" prop="name">
          <el-input v-model="stageForm.name" placeholder="请输入阶段名称"></el-input>
        </el-form-item>
        <el-form-item label="颜色" prop="color">
          <el-color-picker v-model="stageForm.color"></el-color-picker>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showStageDialog = false">取消</el-button>
        <el-button type="primary" @click="addStage">添加</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Kanban',
  data() {
    return {
      showStageDialog: false,
      stageForm: {
        name: '',
        color: '#409EFF'
      },
      kanbanStages: [
        {
          id: 1,
          name: '待处理',
          color: '#f5f7fa',
          tasks: [
            { id: 1, name: '页面设计', priority: 2, assignee: '张', endDate: '2024-01-25' },
            { id: 2, name: '接口开发', priority: 1, assignee: '李', endDate: '2024-01-26' }
          ]
        },
        {
          id: 2,
          name: '进行中',
          color: '#e6f7ff',
          tasks: [
            { id: 3, name: '数据库设计', priority: 2, assignee: '王', endDate: '2024-01-15' },
            { id: 4, name: '需求分析', priority: 1, assignee: '赵', endDate: '2024-01-20' }
          ]
        },
        {
          id: 3,
          name: '已完成',
          color: '#e8f5e9',
          tasks: [
            { id: 5, name: '项目立项', priority: 0, assignee: '陈', endDate: '2024-01-05' },
            { id: 6, name: '技术选型', priority: 0, assignee: '刘', endDate: '2024-01-10' }
          ]
        }
      ]
    }
  },
  methods: {
    getPriorityType(priority) {
      const types = ['info', 'warning', 'danger', 'danger']
      return types[priority] || 'info'
    },
    getPriorityText(priority) {
      const texts = ['低', '中', '高', '紧急']
      return texts[priority] || '中'
    },
    handleTaskClick(task) {
      console.log('点击了任务:', task)
    },
    addTaskToStage(stage) {
      console.log('添加任务到阶段:', stage)
    },
    addStage() {
      const newStage = {
        id: this.kanbanStages.length + 1,
        name: this.stageForm.name,
        color: this.stageForm.color,
        tasks: []
      }
      this.kanbanStages.push(newStage)
      this.showStageDialog = false
      this.stageForm = { name: '', color: '#409EFF' }
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

.kanban-container {
  display: flex;
  gap: 20px;
  overflow-x: auto;
  padding-bottom: 20px;
}

.kanban-column {
  min-width: 300px;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.column-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #e4e7ed;
}

.column-title {
  font-weight: bold;
  font-size: 16px;
}

.column-tasks {
  min-height: 200px;
}

.task-card {
  background: white;
  border-radius: 6px;
  padding: 12px;
  margin-bottom: 10px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  cursor: pointer;
}

.task-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.task-name {
  font-weight: 500;
}

.task-card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.task-date {
  color: #909399;
  font-size: 12px;
}
</style>
