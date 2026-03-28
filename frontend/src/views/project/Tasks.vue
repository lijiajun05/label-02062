<template>
  <div class="tasks-page">
    <div class="page-header">
      <h3>任务管理</h3>
      <el-button type="primary" @click="showCreateDialog = true">创建任务</el-button>
    </div>
    
    <el-card class="tasks-card">
      <el-tree
        :data="taskTree"
        :props="defaultProps"
        node-key="id"
        default-expand-all
        @node-click="handleNodeClick"
      >
        <template #default="{ node, data }">
          <div class="task-item">
            <span class="task-name">{{ data.name }}</span>
            <el-tag :type="getStatusType(data.status)" size="small">
              {{ getStatusText(data.status) }}
            </el-tag>
            <el-progress :percentage="data.progress" :stroke-width="6" style="width: 100px; margin-left: 10px;"></el-progress>
            <span class="task-date">{{ data.startDate }} ~ {{ data.endDate }}</span>
          </div>
        </template>
      </el-tree>
    </el-card>

    <!-- 创建任务对话框 -->
    <el-dialog v-model="showCreateDialog" title="创建任务" width="600px">
      <el-form ref="createForm" :model="createForm" label-width="80px">
        <el-form-item label="任务名称" prop="name">
          <el-input v-model="createForm.name" placeholder="请输入任务名称"></el-input>
        </el-form-item>
        <el-form-item label="任务描述" prop="description">
          <el-input type="textarea" v-model="createForm.description" placeholder="请输入任务描述"></el-input>
        </el-form-item>
        <el-form-item label="优先级" prop="priority">
          <el-radio-group v-model="createForm.priority">
            <el-radio :label="0">低</el-radio>
            <el-radio :label="1">中</el-radio>
            <el-radio :label="2">高</el-radio>
            <el-radio :label="3">紧急</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="开始日期" prop="startDate">
          <el-date-picker v-model="createForm.startDate" type="date" placeholder="选择开始日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="结束日期" prop="endDate">
          <el-date-picker v-model="createForm.endDate" type="date" placeholder="选择结束日期"></el-date-picker>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="createTask">创建</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Tasks',
  data() {
    return {
      showCreateDialog: false,
      createForm: {
        name: '',
        description: '',
        priority: 1,
        startDate: '',
        endDate: ''
      },
      taskTree: [
        {
          id: 1,
          name: '需求分析',
          status: 2,
          progress: 100,
          startDate: '2024-01-01',
          endDate: '2024-01-10',
          children: [
            {
              id: 11,
              name: '用户需求调研',
              status: 2,
              progress: 100,
              startDate: '2024-01-01',
              endDate: '2024-01-05'
            },
            {
              id: 12,
              name: '需求文档编写',
              status: 2,
              progress: 100,
              startDate: '2024-01-06',
              endDate: '2024-01-10'
            }
          ]
        },
        {
          id: 2,
          name: '系统设计',
          status: 1,
          progress: 60,
          startDate: '2024-01-11',
          endDate: '2024-01-20',
          children: [
            {
              id: 21,
              name: '数据库设计',
              status: 1,
              progress: 80,
              startDate: '2024-01-11',
              endDate: '2024-01-15'
            },
            {
              id: 22,
              name: '接口设计',
              status: 1,
              progress: 40,
              startDate: '2024-01-16',
              endDate: '2024-01-20'
            }
          ]
        },
        {
          id: 3,
          name: '编码实现',
          status: 0,
          progress: 0,
          startDate: '2024-01-21',
          endDate: '2024-02-10'
        }
      ],
      defaultProps: {
        children: 'children',
        label: 'name'
      }
    }
  },
  methods: {
    getStatusType(status) {
      const types = ['info', 'warning', 'success', 'danger']
      return types[status] || 'info'
    },
    getStatusText(status) {
      const texts = ['待开始', '进行中', '已完成', '已取消']
      return texts[status] || '待开始'
    },
    handleNodeClick(data) {
      console.log('点击了任务:', data)
    },
    createTask() {
      // 模拟创建任务
      const newTask = {
        id: this.taskTree.length + 1,
        name: this.createForm.name,
        status: 0,
        progress: 0,
        startDate: this.createForm.startDate,
        endDate: this.createForm.endDate,
        children: []
      }
      this.taskTree.push(newTask)
      this.showCreateDialog = false
      this.createForm = { name: '', description: '', priority: 1, startDate: '', endDate: '' }
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

.tasks-card {
  padding: 20px;
}

.task-item {
  display: flex;
  align-items: center;
  gap: 10px;
  width: 100%;
}

.task-name {
  flex: 1;
}

.task-date {
  color: #909399;
  font-size: 12px;
}
</style>
