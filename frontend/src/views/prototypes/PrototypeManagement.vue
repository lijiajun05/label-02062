<template>
  <div class="prototype-management">
    <div class="header-actions">
      <el-button type="primary" @click="showUploadDialog = true">
        <el-icon><Upload /></el-icon>
        上传原型
      </el-button>
    </div>

    <el-row :gutter="20">
      <el-col :span="6" v-for="prototype in prototypes" :key="prototype.id" style="margin-top: 20px">
        <el-card shadow="hover" class="prototype-card">
          <div class="prototype-preview">
            <el-icon :size="60" color="#409EFF"><Document /></el-icon>
          </div>
          <div class="prototype-info">
            <div class="prototype-name">{{ prototype.name }}</div>
            <div class="prototype-meta">
              <span>上传于 {{ formatDate(prototype.createTime) }}</span>
            </div>
          </div>
          <div class="prototype-actions">
            <el-button type="primary" size="small" @click="previewPrototype(prototype)">
              <el-icon><View /></el-icon>
              预览
            </el-button>
            <el-button type="danger" size="small" @click="deletePrototype(prototype.id)">
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="showUploadDialog" title="上传原型" width="500px">
      <el-upload
        drag
        action="/api/prototypes/upload"
        :auto-upload="false"
        :on-change="handleFileChange"
        accept=".html,.zip"
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          将文件拖到此处，或<em>点击上传</em>
        </div>
        <template #tip>
          <div class="el-upload__tip">
            支持上传单个 HTML 文件或 ZIP 压缩包
          </div>
        </template>
      </el-upload>
      <template #footer>
        <el-button @click="showUploadDialog = false">取消</el-button>
        <el-button type="primary" @click="uploadPrototype">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog v-model="showPreviewDialog" title="原型预览" width="80%" fullscreen>
      <div class="prototype-preview-container">
        <iframe :src="previewUrl" frameborder="0" width="100%" height="100%"></iframe>
      </div>
      <template #footer>
        <el-button @click="showPreviewDialog = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const showUploadDialog = ref(false)
const showPreviewDialog = ref(false)
const previewUrl = ref('')
const prototypes = ref([])

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString('zh-CN')
}

const handleFileChange = (file) => {
  console.log('File selected:', file.name)
}

const uploadPrototype = () => {
  const newPrototype = {
    id: Date.now(),
    name: '新上传的原型',
    filePath: '/prototypes/mock.html',
    createTime: new Date()
  }
  prototypes.value.unshift(newPrototype)
  showUploadDialog.value = false
  ElMessage.success('上传成功')
}

const previewPrototype = (prototype) => {
  previewUrl.value = 'https://element-plus.org'
  showPreviewDialog.value = true
}

const deletePrototype = (id) => {
  ElMessageBox.confirm('确定要删除这个原型吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    prototypes.value = prototypes.value.filter(p => p.id !== id)
    ElMessage.success('删除成功')
  }).catch(() => {})
}

onMounted(() => {
  prototypes.value = [
    { id: 1, name: '电商平台首页原型', filePath: '/prototypes/home.html', createTime: '2024-01-20' },
    { id: 2, name: '商品详情页原型', filePath: '/prototypes/product.html', createTime: '2024-01-22' },
    { id: 3, name: '购物车原型', filePath: '/prototypes/cart.html', createTime: '2024-01-25' },
    { id: 4, name: '支付流程原型', filePath: '/prototypes/payment.html', createTime: '2024-01-28' }
  ]
})
</script>

<style scoped>
.prototype-management {
  padding: 20px;
}

.header-actions {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

.prototype-card {
  height: 300px;
  display: flex;
  flex-direction: column;
}

.prototype-preview {
  height: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  border-radius: 4px;
}

.prototype-info {
  flex: 1;
  padding: 12px 0;
}

.prototype-name {
  font-size: 14px;
  font-weight: 500;
  color: #303133;
  margin-bottom: 8px;
}

.prototype-meta {
  font-size: 12px;
  color: #909399;
}

.prototype-actions {
  display: flex;
  gap: 8px;
}

.prototype-preview-container {
  height: calc(100vh - 150px);
}
</style>
