<template>
  <div class="task-management">
    <div class="header-actions">
      <el-button type="primary" @click="showCreateDialog = true">
        <el-icon><Plus /></el-icon>
        新建任务
      </el-button>
    </div>

    <el-table :data="tasks" style="width: 100%" v-loading="loading">
      <el-table-column prop="name" label="任务名称" min-width="200" />
      <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip />
      <el-table-column prop="stage" label="阶段" width="100">
        <template #default="scope">
          <el-tag size="small">{{ scope.row.stage }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.status)" size="small">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="进度" width="120">
        <template #default="scope">
          <el-progress :percentage="scope.row.progress" :stroke-width="10" />
        </template>
      </el-table-column>
      <el-table-column prop="startTime" label="开始时间" width="110">
        <template #default="scope">
          {{ formatDate(scope.row.startTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="endTime" label="结束时间" width="110">
        <template #default="scope">
          {{ formatDate(scope.row.endTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="editTask(scope.row)">编辑</el-button>
          <el-button link type="danger" size="small" @click="deleteTask(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="showCreateDialog" :title="isEdit ? '编辑任务' : '新建任务'" width="600px">
      <el-form :model="taskForm" label-width="80px">
        <el-form-item label="任务名称">
          <el-input v-model="taskForm.name" placeholder="请输入任务名称" />
        </el-form-item>
        <el-form-item label="任务描述">
          <el-input v-model="taskForm.description" type="textarea" :rows="3" placeholder="请输入任务描述" />
        </el-form-item>
        <el-form-item label="阶段">
          <el-select v-model="taskForm.stage" placeholder="请选择阶段">
            <el-option label="需求" value="需求" />
            <el-option label="设计" value="设计" />
            <el-option label="开发" value="开发" />
            <el-option label="测试" value="测试" />
            <el-option label="上线" value="上线" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="taskForm.status" placeholder="请选择状态">
            <el-option label="待处理" value="todo" />
            <el-option label="进行中" value="in_progress" />
            <el-option label="已完成" value="done" />
          </el-select>
        </el-form-item>
        <el-form-item label="进度">
          <el-slider v-model="taskForm.progress" :marks="{0: '0%', 50: '50%', 100: '100%'}" />
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker v-model="taskForm.startTime" type="date" placeholder="选择开始时间" style="width: 100%" />
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker v-model="taskForm.endTime" type="date" placeholder="选择结束时间" style="width: 100%" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="saveTask">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'

const route = useRoute()
const loading = ref(false)
const showCreateDialog = ref(false)
const isEdit = ref(false)
const tasks = ref([])

const taskForm = reactive({
  id: null,
  name: '',
  description: '',
  stage: '开发',
  status: 'todo',
  progress: 0,
  startTime: null,
  endTime: null
})

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString('zh-CN')
}

const getStatusType = (status) => {
  const map = { todo: 'info', in_progress: 'warning', done: 'success' }
  return map[status] || 'info'
}

const getStatusText = (status) => {
  const map = { todo: '待处理', in_progress: '进行中', done: '已完成' }
  return map[status] || status
}

const editTask = (task) => {
  isEdit.value = true
  Object.assign(taskForm, task)
  showCreateDialog.value = true
}

const saveTask = () => {
  if (!taskForm.name) {
    ElMessage.error('请输入任务名称')
    return
  }
  if (isEdit.value) {
    const index = tasks.value.findIndex(t => t.id === taskForm.id)
    if (index > -1) {
      tasks.value[index] = { ...tasks.value[index], ...taskForm }
    }
    ElMessage.success('更新成功')
  } else {
    tasks.value.unshift({
      id: Date.now(),
      ...taskForm
    })
    ElMessage.success('创建成功')
  }
  showCreateDialog.value = false
  resetForm()
}

const deleteTask = (id) => {
  ElMessageBox.confirm('确定要删除这个任务吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    tasks.value = tasks.value.filter(t => t.id !== id)
    ElMessage.success('删除成功')
  }).catch(() => {})
}

const resetForm = () => {
  isEdit.value = false
  taskForm.id = null
  taskForm.name = ''
  taskForm.description = ''
  taskForm.stage = '开发'
  taskForm.status = 'todo'
  taskForm.progress = 0
  taskForm.startTime = null
  taskForm.endTime = null
}

onMounted(() => {
  tasks.value = [
    { id: 1, name: '需求分析文档编写', description: '编写详细的需求分析文档', stage: '需求', status: 'done', progress: 100, startTime: '2024-01-15', endTime: '2024-01-20' },
    { id: 2, name: '数据库设计', description: '设计数据库表结构和关系', stage: '设计', status: 'done', progress: 100, startTime: '2024-01-21', endTime: '2024-01-25' },
    { id: 3, name: '用户界面开发', description: '开发前端用户界面', stage: '开发', status: 'in_progress', progress: 60, startTime: '2024-01-26', endTime: '2024-02-10' },
    { id: 4, name: '后端接口开发', description: '开发后端API接口', stage: '开发', status: 'in_progress', progress: 40, startTime: '2024-01-26', endTime: '2024-02-15' },
    { id: 5, name: '系统测试', description: '进行系统功能测试', stage: '测试', status: 'todo', progress: 0, startTime: '2024-02-16', endTime: '2024-02-20' }
  ]
})
</script>

<style scoped>
.task-management {
  padding: 20px;
}

.header-actions {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}
</style>
