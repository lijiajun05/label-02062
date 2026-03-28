<template>
  <div class="knowledge-management">
    <div class="header-actions">
      <el-button type="primary" @click="showCreateDialog = true">
        <el-icon><Plus /></el-icon>
        新建文档
      </el-button>
    </div>

    <el-table :data="knowledgeList" style="width: 100%" v-loading="loading">
      <el-table-column prop="title" label="标题" min-width="200" />
      <el-table-column prop="content" label="内容摘要" min-width="300" show-overflow-tooltip>
        <template #default="scope">
          {{ scope.row.content.substring(0, 100) }}...
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180">
        <template #default="scope">
          {{ formatDateTime(scope.row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="scope">
          <el-button link type="primary" size="small" @click="viewDocument(scope.row)">查看</el-button>
          <el-button link type="primary" size="small" @click="editDocument(scope.row)">编辑</el-button>
          <el-button link type="danger" size="small" @click="deleteDocument(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="showCreateDialog" :title="isEdit ? '编辑文档' : '新建文档'" width="800px">
      <el-form :model="documentForm" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="documentForm.title" placeholder="请输入文档标题" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input
            v-model="documentForm.content"
            type="textarea"
            :rows="15"
            placeholder="请输入文档内容"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showCreateDialog = false">取消</el-button>
        <el-button type="primary" @click="saveDocument">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="showViewDialog" :title="viewDocumentData.title" width="800px">
      <div class="document-content">
        {{ viewDocumentData.content }}
      </div>
      <template #footer>
        <el-button @click="showViewDialog = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const showCreateDialog = ref(false)
const showViewDialog = ref(false)
const isEdit = ref(false)
const knowledgeList = ref([])
const viewDocumentData = ref({ title: '', content: '' })

const documentForm = reactive({
  id: null,
  title: '',
  content: ''
})

const formatDateTime = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleString('zh-CN')
}

const viewDocument = (doc) => {
  viewDocumentData.value = { ...doc }
  showViewDialog.value = true
}

const editDocument = (doc) => {
  isEdit.value = true
  Object.assign(documentForm, doc)
  showCreateDialog.value = true
}

const saveDocument = () => {
  if (!documentForm.title) {
    ElMessage.error('请输入文档标题')
    return
  }
  if (!documentForm.content) {
    ElMessage.error('请输入文档内容')
    return
  }
  if (isEdit.value) {
    const index = knowledgeList.value.findIndex(d => d.id === documentForm.id)
    if (index > -1) {
      knowledgeList.value[index] = { ...knowledgeList.value[index], ...documentForm, updateTime: new Date() }
    }
    ElMessage.success('更新成功')
  } else {
    knowledgeList.value.unshift({
      id: Date.now(),
      ...documentForm,
      createTime: new Date(),
      updateTime: new Date()
    })
    ElMessage.success('创建成功')
  }
  showCreateDialog.value = false
  resetForm()
}

const deleteDocument = (id) => {
  ElMessageBox.confirm('确定要删除这个文档吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    knowledgeList.value = knowledgeList.value.filter(d => d.id !== id)
    ElMessage.success('删除成功')
  }).catch(() => {})
}

const resetForm = () => {
  isEdit.value = false
  documentForm.id = null
  documentForm.title = ''
  documentForm.content = ''
}

onMounted(() => {
  knowledgeList.value = [
    { 
      id: 1, 
      title: '项目需求文档', 
      content: '本文档详细描述了项目的功能需求、非功能需求以及业务流程。包括用户管理、商品管理、订单管理等核心模块的详细需求说明。', 
      createTime: '2024-01-15 10:00:00', 
      updateTime: '2024-01-15 10:00:00' 
    },
    { 
      id: 2, 
      title: '数据库设计文档', 
      content: '数据库设计文档包含了所有数据表的结构设计、字段说明、索引设计以及表之间的关系图。同时包含了数据库优化建议。', 
      createTime: '2024-01-20 14:30:00', 
      updateTime: '2024-01-20 14:30:00' 
    },
    { 
      id: 3, 
      title: 'API接口文档', 
      content: 'API接口文档描述了所有后端接口的请求参数、响应格式以及错误码说明。每个接口都提供了详细的使用示例。', 
      createTime: '2024-01-25 09:15:00', 
      updateTime: '2024-01-25 09:15:00' 
    },
    { 
      id: 4, 
      title: '部署手册', 
      content: '部署手册提供了项目在开发、测试、生产环境的部署步骤，包括环境配置、依赖安装、数据库初始化等内容。', 
      createTime: '2024-02-01 16:45:00', 
      updateTime: '2024-02-01 16:45:00' 
    }
  ]
})
</script>

<style scoped>
.knowledge-management {
  padding: 20px;
}

.header-actions {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

.document-content {
  padding: 20px;
  background: #f5f7fa;
  border-radius: 4px;
  min-height: 300px;
  white-space: pre-wrap;
  line-height: 1.8;
  color: #303133;
}
</style>
